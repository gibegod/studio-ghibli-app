package com.example.studioghibliapp.models;

public class Film {
    private String id;
    private String title;
    private String image;
    private String movie_banner;
    private String description;
    private String director;
    private String release_date;
    private String producer;
    private String rt_score;

    public Film() { }

    public Film(String id, String title, String image, String movie_banner, String description, String director, String releaseDate, String producer, String rt_score) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.movie_banner = movie_banner;
        this.description = description;
        this.director = director;
        this.release_date = releaseDate;
        this.producer = producer;
        this.rt_score = rt_score;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getProducer() { return producer; }

    public void setProducer(String producer) { this.producer = producer; }

    public String getMovie_banner() { return movie_banner; }

    public void setMovie_banner(String movie_banner) { this.movie_banner = movie_banner; }

    public String getRt_score() { return rt_score; }

    public void setRt_score(String rt_score) { this.rt_score = rt_score; }
}
