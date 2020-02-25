package com.pillowcase.user.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.pillowcase.user.model.User;
import com.pillowcase.user.sqlite.dao.UserDao;

/**
 * Author      : PillowCase
 * Create On   : 2020-02-25 13:48
 * Description :
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static int version = 1;

    private String CREATE_USER_SQL = "CREATE TABLE IF NOT EXISTS " + UserDao.TABLE_NAME +
            "(" + User.Sql.userId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            User.Sql.username + " VARCHAR(255), " +
            User.Sql.password + " VARCHAR(255), " +
            User.Sql.nickname + " VARCHAR(255), " +
            User.Sql.loginTime + " VATCHAR(255))";

    public DataBaseHelper(Context context, String dataBase) {
        this(context, dataBase, null, version);
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
