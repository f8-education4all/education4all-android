package com.f82019.education4all.models;

import com.orm.SugarRecord;

public class Archivment extends SugarRecord {
    private String archivment;
    private String description;
    private String goal;
    private String logo;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString(){
        return archivment;
    }
}
