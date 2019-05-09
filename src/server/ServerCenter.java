package server;

import Interface.Center;
import model.DataPak;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCenter implements Center {

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<String, Class> serviceRegistry = new HashMap<>();

    private static boolean isRunning = false;

    private static int port = 0;

    private static String host = "127.0.0.1";

    public ServerCenter() {}

    public ServerCenter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    @Override
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.printf("服务器启动完成，监听地址为%s\n", serverSocket.getLocalSocketAddress());
        System.out.printf("center.start\n");
        isRunning = true;
        try {
            while (true) {
                executor.execute(new ServiceTask(serverSocket.accept()));
            }
        } finally {
            serverSocket.close();
        }

    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        serviceRegistry.put(serviceInterface.getName(), impl);
        System.out.println("注册服务列表：");
        int i = 1;
        for (Object k: serviceRegistry.keySet()) {
            System.out.println(new StringBuffer().append(i).append(". ").append(k).append(": ").append(serviceRegistry.get(k)).toString());
            i++;
        }
        System.out.println("注册函数结束：");

    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPort() {
        return port;
    }

    private static class ServiceTask implements Runnable {
        Socket client = null;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        public void run() {
            OutputStream out = null;
            ObjectOutputStream oos = null;
            InputStream in = null;
            ObjectInputStream ois = null;

            try {
                out = client.getOutputStream();
                oos = new ObjectOutputStream(out);
                in = client.getInputStream();
                ois = new ObjectInputStream(in);

                // 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                DataPak dataPak = (DataPak)ois.readObject();
                System.out.println(Thread.currentThread().getName() + " accepted: " + dataPak);
                String serviceName = dataPak.getClassName();
                String methodName = dataPak.getMethodName();
                Class<?>[] parameterTypes = dataPak.getParamTypes();
                Object[] arguments = dataPak.getParams();
                Class serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                dataPak.setResult(result);
                // 3.将执行结果反序列化，通过socket发送给客户端
                oos.writeObject(dataPak);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
