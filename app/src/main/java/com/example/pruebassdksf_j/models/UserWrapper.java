package com.example.pruebassdksf_j.models;

import com.stfalcon.chatkit.commons.models.IUser;


public class UserWrapper implements IUser {
    String id;
    String name;
    String avatar;

    public UserWrapper(String id, String name, String avatar) {
        this.avatar = avatar;
        this.name = name;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
};