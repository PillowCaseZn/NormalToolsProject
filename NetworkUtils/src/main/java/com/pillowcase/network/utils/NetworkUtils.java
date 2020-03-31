package com.pillowcase.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.pillowcase.network.model.NetworkType;

import java.util.Objects;

/**
 * Author      : PillowCase
 * Create On   : 2020-03-09 11:49
 * Description :
 */
public class NetworkUtils {
    /**
     * 获取活动网络信息
     *
     * @param context
     * @return
     * @code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return Objects.requireNonNull(cm).getActiveNetworkInfo();
    }

    /**
     * 获取当前网络类型
     *
     * @param context
     * @return
     * @code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static NetworkType getNetworkType(Context context) {
        NetworkType netType = NetworkType.NETWORK_WITHOUT_NETWORK;
        try {
            NetworkInfo info = getActiveNetworkInfo(context);
            if (info != null && info.isAvailable()) {

                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    netType = NetworkType.NETWORK_WIFI;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    switch (info.getSubtype()) {

                        case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            netType = NetworkType.NETWORK_3G;
                            break;

                        case TelephonyManager.NETWORK_TYPE_LTE:
                        case TelephonyManager.NETWORK_TYPE_IWLAN:
                            netType = NetworkType.NETWORK_4G;
                            break;

                        case TelephonyManager.NETWORK_TYPE_GSM:
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netType = NetworkType.NETWORK_2G;
                            break;
                        default:

                            String subtypeName = info.getSubtypeName();
                            if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                    || subtypeName.equalsIgnoreCase("WCDMA")
                                    || subtypeName.equalsIgnoreCase("CDMA2000")) {
                                netType = NetworkType.NETWORK_3G;
                            } else {
                                netType = NetworkType.NETWORK_UNKNOWN;
                            }
                            break;
                    }
                } else {
                    netType = NetworkType.NETWORK_UNKNOWN;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netType;
    }
}
