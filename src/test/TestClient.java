package test;

import model.ATM;

public class TestClient {
    public static void main(String[] args) {
        Thread atm = new Thread(new ATM());
        atm.run();
    }
}
