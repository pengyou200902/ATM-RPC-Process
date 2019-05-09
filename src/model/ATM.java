package model;

import Interface.RPCService;
import client.RPCClientProxy;

import java.util.Scanner;

public class ATM implements Runnable{
    private static Scanner sc = new Scanner(System.in);

    private String host = "127.0.0.1";
    private int port = 9000;

    public ATM(){}

    public ATM(String h, int p){
        this.host = h;
        this.port = p < 0 ? 9000 : p;
    }

    public int topMenu() {
        int choice = 0;
        do {
            System.out.print("菜单: \n\t1. 登陆\n\t2. 注册\n\t3. 退出\n请输入您的选择 >>> ");
            choice = sc.nextInt();
        }while(choice < 1 || choice > 3);
        return choice;
    }

    public int secondMenu(){
        int choice = 0;
        do {
            System.out.print("请选择业务: \n\t1. 取钱\n\t2. 存钱\n\t3. 查余额\n\t4. 注销\n请输入您的选择 >>> ");
            choice = sc.nextInt();
        }while(choice < 1 || choice > 4);
        return choice;
    }

    public int showup(RPCService service){
        boolean login = false;
//        Account myAcc = null;
        int accIndex = -1;
        while(!login) {
            int id;
            String pwd, name;
            double remain = 0;
            int choice = topMenu(); //菜单放这里
            switch (choice) {
                case 1: // 登陆
                    System.out.print("请输入您的id: ");
                    id = sc.nextInt();sc.nextLine();
                    System.out.print("请输入您的密码: ");
                    pwd = sc.nextLine();
//                    myAcc = service.login(id, pwd);
                    accIndex = service.login(id, pwd);
                    if (accIndex > -1) {
                        System.out.println(service.sayHi(accIndex));
                        login = true;
                    } else {
                        System.out.println("--------验证失败！您输入的信息有误！");
                    }
                    break;

                case 2: // 注册
                    System.out.print("请输入您的id: ");
                    id = sc.nextInt();sc.nextLine();
                    System.out.print("请输入您的密码: ");
                    pwd = sc.nextLine();
                    System.out.print("请输入您的姓名: ");
                    name = sc.nextLine();
                    remain = 0;
                    if (service.accountReg(id, pwd, name, remain)) {
                        System.out.println("--------您已成功注册！");
                    } else {
                        System.out.println("--------账户已存在！无法再次注册，请选择登陆！");
                    }
                    break;
                case 3: // 退出
                    System.exit(0);
                    break;
            }
        }
        return accIndex;
    }

    public void op(RPCService service, int accIndex){
        boolean login = false;
        if(accIndex > -1) login = true;
        while(login) {
            int choice = secondMenu();
            switch (choice) {
                case 1: // 取钱
                    System.out.print("请输入要取的钱数: ");
                    System.out.print(service.draw(accIndex, sc.nextDouble(), login));
                    break;

                case 2: // 存钱
                    System.out.print("请输入要存的钱数: ");
                    System.out.print(service.save(accIndex, sc.nextDouble(), login));
                    break;

                case 3: // 查余额
                    System.out.print(service.printInfo(accIndex, login));
                    break;

                case 4: // 退出
                    login = false;
                    accIndex = -1;
                    break;
            }
        }
    }
    public void run() {
        RPCService service = new RPCClientProxy(host, port).getProxy(RPCService.class);
        Account myAcc;
        int accIndex = -1;
        do {
            accIndex = showup(service);
            op(service, accIndex);
        }while (accIndex > -1);
    }
}
