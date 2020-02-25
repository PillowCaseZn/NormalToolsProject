package com.pillowcase.user.model;

import androidx.annotation.Nullable;

/**
 * Author      : PillowCase
 * Create On   : 2020-02-25 12:05
 * Description : 用户实体类
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String nickname;
    private String loginTime;

    public static class Sql {
        public static String userId = "userId";
        public static String username = "username";
        public static String password = "password";
        public static String nickname = "nickname";
        public static String loginTime = "loginTime";
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.userId == user.getUserId();
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", loginTime='" + loginTime + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
