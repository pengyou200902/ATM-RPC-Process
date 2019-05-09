package test;

import Interface.Center;
import Interface.RPCService;
import model.AccountImpl;
import server.ServerCenter;

import java.io.IOException;

public class TestServer_A {

    private static class ServerThread implements Runnable {
        private String host = "127.0.0.1";
        private int port = 9000;
        @Override
        public void run() {
//            System.out.println("main.run");
            try {
//                System.out.println("main.run.try");
                Center server = new ServerCenter(host, port);
                server.register(RPCService.class, AccountImpl.class);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread server = new Thread(new ServerThread());
        server.start();
    }
}
