package Interface;

import model.Account;

import java.io.Serializable;

public interface RPCService extends Serializable {
    StringBuffer save(int i, double money, boolean login);
    StringBuffer draw(int i, double money, boolean login);
    StringBuffer printInfo(int i, Boolean login);
    Boolean accountReg(int id, String pwd, String name, double remain);
    int login(int id, String pwd);
    StringBuffer sayHi(int i);

}