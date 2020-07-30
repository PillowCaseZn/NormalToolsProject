package com.pillowcase.utils.modules;

/**
 * Author      : PillowCase
 * Create On   : 2020-07-29 11:07
 * Description : 设备已安装APP相关信息
 */
public class InstallApp {
    private String appName;
    private String packageName;
    private String applicationName;
    private boolean isRunning = false;

    @Override
    public String toString() {
        return "InstallApp{" + '\n' +
                "appName='" + appName + '\n' +
                ", packageName='" + packageName + '\n' +
                ", applicationName='" + applicationName + '\n' +
                ", isRunning='" + isRunning + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof InstallApp) {
            InstallApp app = (InstallApp) o;
            return app.appName.equals(this.appName) && app.packageName.equals(this.packageName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = appName != null ? appName.hashCode() : 0;
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (applicationName != null ? applicationName.hashCode() : 0);
        return result;
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
