package com.pillowcase.emulator.model;

/**
 * Author      : PillowCase
 * Created On ： 2019-08-02 15:28
 * Description ：
 */
public class AppInfo {
    private String packageName;
    private String label;

    public AppInfo() {
    }

    public AppInfo(String packageName, String label) {
        this.packageName = packageName;
        this.label = label;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\n' +
                "label='" + label + '\n' +
                '}';
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
