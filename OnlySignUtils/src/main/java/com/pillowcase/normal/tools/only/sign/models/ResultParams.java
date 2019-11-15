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
    private String OAID;
    private String VAID;
    private String AAID;

    @Override
    public String toString() {
        return "{" + '\n' +
                "IMEI ='" + IMEI + '\n' +
                "IMSI ='" + IMSI + '\n' +
                "MEID ='" + MEID + '\n' +
                "OAID ='" + OAID + '\n' +
                "VAID ='" + VAID + '\n' +
                "AAID ='" + AAID + '\n' +
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
}
