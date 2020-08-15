package com.example.lenovo.bean;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String appId;
    private String ip;
    private String secret;
    private String port;


    User(int id, String appId, String ip, String secret, String port) {
        this.id = id;
        this.appId = appId;
        this.ip = ip;
        this.secret = secret;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    public static List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(101, "w6OYeemqsWJC4Ila_P0CqNy_eZ4a", "139.159.133.59", "ANP7ybKNaYvWa2NzK0k21ByJR2ca", "8743"));
        userList.add(new User(102, "QO7fJXSK8Z4aLV9KjUVdk996DHka", "139.159.133.59", "sc0ubDPB0APOaq_OYzCNAICjvzsa", "8743"));
        return userList;
    }
}
