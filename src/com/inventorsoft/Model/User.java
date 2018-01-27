package com.inventorsoft.Model;

public class User {

    private String login;
    private String password;
    private double money;
    private boolean isAdmin;

    public User(String login, String password, double money, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.money = money;
        this.isAdmin = isAdmin;
    }

    public User(String login, String password, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
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
