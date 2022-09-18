package com.example.studioghibliapp.models;

public class Film {
    private String id;
    private String title;
    private String image;
    private String description;
    private String director;
    private String releaseDate;

    public Film() {
    }

    public Film(String id, String title, String image, String description, String director, String releaseDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.director = director;
        this.releaseDate = releaseDate;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
