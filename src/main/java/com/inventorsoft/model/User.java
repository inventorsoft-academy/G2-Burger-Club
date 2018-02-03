package com.inventorsoft.model;

public class User {

    private String login;
    private String password;
    private double money;
    private int commissions;
    private boolean isAdmin;

    public User(String login, String password, double money, int commissions, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.money = money;
        this.commissions = commissions;
        this.isAdmin = isAdmin;
    }

    public User(String login, String password, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getCommissions() {
        return commissions;
    }

    public void setCommissions(int commissions) {
        this.commissions = commissions;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setEmail(double money) {
        this.money = money;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
