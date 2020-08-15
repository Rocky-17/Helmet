package com.example.lenovo.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Seat {
    private int seatId;
    private int areaId;
    private String appId;
    private String name;
    private boolean available;

    public Seat(int seatId, int areaId, String appId, String name, boolean available) {
        this.seatId = seatId;
        this.areaId = areaId;
        this.appId = appId;
        this.name = name;
        this.available = available;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public static List<Seat> getSeatList(int areaId){
        List<Seat> list=new ArrayList<>();
        for(int i=0;i<16;i++){
            int seatId=100+i+1;
            if(i==0) {
                list.add(new Seat(seatId, areaId, "w6OYeemqsWJC4Ila_P0CqNy_eZ4a", "A" + getNameTail(i + 1), true));
            }else if(i==1) {
                list.add(new Seat(seatId, areaId, "QO7fJXSK8Z4aLV9KjUVdk996DHka", "A" + getNameTail(i + 1), true));
            }else {
                list.add(new Seat(seatId, areaId, "1321321321412432", "A" + getNameTail(i + 1), true));
            }
        }
        return list;
    }

    private static String getNameTail(int seatId){
        if(seatId<10){
            return "0"+seatId;
        }
        return seatId+"";
    }

    public static String getSeatName(int areaName,int seatId){
        List<Seat> list=getSeatList(101);
        for (Seat seat:list){
            if(seat.getSeatId()==seatId){
                return seat.getName();
            }
        }
        return "unknown";
    }
}
