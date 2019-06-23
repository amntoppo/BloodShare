package com.hack.bloodshare.model;

public class Receivers {

    private String name;
    private String group;
    private String location;

    public Receivers() {

    }

    public Receivers(String name, String group, String location) {
        this.name = name;
        this.group = group;
        this.location = location;
    }
    public String getName() {
        return name;
    }
    public String getGroup() {
        return group;
    }
    public String getLocation() { return location;}
    public void setName(String name) {
        this.name = name;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setLocation(String location) { this.location = location; }
}
