package com.f82019.education4all.models;

import com.orm.SugarRecord;

public class User extends SugarRecord {
    private String username;
    private String email;
    private String name;
    private String local_image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal_image() {
        return local_image;
    }

    public void setLocal_image(String local_image) {
        this.local_image = local_image;
    }

    @Override
    public String toString(){
        return name;
    }
}
