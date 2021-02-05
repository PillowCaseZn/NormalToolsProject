package com.pillowcase.plugin.modules;

import java.util.Objects;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 14:57
 * Description ： APP相关信息
 */
public class AppBean {
    private String appName;
    private String label;
    private String packageName;
    private String applicationName;
    private boolean isRunning = false;

    public AppBean() {
    }

    public AppBean(String appName, String packageName) {
        this.appName = appName;
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppBean{" + "\n" +
                "appName='" + appName + "'\n" +
                ", label='" + label + "'\n" +
                ", packageName='" + packageName + "'\n" +
                ", applicationName='" + applicationName + "'\n" +
                ", isRunning='" + isRunning + "'\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppBean)) return false;
        AppBean that = (AppBean) o;
        return Objects.equals(getAppName(), that.getAppName()) &&
                Objects.equals(getPackageName(), that.getPackageName()) &&
                Objects.equals(getApplicationName(), that.getApplicationName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppName(), getPackageName(), getApplicationName(), isRunning());
    }

    public boolean checkSimulator(String label, String packageName) {
        return this.label.equals(label) && this.packageName.equals(packageName);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
