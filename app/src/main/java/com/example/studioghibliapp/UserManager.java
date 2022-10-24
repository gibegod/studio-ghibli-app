package com.example.studioghibliapp;

import android.content.Context;

import com.example.studioghibliapp.models.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    Dao<User, Integer> dao;

    public UserManager() { }

    public UserManager(Context context) {
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);

        try {
            dao = helper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserManager getInstance(Context context) {
        if(instance == null) {
            instance = new UserManager(context);
        }

        return instance;
    }

    public static void setInstance(UserManager instance) {
        UserManager.instance = instance;
    }

    public Dao<User, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<User, Integer> dao) {
        this.dao = dao;
    }

    public List<User> getUsers() throws Exception {
        return dao.queryForAll();
    }

    public boolean userExistsInDB(String username) throws Exception {
        boolean result = false;

        QueryBuilder<User, Integer> userQb = dao.queryBuilder();
        userQb.where().eq("username", username);

        if(!userQb.query().isEmpty()) {
            result = true;
        }

        return result;
    }

    public boolean logInUser(User user) throws Exception {
        User userDB = getUserByUsername(user.getUsername());

        if(userDB != null && userDB.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    public User getUserByUsername(String username) throws Exception {
        QueryBuilder<User, Integer> userQb = dao.queryBuilder();
        userQb.where().eq("username", username);

        if(!userQb.query().isEmpty()) {
            return userQb.query().get(0);
        } else {
            return null;
        }
    }

    public void createUser(User user) throws Exception {
        dao.create(user);
    }
}
