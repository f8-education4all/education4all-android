package com.f82019.education4all.models;

import com.orm.SugarRecord;

public class Archivment extends SugarRecord {
    private String archivment;
    private String description;
    private String goal;
    private int logo;

    public Archivment(String archivment, String description, String goal, int logo) {
        this.archivment = archivment;
        this.description = description;
        this.goal = goal;
        this.logo = logo;
    }

    public String getArchivment() {
        return archivment;
    }

    public void setArchivment(String archivment) {
        this.archivment = archivment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    @Override
    public String toString(){
        return archivment;
    }
}
