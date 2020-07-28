package com.pillowcase.game.plugin.detection.modules;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-07-28 17:01
 * Description ： 外挂APP相关信息
 */
public class GamePlugin {
    private String appName;
    private String packageName;

    @Override
    public String toString() {
        return "GamePlugin{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
