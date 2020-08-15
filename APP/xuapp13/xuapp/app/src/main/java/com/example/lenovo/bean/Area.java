package com.example.lenovo.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private int id;
    private String name;
    private float progress;

    public Area(int id, String name, float progress) {
        this.id=id;
        this.name = name;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public static List<Area> getAreaList(){
        List<Area> list = new ArrayList<>();
        list.add(new Area(100,"A座一区", 32f));
        list.add(new Area(101,"A座二区", 52f));
        list.add(new Area(102,"A座三区", 12f));
        list.add(new Area(103,"A座科学厅", 2f));
        list.add(new Area(104,"B座北区", 3f));
        list.add(new Area(105,"B座军事厅", 41f));
        list.add(new Area(106,"C座南区", 68f));
        list.add(new Area(107,"C座北区", 100f));

        return list;
    }

    public static String getAreaName(int areaId){
        List<Area> list=getAreaList();
        for (Area area:list){
            if(area.getId()==areaId){
                return area.getName();
            }
        }
        return "unknown";
    }
}
