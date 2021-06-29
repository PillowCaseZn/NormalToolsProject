package com.pillowcase.models;

import android.os.Build;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:12
 * Description ： 当前设备信息
 */
public class DeviceBean {
    /**
     * 获取设备品牌
     */
    private String brand = Build.BRAND;
    /**
     * 获取手机的型号 设备名称。
     */
    private String model = Build.MODEL;
    /**
     * 设备的唯一标识。由设备的多个信息拼接合成。
     */
    private String fingerPrint = Build.FINGERPRINT;
    /**
     * 获取设备制造商
     */
    private String manufacturer = Build.MANUFACTURER;
    /**
     * 基带信息
     */
    private String baseBand;
    /**
     * 处理器信息
     */
    private String board = Build.BOARD;
    /**
     * 主板平台
     */
    private String platform;
    /**
     * 渠道信息
     */
    private String flavor;

    @Override
    public String toString() {
        return "Device{" + "\n" +
                "设备品牌(Brand):'" + brand + "'\n" +
                "手机的型号(Model):'" + model + "'\n" +
                "唯一标识(FingerPrint):'" + fingerPrint + "'\n" +
                "设备制造商(Manufacturer):'" + manufacturer + "'\n" +
                "基带信息(BaseBand):'" + baseBand + "'\n" +
                "处理器信息(Board):'" + board + "'\n" +
                "主板平台(Platform):'" + platform + "'\n" +
                "渠道信息(Flavor):'" + flavor + "'\n" +
                '}';
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBaseBand() {
        return baseBand;
    }

    public void setBaseBand(String baseBand) {
        this.baseBand = baseBand;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
