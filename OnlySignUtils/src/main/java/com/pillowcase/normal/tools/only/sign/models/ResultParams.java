package com.pillowcase.normal.tools.only.sign.models;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-13 14:38
 * Description :
 */
public class ResultParams {
    private String IMEI;
    private String IMSI;
    private String MEID;
    private String MAC;
    private String ADNROID_ID;
    private String OAID;
    private String VAID;
    private String AAID;
    /**
     * 唯一表示符
     */
    private String DEVICE_ID;

    @Override
    public String toString() {
        return "{" + '\n' +
                "IMEI ='" + IMEI + '\n' +
                "IMSI ='" + IMSI + '\n' +
                "MEID ='" + MEID + '\n' +
                "MAC ='" + MAC + '\n' +
                "ADNROID_ID ='" + ADNROID_ID + '\n' +
                "OAID ='" + OAID + '\n' +
                "VAID ='" + VAID + '\n' +
                "AAID ='" + AAID + '\n' +
                "DEVICE_ID ='" + DEVICE_ID + '\n' +
                '}';
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getMEID() {
        return MEID;
    }

    public void setMEID(String MEID) {
        this.MEID = MEID;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getADNROID_ID() {
        return ADNROID_ID;
    }

    public void setADNROID_ID(String ADNROID_ID) {
        this.ADNROID_ID = ADNROID_ID;
    }

    public String getOAID() {
        return OAID;
    }

    public void setOAID(String OAID) {
        this.OAID = OAID;
    }

    public String getVAID() {
        return VAID;
    }

    public void setVAID(String VAID) {
        this.VAID = VAID;
    }

    public String getAAID() {
        return AAID;
    }

    public void setAAID(String AAID) {
        this.AAID = AAID;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }
}
