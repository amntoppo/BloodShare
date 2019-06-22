package com.hack.bloodshare.model;

public class User {

    public String name;
    public String group;

    public User(String name, String group) {
        this.name = name;
        this.group = group;
    }
    public String getGroup() {
        return group;
    }
}
