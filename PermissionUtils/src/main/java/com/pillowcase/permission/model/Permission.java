package com.pillowcase.permission.model;

import android.Manifest;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-22 15:29
 * Description :
 */
public class Permission {
    //日历
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    //电话相关
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    //照相机
    public static final String CAMERA = Manifest.permission.CAMERA;
    //联系人
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    //地理位置
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_LOCATION_EXTRA_COMMANDS = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
    //录音
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    //手机信息
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String READ_PHONE_NUMBERS = Manifest.permission.READ_PHONE_NUMBERS;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String ANSWER_PHONE_CALLS = Manifest.permission.ANSWER_PHONE_CALLS;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    //传感器
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    //短信
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    //SD卡存储
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
}
