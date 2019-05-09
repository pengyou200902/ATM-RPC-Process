package model;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private double remain;
    private String name;
    private String pwd;

    public Account(int id, String pwd) {
        this.id = id;
        this.pwd = pwd;
        this.name = "";
        this.remain = 0;
    }

    public Account(int id, String pwd, String name, double remain) {
        this.id = id;
        this.remain = remain;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRemain() {
        return remain * 100 / 100;
    }

    public void setRemain(double remain) {
        this.remain = remain * 100 / 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "账号=" + id +
                ", 密码='" + pwd + '\'' +
                ", 姓名='" + name + '\'' +
                ", 余额=" + remain + "元" +
                "}\n";
    }
}
