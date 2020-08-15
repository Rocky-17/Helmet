package com.example.lenovo.bean;

public class DataForNetMessage {
    private String appId;
    public static final int WSD = 0;//温湿度
    public static final int YL = 1;//压力
    private int type;
    private float tem, hum;
    private int YaLi;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setYaLi(int YaLi) {
        this.YaLi = YaLi;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public void setTem(float tem) {
        this.tem = tem;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTem(int tem) {
        this.tem = tem;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }

    public double getHum() {
        return hum;
    }

    public float getTem() {
        return tem;
    }

    public int getYaLi() {
        return YaLi;
    }

    public int getType() {
        return type;
    }
}
