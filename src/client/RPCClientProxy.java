package client;

import model.DataPak;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCClientProxy implements InvocationHandler {
    private String host = "127.0.0.1";
    private int port = 9000;

    public RPCClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RPCClientProxy() { }

    public <T>T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, RPCClientProxy.this);
    }

    public Object invoke(Object obj, Method method, Object[] params) throws Throwable {
//        System.out.printf("PY: 开始代理客户端, 监听地址为：%s:%d\n" , host, port);
//        System.out.println("client proxy invoke");
        //封装参数
        DataPak dataPak = new DataPak(method.getDeclaringClass().getName(), method.getName(), method.getParameterTypes(), params);
        //链接服务器调用服务
        RPCClient client = new RPCClient();
        return client.start(dataPak, host, port);
    }

}
