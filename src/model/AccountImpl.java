package model;

import Interface.RPCService;
import java.util.ArrayList;

public class AccountImpl implements RPCService {
    private static ArrayList<Account> accounts = new ArrayList<>();

    public AccountImpl() {
        accounts.add(new Account(1,"asd","PengYou-1",12345.67));
        accounts.add(new Account(2,"asd","PengYou-2",89012.34));
    }

    public StringBuffer sayHi(int i) {
        return new StringBuffer().append("--------你好~! ").append(", ").append(accounts.get(i).getName());
    }

    @Override
    public StringBuffer save(int i, double money, boolean login) {
        if(login && i > -1) {
            Account account = accounts.get(i);
            account.setRemain((account.getRemain() + money) * 100 / 100);
            accounts.set(i, account);
            return new StringBuffer().append("--------存款成功！请确认").append(account.toString());
        }
        return new StringBuffer().append("--------验证失败！无法存款\n");
    }

    @Override
    public StringBuffer draw(int i, double money, boolean login) {
//        if(login && account!=null) {
        if(login && i > -1) {
            Account account = accounts.get(i);
            if(account.getRemain() >= money) {
//                int i = accounts.indexOf(account);
                account.setRemain((account.getRemain() - money) * 100 / 100);
                accounts.set(i, account);
                return new StringBuffer().append("--------取款成功！请确认.").append(account.toString());
            }
            else return new StringBuffer().append("--------余额不足！\n");
        }
        return new StringBuffer().append("--------验证失败！无法取款.\n");
    }

    @Override
    public StringBuffer printInfo(int i, Boolean login) {
        if(i > -1) {
            if (login) {
                Account account = accounts.get(i);
                return new StringBuffer().append(account.toString());
            }
            else return new StringBuffer().append("--------请您先登陆！\n");
        }
        else return new StringBuffer().append("--------ERROR ！\n");
    }

    @Override
    public Boolean accountReg(int id, String pwd, String name, double remain) {
        Account account = new Account(id, pwd, name,remain);
        if(accounts.contains(account)) {
            return false;
        }
        else
        {
            accounts.add(account);
            return true;
        }
    }

    @Override
    public int login(int id, String pwd) {
        Account tmpAcc = new Account(id, pwd);
//        System.out.println("tempACC: " + tmpAcc);
//        System.out.println("index: " + accounts.indexOf(tmpAcc));
//        System.out.println(accounts.contains(tmpAcc));
//        if(accounts.contains(tmpAcc)) {
        int i = accounts.indexOf(tmpAcc);
        if(i != -1) {
            Account found = accounts.get(i);
            if(pwd.equals(found.getPwd())){
//                System.out.println(found);
                return i;
            }
        }
        System.out.println("--------not found");
        return -1;
    }
}
