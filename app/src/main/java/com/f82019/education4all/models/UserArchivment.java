package com.f82019.education4all.models;

import com.orm.SugarRecord;

import java.util.Date;

public class UserArchivment extends SugarRecord {
    private User user;
    private Archivment archivment;
    private Date dateAchivment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Archivment getArchivment() {
        return archivment;
    }

    public void setArchivment(Archivment archivment) {
        this.archivment = archivment;
    }

    public Date getDateAchivment() {
        return dateAchivment;
    }

    public void setDateAchivment(Date dateAchivment) {
        this.dateAchivment = dateAchivment;
    }

    @Override
    public String toString() {
        return dateAchivment.toString();
    }
}
