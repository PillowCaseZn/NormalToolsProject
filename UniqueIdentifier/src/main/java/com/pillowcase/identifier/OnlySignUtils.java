package com.pillowcase.identifier;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.JLibrary;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;
import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.identifier.impl.ISupportListener;
import com.pillowcase.identifier.models.ResultParams;
import com.pillowcase.identifier.utils.DeviceIdUtils;
import com.pillowcase.identifier.utils.SystemVersionUtils;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-13 11:45
 * Description :
 */
public class OnlySignUtils implements IIdentifierListener {
    private static OnlySignUtils instance;
    private ResultListener mListener;
    private LoggerUtils mLogger;

    private static final String SIM_IMEI = "getImei";
    private static final String SIM_DEVICEID = "getDeviceId";


    public OnlySignUtils() {
        if (mLogger == null) {
            mLogger = new LoggerUtils(true, "OnlySignUtils");
        }
    }

    public static OnlySignUtils getInstance() {
        if (instance == null) {
            synchronized (OnlySignUtils.class) {
                if (instance == null) {
                    instance = new OnlySignUtils();
                }
            }
        }
        return instance;
    }

    public void getOnlySign(Context context, ISupportListener listener) {
        mLogger.log("getOnlySign", "");
        try {
            this.mListener = new ResultListener(context, listener);

            if (SystemVersionUtils.getInstance().isVersionMoreThanQ()) {
                int state = MdidSdkHelper.InitSdk(context, true, this);
                switch (state) {
                    case ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT:
                        mLogger.log("getOnlySign", "不支持的设备");
                        mListener.result(null);
                        break;
                    case ErrorCode.INIT_ERROR_LOAD_CONFIGFILE:
                        mLogger.log("getOnlySign", "加载配置文件出错");
                        mListener.result(null);
                        break;
                    case ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT:
                        mLogger.log("getOnlySign", "不支持的设备厂商");
                        mListener.result(null);
                        break;
                    case ErrorCode.INIT_ERROR_RESULT_DELAY:
                        mLogger.log("getOnlySign", "获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程");
                        mListener.result(null);
                        break;
                    case ErrorCode.INIT_HELPER_CALL_ERROR:
                        mLogger.log("getOnlySign", "反射调用出错");
                        mListener.result(null);
                        break;
                }
            } else {
                mListener.result(null);
            }
        } catch (Exception e) {
            mLogger.error(e, "getOnlySign");
        }
    }

    public void loadLibrary(Context context) {
        mLogger.log("loadLibrary", "");
        try {
            if (SystemVersionUtils.getInstance().isVersionMoreThanQ()) {
                mLogger.log("loadLibrary", "InitEntry");
                JLibrary.InitEntry(context);
            }
        } catch (Exception e) {
            mLogger.error(e, "loadLibrary");
        }
    }

    @SuppressLint("NewApi")
    private String getImsi(Context context) {
        mLogger.log("getImsi", "");
        String data = "";
        try {
            if (SystemVersionUtils.getInstance().isVersionMoreThanQ()) {
                return "";
            } else {
                if (SystemVersionUtils.getInstance().isVersionMoreThanM()) {
                    if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        data = getPhoneImsi(context);
                    }
                } else {
                    data = getPhoneImsi(context);
                }
            }
        } catch (Exception e) {
            mLogger.error(e, "getImsi");
        }
        return data;
    }

    @SuppressLint("NewApi")
    private String getImei(Context context) {
        mLogger.log("getImei", "");
        String data = "000000000000000";
        try {
            if (SystemVersionUtils.getInstance().isVersionMoreThanQ()) {
                return data;
            } else {
                List<String> imeiState = new ArrayList<>();
                if (SystemVersionUtils.getInstance().isVersionMoreThanM()) {
                    if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        imeiState.addAll(getPhoneImeiState(context));
                    }
                } else {
                    imeiState.addAll(getPhoneImeiState(context));
                }
                mLogger.log("getImei", "imeiState : " + imeiState.toString());
                if (imeiState.size() > 0) {
                    String[] imeiStrings = new String[3];
                    for (int i = 0; i < imeiState.size(); i++) {
                        imeiStrings[i] = imeiState.get(i);
                    }
                    data = imeiStrings[0] + "_" + imeiStrings[1] + "_" + imeiStrings[2];
                }
            }
        } catch (Exception e) {
            mLogger.error(e, "getImei");
        }
        return data;
    }

    @SuppressLint("NewApi")
    private String getMeid(Context context) {
        mLogger.log("getMeid", "");
        String data = "00000000000000";
        try {
            if (SystemVersionUtils.getInstance().isVersionMoreThanQ()) {
                return data;
            } else {
                if (SystemVersionUtils.getInstance().isVersionMoreThanM()) {
                    if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        data = getPhoneMeid(context);
                    }
                } else {
                    data = getPhoneMeid(context);
                }
            }
        } catch (Exception e) {
            mLogger.error(e, "getMeid");
        }
        return data;
    }

    @SuppressLint("HardwareIds")
    private String getMac(Context context) {
        mLogger.log("getMac", "");
        String data = "02:00:00:00:00:02";
        try {
            if (SystemVersionUtils.getInstance().isVersionMoreThanM()) {
                StringBuffer buf = new StringBuffer();
                NetworkInterface networkInterface = NetworkInterface.getByName("eth1");

                if (networkInterface == null) {
                    networkInterface = NetworkInterface.getByName("wlan0");
                }
                if (networkInterface != null) {
                    byte[] addr = networkInterface.getHardwareAddress();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    data = buf.toString();
                }
            } else {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager != null) {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    data = wifiInfo.getMacAddress();
                } else {
                    mLogger.log("getMac", "WifiManager is Null");
                }
            }
        } catch (Exception e) {
            mLogger.error(e, "getMac");
        }
        return data;
    }

    @SuppressLint("HardwareIds")
    private String getAndroidId(Context context) {
        mLogger.log("getAndroidId", "");
        String data = "";
        try {
            data = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (!TextUtils.isEmpty(data)) {
                data = data.toLowerCase();
            }
        } catch (Exception e) {
            mLogger.error(e, "getAndroidId");
        }
        return data;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getPhoneImsi(Context context) {
        mLogger.log("getPhoneImsi", "");
        String imsi = "";
        try {

            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                imsi = manager.getSubscriberId();
            }
        } catch (Exception e) {
            mLogger.error(e, "getPhoneImsi");
        }
        return imsi;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getPhoneMeid(Context context) {
        mLogger.log("getPhoneMeid", "");
        String meid = "";
        try {

            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                Class clazz = manager.getClass();
                Method getDevice = clazz.getDeclaredMethod(SIM_DEVICEID, int.class);
                //MEID
                meid = String.valueOf(getDevice.invoke(manager, TelephonyManager.PHONE_TYPE_CDMA));
            }
        } catch (Exception e) {
            mLogger.error(e, "getPhoneMeid");
        }
        return meid;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private List<String> getPhoneImeiState(Context context) {
        mLogger.log("getPhoneImeiState", "");
        List<String> imeiList = new ArrayList<>();
        try {
            String imei = "";

            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                imei = manager.getDeviceId();
                if (!"".equals(imei)) {
                    imeiList.add(manager.getDeviceId());
                }
            }
            Class clazz = null;
            if (manager != null) {
                clazz = manager.getClass();
                Method getImei = null;
                getImei = clazz.getDeclaredMethod(SIM_IMEI, int.class);

                imei = String.valueOf(getImei.invoke(manager, 0));
                if (!"".equals(imei) && !imeiList.contains(imei)) {
                    imeiList.add(imei);
                }
                imei = String.valueOf(getImei.invoke(manager, 1));
                if (!"".equals(imei) && !imeiList.contains(imei)) {
                    imeiList.add(imei);
                }
            }
            if (manager != null) {
                clazz = manager.getClass();
                Method getDevice = clazz.getDeclaredMethod(SIM_DEVICEID, int.class);
                imei = String.valueOf(getDevice.invoke(manager, TelephonyManager.PHONE_TYPE_NONE));
                if (!"".equals(imei) && !imeiList.contains(imei)) {
                    imeiList.add(imei);
                }
                //MEID
                imei = String.valueOf(getDevice.invoke(manager, TelephonyManager.PHONE_TYPE_CDMA));
                if (!"".equals(imei) && !imeiList.contains(imei)) {
                    imeiList.add(imei);
                }

                imei = String.valueOf(getDevice.invoke(manager, TelephonyManager.PHONE_TYPE_GSM));
                if (!"".equals(imei) && !imeiList.contains(imei)) {
                    imeiList.add(imei);
                }

                imei = String.valueOf(getDevice.invoke(manager, TelephonyManager.PHONE_TYPE_SIP));
                if (!"".equals(imei) && !imeiList.contains(imei)) {
                    imeiList.add(imei);
                }
            }
        } catch (Exception e) {
            mLogger.error(e, "getPhoneImeiState");
        }
        return imeiList;
    }

    private class ResultListener implements ISupportListener {
        private Context context;
        private ISupportListener resultListener;

        public ResultListener(Context context, ISupportListener resultListener) {
            this.context = context;
            this.resultListener = resultListener;
        }

        @Override
        public void result(ResultParams data) {
            ResultParams params = new ResultParams();
            if (data != null) {
                params.setOAID(data.getOAID());
                params.setVAID(data.getVAID());
                params.setAAID(data.getAAID());
            }
            params.setIMEI(getImei(context));
            params.setIMSI(getImsi(context));
            params.setMEID(getMeid(context));
            params.setMAC(getMac(context));
            params.setADNROID_ID(getAndroidId(context));

            params.setDEVICE_ID(DeviceIdUtils.getInstance().getDeviceId(context, params));
            resultListener.result(params);
        }
    }


    @Override
    public void OnSupport(boolean b, IdSupplier idSupplier) {
        mLogger.log("OnSupport", "");
        try {
            if (idSupplier == null) {
                mLogger.log("OnSupport", "IdSupplier is Null");
                return;
            }
            if (b) {
                ResultParams params = new ResultParams();
                params.setOAID(idSupplier.getOAID());
                params.setVAID(idSupplier.getVAID());
                params.setAAID(idSupplier.getAAID());
                mLogger.log("OnSupport", "ResultParams : " + params);
                if (mListener != null) {
                    mListener.result(params);
                }
            } else {
                mLogger.log("OnSupport", "不支持补充设备标识符获取");
            }
        } catch (Exception e) {
            mLogger.error(e, "OnSupport");
        }
    }
}
