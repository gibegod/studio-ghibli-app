package com.example.studioghibliapp;

public class FilmModel {
    private String id;
    private String title;
    private String image;
    private String description;

    public FilmModel() {
    }

    public FilmModel(String id, String title, String image, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
