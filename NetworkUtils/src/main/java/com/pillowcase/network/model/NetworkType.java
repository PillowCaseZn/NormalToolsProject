package com.pillowcase.network.model;

/**
 * Author      : PillowCase
 * Create On   : 2020-03-09 10:49
 * Description : 网络类型
 */
public enum NetworkType {
    NETWORK_WIFI("WiFi"),
    NETWORK_4G("4G"),
    NETWORK_3G("3G"),
    NETWORK_2G("2G"),
    NETWORK_UNKNOWN("Unknown"),
    NETWORK_WITHOUT_NETWORK("Without Network");

    private String desc;

    NetworkType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
