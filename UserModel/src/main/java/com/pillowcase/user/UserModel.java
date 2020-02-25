package com.pillowcase.user;

import android.app.Application;

import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.user.interfaces.IUserModelCallback;
import com.pillowcase.user.model.User;
import com.pillowcase.user.sqlite.dao.UserDao;
import com.pillowcase.user.utiils.TimeUtils;
import com.pillowcase.user.utiils.UserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : PillowCase
 * Create On   : 2020-02-25 12:00
 * Description :
 */
public class UserModel implements ILoggerOperation {
    private static UserModel instance;
    private LoggerUtils mLoggerUtils;
    private User mCurrentUser;

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class) {
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }

    public void initApplication(Application application, boolean isLogger) {
        try {
            if (mLoggerUtils == null) {
                mLoggerUtils = new LoggerUtils(isLogger, "UserModel");
            }
            UserDao.getInstance().initData(application.getApplicationContext(), isLogger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     * 用户账号5-18位中英文
     */
    public void login(String username, String password, final IUserModelCallback callback) {
        try {
            log("login", "UserName : " + username + " , PassWord : " + password);
            if (!UserUtils.check(username)) {
                callback.onFailed("账号不符合规则");
                return;
            }
            if (!UserUtils.check(password)) {
                callback.onFailed("密码不符合规则");
                return;
            }

            User queryData = UserDao.getInstance().queryData(username);
            if (queryData == null) {
                callback.onFailed("用户不存在");
                return;
            }
            if (!queryData.getPassword().equals(password)) {
                callback.onFailed("密码错误");
                return;
            }
            queryData.setLoginTime(TimeUtils.stampToDate(System.currentTimeMillis()));
            mCurrentUser = UserDao.getInstance().updateData(queryData.getUserId(), queryData);
            if (mCurrentUser != null) {
                callback.onSuccess(mCurrentUser);
            } else {
                callback.onFailed("数据查询失败");
            }
        } catch (Exception e) {
            error(e, "login");
            callback.onFailed(e.getMessage());
        }
    }

    /**
     * 注册
     */
    public void register(String username, String password, String repeatPassword, final IUserModelCallback callback) {
        try {
            log("register", "UserName : " + username + " , PassWord : " + password);
            if (!UserUtils.check(username)) {
                callback.onFailed("账号不符合规则");
                return;
            }
            if (!UserUtils.check(password)) {
                callback.onFailed("密码不符合规则");
                return;
            }
            if (!password.equals(repeatPassword)) {
                callback.onFailed("两次输入的密码不一致");
                return;
            }

            UserDao.getInstance().insertData(new User(username, password), new IUserModelCallback() {
                @Override
                public void onSuccess(User user) {
                    mCurrentUser = user;
                    callback.onSuccess(user);
                }

                @Override
                public void onFailed(Object error) {
                    callback.onFailed(error);
                }
            });
        } catch (Exception e) {
            error(e, "register");
            callback.onFailed(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    public void updateUser(int userId, User user, IUserModelCallback callback) {
        try {
            User updateData = UserDao.getInstance().updateData(userId, user);
            if (updateData != null) {
                mCurrentUser = updateData;
                callback.onSuccess(mCurrentUser);
            } else {
                callback.onFailed("数据更新失败");
            }
        } catch (Exception e) {
            error(e, "updateUser");
        }
    }

    /**
     * 更新用户信息
     */
    public void updateUser(int userId, String key, String value, IUserModelCallback callback) {
        try {
            User updateData = UserDao.getInstance().updateData(userId, key, value);
            if (updateData != null) {
                mCurrentUser = updateData;
                callback.onSuccess(mCurrentUser);
            } else {
                callback.onFailed("数据更新失败");
            }
        } catch (Exception e) {
            error(e, "updateUser");
        }
    }

    /**
     * @return 当前登录的账号信息
     */
    public User getCurrentUser() {
        if (mCurrentUser != null) {
            return mCurrentUser;
        } else {
            return null;
        }
    }

    /**
     * 获取用户信息
     */
    public User getUser(String username) {
        User user = null;
        try {
            User queryData = UserDao.getInstance().queryData(username);
            if (queryData != null) {
                user = queryData;
            }
        } catch (Exception e) {
            error(e, "getUser");
        }
        return user;
    }

    /**
     * 获取全部用户信息
     */
    public List<User> getAllUser() {
        List<User> dataList = new ArrayList<>();
        try {
            List<User> queryAllData = UserDao.getInstance().queryAllData();
            for (User user : queryAllData) {
                if (!dataList.contains(user)) {
                    dataList.add(user);
                }
            }
        } catch (Exception e) {
            error(e, "getAllUser");
        }
        return dataList;
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
