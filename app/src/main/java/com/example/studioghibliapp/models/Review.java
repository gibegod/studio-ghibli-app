package com.example.studioghibliapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "review")
public class Review {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "user_id", foreignAutoRefresh = true, foreign = true)
    private User user;

    @DatabaseField
    private String filmId;

    @DatabaseField
    private Integer score;

    @DatabaseField
    private String comment;

    public Review() { }

    public Review(User user, String filmId, Integer score, String comment) {
        this.user = user;
        this.filmId = filmId;
        this.score = score;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFilm() {
        return filmId;
    }

    public void setFilm(String filmId) {
        this.filmId = filmId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
