package com.softweb.desktop.database.entities;

import javafx.scene.image.Image;

public class Application {
    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Image logo;
    private String developerFullName;
    private String developerShortName;

    public Application() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public String getDeveloperFullName() {
        return developerFullName;
    }

    public void setDeveloperFullName(String developerFullName) {
        this.developerFullName = developerFullName;
    }

    public String getDeveloperShortName() {
        return developerShortName;
    }

    public void setDeveloperShortName(String developerShortName) {
        this.developerShortName = developerShortName;
    }
}
