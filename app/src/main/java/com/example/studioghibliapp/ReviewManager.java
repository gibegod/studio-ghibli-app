package com.example.studioghibliapp;

import android.content.Context;

import com.example.studioghibliapp.models.Review;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class ReviewManager {

    private static ReviewManager instance;
    Dao<Review, Integer> dao;

    public ReviewManager() { }

    public ReviewManager(Context context) {
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);

        try {
            dao = helper.getDao(Review.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ReviewManager getInstance(Context context) {
        if(instance == null) {
            instance = new ReviewManager(context);
        }

        return instance;
    }

    public static void setInstance(ReviewManager instance) {
        ReviewManager.instance = instance;
    }

    public Dao<Review, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<Review, Integer> dao) {
        this.dao = dao;
    }

    public List<Review> getReviews() throws Exception {
        return dao.queryForAll();
    }

    public Review getReviewByUserIdAndFilmId(Integer userId, String filmId) throws Exception {
        QueryBuilder<Review, Integer> reviewQb = dao.queryBuilder();
        reviewQb.where().eq("user_id", userId);

        if(!reviewQb.query().isEmpty()) {
            for (Review review: reviewQb.query()) {
                if(review.getFilm().equals(filmId)) {
                    return review;
                }
            }
        }

        return null;
    }

    public void createOrUpdateReview(Review review) throws Exception {
        dao.createOrUpdate(review);
    }

}
