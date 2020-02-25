package com.pillowcase.user.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.user.interfaces.IUserModelCallback;
import com.pillowcase.user.model.User;
import com.pillowcase.user.sqlite.DataBaseHelper;
import com.pillowcase.user.utiils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : PillowCase
 * Create On   : 2020-02-25 13:59
 * Description :
 */
public class UserDao implements ILoggerOperation {
    private static UserDao instance;
    private SQLiteOpenHelper mHelper;
    private LoggerUtils mLoggerUtils;

    public static String TABLE_NAME = "User";

    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null) {
                    instance = new UserDao();
                }
            }
        }
        return instance;
    }

    public void initData(Context context, boolean isLogger) {
        try {
            if (mHelper == null) {
                mHelper = new DataBaseHelper(context, "ModelData");
            }
            if (mLoggerUtils == null) {
                mLoggerUtils = new LoggerUtils(isLogger, "UserDao");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加数据
     */
    public void insertData(User user, IUserModelCallback callback) {
        try {
            User data = queryData(user.getUsername());
            if (data != null) {
                callback.onFailed("该账号已存在");
                return;
            }
            if (mHelper != null) {
                SQLiteDatabase database = mHelper.getWritableDatabase();
                if (database != null) {

                    ContentValues values = new ContentValues();
                    values.put(User.Sql.username, user.getUsername());
                    values.put(User.Sql.password, user.getPassword());
                    values.put(User.Sql.nickname, user.getNickname());
                    values.put(User.Sql.loginTime, TimeUtils.stampToDate(System.currentTimeMillis()));

                    database.insert(TABLE_NAME, null, values);

                    user = queryData(user.getUsername());
                    if (user != null) {
                        callback.onSuccess(user);
                    } else {
                        callback.onFailed("数据插入失败");
                    }
                }
            }
        } catch (Exception e) {
            error(e, "insertData");
            callback.onFailed(e.getLocalizedMessage());
        }
    }

    /**
     * 删除数据
     *
     * @param userId 用户Id
     */
    public boolean deleteData(int userId) {
        boolean isDelete = true;
        try {
            if (mHelper != null) {
                User queryData = queryData(userId);
                if (queryData != null) {
                    SQLiteDatabase database = mHelper.getWritableDatabase();
                    if (database != null) {
                        database.delete(TABLE_NAME, User.Sql.userId + "=?", new String[]{userId + ""});

                        database.close();
                    }

                    queryData = queryData(userId);
                    if (queryData != null) {
                        isDelete = false;
                    }
                }
            }
        } catch (Exception e) {
            error(e, "deleteData");
        }
        return isDelete;
    }

    /**
     * 查询数据
     *
     * @return
     */
    public User queryData(String username) {
        User user = null;
        try {
            if (mHelper != null) {
                SQLiteDatabase database = mHelper.getReadableDatabase();
                if (database != null) {
                    Cursor cursor = database.query(TABLE_NAME,
                            new String[]{User.Sql.userId, User.Sql.username, User.Sql.password, User.Sql.nickname, User.Sql.loginTime},
                            User.Sql.username + "=?",
                            new String[]{username},
                            null, null, null);
                    while (cursor.moveToNext()) {
                        user = new User();
                        user.setUserId(cursor.getInt(cursor.getColumnIndex(User.Sql.userId)));
                        user.setUsername(cursor.getString(cursor.getColumnIndex(User.Sql.username)));
                        user.setPassword(cursor.getString(cursor.getColumnIndex(User.Sql.password)));
                        user.setNickname(cursor.getString(cursor.getColumnIndex(User.Sql.nickname)));
                        user.setLoginTime(cursor.getString(cursor.getColumnIndex(User.Sql.loginTime)));
                    }
                    cursor.close();
                    database.close();
                }
            }
        } catch (Exception e) {
            error(e, "queryData");
        }
        return user;
    }

    /**
     * 查询数据
     *
     * @return
     */
    private User queryData(int userId) {
        User user = null;
        try {
            if (mHelper != null) {
                SQLiteDatabase database = mHelper.getReadableDatabase();
                if (database != null) {
                    Cursor cursor = database.query(TABLE_NAME,
                            new String[]{User.Sql.userId, User.Sql.username, User.Sql.password, User.Sql.nickname, User.Sql.loginTime},
                            User.Sql.userId + "=?",
                            new String[]{userId + ""},
                            null, null, null);
                    while (cursor.moveToNext()) {
                        user = new User();
                        user.setUserId(cursor.getInt(cursor.getColumnIndex(User.Sql.userId)));
                        user.setUsername(cursor.getString(cursor.getColumnIndex(User.Sql.username)));
                        user.setPassword(cursor.getString(cursor.getColumnIndex(User.Sql.password)));
                        user.setNickname(cursor.getString(cursor.getColumnIndex(User.Sql.nickname)));
                        user.setLoginTime(cursor.getString(cursor.getColumnIndex(User.Sql.loginTime)));
                    }
                    cursor.close();
                    database.close();
                }
            }
        } catch (Exception e) {
            error(e, "queryData");
        }
        return user;
    }

    /**
     * 查询全部数据
     */
    public List<User> queryAllData() {
        List<User> dataList = new ArrayList<>();
        try {
            if (mHelper != null) {
                SQLiteDatabase database = mHelper.getReadableDatabase();
                if (database != null) {
                    Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        User user = new User();
                        user.setUserId(cursor.getInt(cursor.getColumnIndex(User.Sql.userId)));
                        user.setUsername(cursor.getString(cursor.getColumnIndex(User.Sql.username)));
                        user.setPassword(cursor.getString(cursor.getColumnIndex(User.Sql.password)));
                        user.setNickname(cursor.getString(cursor.getColumnIndex(User.Sql.nickname)));
                        user.setLoginTime(cursor.getString(cursor.getColumnIndex(User.Sql.loginTime)));

                        if (!dataList.contains(user)) {
                            dataList.add(user);
                        }
                    }

                    cursor.close();
                    database.close();
                }
            }
        } catch (Exception e) {
            error(e, "queryAllData");
        }
        return dataList;
    }

    /**
     * 更新数据
     */
    public User updateData(int userId, User user) {
        User resultUser = null;
        try {
            if (mHelper != null) {
                User queryData = queryData(userId);
                if (queryData != null) {

                    SQLiteDatabase database = mHelper.getWritableDatabase();
                    if (database != null) {
                        ContentValues values = new ContentValues();
                        values.put(User.Sql.username, user.getUsername());
                        values.put(User.Sql.password, user.getPassword());
                        values.put(User.Sql.nickname, user.getNickname());
                        values.put(User.Sql.loginTime, user.getLoginTime());

                        database.update(TABLE_NAME, values, User.Sql.userId + "=?", new String[]{userId + ""});

                        database.close();
                    }

                    queryData = queryData(userId);
                    if (queryData != null) {
                        resultUser = queryData;
                    }
                }
            }
        } catch (Exception e) {
            error(e, "updateData");
        }
        return resultUser;
    }

    /**
     * 更新数据
     */
    public User updateData(int userId, String key, String value) {
        User resultUser = null;
        try {
            if (mHelper != null) {
                User queryData = queryData(userId);
                if (queryData != null) {

                    SQLiteDatabase database = mHelper.getWritableDatabase();
                    if (database != null) {
                        ContentValues values = new ContentValues();
                        values.put(key, value);

                        database.update(TABLE_NAME, values, User.Sql.userId + "=?", new String[]{userId + ""});

                        database.close();
                    }

                    queryData = queryData(userId);
                    if (queryData != null) {
                        resultUser = queryData;
                    }
                }
            }
        } catch (Exception e) {
            error(e, "updateData");
        }
        return resultUser;
    }

    @Override
    public void log(String s, Object o) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(s, o);
        }
    }

    @Override
    public void warn(String s, String s1) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(s, s1);
        }
    }

    @Override
    public void error(Throwable throwable, String s) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, s);
        }
    }
}
