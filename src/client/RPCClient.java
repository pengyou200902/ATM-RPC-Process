package client;

import model.DataPak;
import java.io.*;
import java.net.Socket;

public class RPCClient {
    public Object start(DataPak request, String host, int port) throws Throwable{
//        System.out.println("RPCClient.start()");
        Socket socket = new Socket(host, port);
        InputStream in = null;
        ObjectInputStream oin = null;
        OutputStream out = null;
        ObjectOutputStream oout = null;
        try {
            // 1. 发送数据
            out = socket.getOutputStream();
            oout = new ObjectOutputStream(out);
            oout.writeObject(request);
            oout.flush();
//            System.out.println("Server : " + socket);
//            System.out.println("client sent over, host: " + host + " port: " + port);
            // 2. 获取返回数据，转参数类型
            in = socket.getInputStream();
            oin = new ObjectInputStream(in);
            DataPak res = (DataPak)oin.readObject();
//            System.out.println("client getResult over");
            return res.getResult();
        }finally{
            try {	//关闭流
                if(in != null) in.close();
                if(oin != null) oin.close();
                if(out != null) out.close();
                if(oout != null) oout.close();
                if(socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
