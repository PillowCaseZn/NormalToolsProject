package com.pillowcase.identifier.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.identifier.models.ResultParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-18 11:01
 * Description :
 */
public class DeviceIdUtils {
    private static DeviceIdUtils instance;
    private LoggerUtils mLogger;

    //保存文件的路径
    private static final String CACHE_IMAGE_DIR = "array/cache/devices";
    //保存的文件 采用隐藏文件的形式进行保存
    private static final String DEVICES_FILE_NAME = "UniqueIdentifierData.DEVICES";

    private static final String SP_DEVICES_ID = "UniqueIdentifier-DevicesId";

    private DeviceIdUtils() {
        if (mLogger == null) {
            mLogger = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static DeviceIdUtils getInstance() {
        if (instance == null) {
            synchronized (DeviceIdUtils.class) {
                if (instance == null) {
                    instance = new DeviceIdUtils();
                }
            }
        }
        return instance;
    }

    @SuppressLint("NewApi")
    public String getDeviceId(final Context context, final ResultParams params) {
        mLogger.log("getDeviceId", "PackageName : " + context.getPackageName());
        String deviceId = "";
        try {
            //获取保存在SD中的 设备唯一标识符
            String sdDeviceId = readDeviceID(context, params);
            //获取缓存在SharePreference 里面的 设备唯一标识
            String cacheDeviceId = String.valueOf(SharedPreferencesUtils.getParam(context, SP_DEVICES_ID, ""));

            mLogger.log("readDeviceID", "sdDeviceId : " + sdDeviceId);
            mLogger.log("readDeviceID", "cacheDeviceId : " + cacheDeviceId);

            //判断 app 内部是否已经缓存,  若已经缓存则使用app 缓存的 设备id
            if (cacheDeviceId.equals("")) {
                deviceId = sdDeviceId;
            } else {
                //app 缓存的和SD卡中保存的不相同 以app 保存的为准, 同时更新SD卡中保存的 唯一标识符
                deviceId = cacheDeviceId;
                if (!cacheDeviceId.equals(sdDeviceId)) {
                    saveDeviceID(deviceId, context);
                }
            }
            SharedPreferencesUtils.setParam(context, SP_DEVICES_ID, deviceId);
        } catch (Exception e) {
            mLogger.error(e, "getDeviceId");
        }
        return deviceId;
    }

    @SuppressLint("NewApi")
    public String readDeviceID(Context context, ResultParams params) {
        mLogger.log("readDeviceID", "PackageName : " + context.getPackageName());
        String deviceId = "";
        try {
            if (SystemVersionUtils.getInstance().isVersionMoreThanM()) {
                if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //读取保存的在sd卡中的唯一标识符
                    deviceId = readDeviceID(context);
                }
            } else {
                //读取保存的在sd卡中的唯一标识符
                deviceId = readDeviceID(context);
            }
            //用于生成最终的唯一标识符
            StringBuffer s = new StringBuffer();
            //判断是否已经生成过,
            if (deviceId != null && !"".equals(deviceId)) {
                mLogger.log("readDeviceID", "deviceId : " + deviceId);
                return deviceId;
            }

            s.append(params.getIMEI());
            s.append(params.getMAC().replace(":", ""));
            s.append(params.getADNROID_ID());

            //如果以上搜没有获取相应的则自己生成相应的UUID作为相应设备唯一标识符
            if (s.length() <= 0) {
                UUID uuid = UUID.randomUUID();
                mLogger.log("readDeviceID", "uuid : " + uuid);
                s.append(uuid.toString().replace("-", ""));
            }
            mLogger.log("readDeviceID", "s : " + s);

            //为了统一格式对设备的唯一标识进行md5加密 最终生成32位字符串
            String md5 = getMD5(s.toString(), false);
            mLogger.log("readDeviceID", "md5 : " + md5);
            if (s.length() > 0) {
                if (SystemVersionUtils.getInstance().isVersionMoreThanM()) {
                    if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //持久化操作, 进行保存到SD卡中
                        saveDeviceID(md5, context);
                    }
                } else {
                    //持久化操作, 进行保存到SD卡中
                    saveDeviceID(md5, context);
                }
            }
            deviceId = md5;
        } catch (Exception e) {
            mLogger.error(e, "readDeviceID");
        }
        return deviceId;
    }


    /**
     * 读取固定的文件中的内容,这里就是读取sd卡中保存的设备唯一标识符
     *
     * @param context
     * @return
     */
    private String readDeviceID(Context context) {
        File file = getDevicesDir(context);
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader in = new BufferedReader(isr);
            int i;
            while ((i = in.read()) > -1) {
                buffer.append((char) i);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            mLogger.error(e, "readDeviceID");
            return null;
        }
    }

    /**
     * 保存 内容到 SD卡中,  这里保存的就是 设备唯一标识符
     *
     * @param str
     * @param context
     */
    private void saveDeviceID(String str, Context context) {
        File file = getDevicesDir(context);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            out.write(str);
            out.close();
        } catch (IOException e) {
            mLogger.error(e, "saveDeviceID");
        }
    }

    /**
     * 对挺特定的 内容进行 md5 加密
     *
     * @param message   加密明文
     * @param upperCase 加密以后的字符串是是大写还是小写  true 大写  false 小写
     * @return
     */
    private String getMD5(String message, boolean upperCase) {
        String md5str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = message.getBytes();
            byte[] buff = md.digest(input);
            md5str = bytesToHex(buff, upperCase);
        } catch (Exception e) {
            mLogger.error(e, "getMD5");
        }
        return md5str;
    }


    private String bytesToHex(byte[] bytes, boolean upperCase) {
        StringBuffer md5str = new StringBuffer();
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        if (upperCase) {
            return md5str.toString().toUpperCase();
        }
        return md5str.toString().toLowerCase();
    }

    /**
     * 统一处理设备唯一标识 保存的文件的地址
     *
     * @param context
     * @return
     */
    private File getDevicesDir(Context context) {
        File mCropFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cropdir = new File(Environment.getExternalStorageDirectory(), CACHE_IMAGE_DIR);
            if (!cropdir.exists()) {
                cropdir.mkdirs();
            }
            mCropFile = new File(cropdir, DEVICES_FILE_NAME); // 用当前时间给取得的图片命名
        } else {
            File cropdir = new File(context.getFilesDir(), CACHE_IMAGE_DIR);
            if (!cropdir.exists()) {
                cropdir.mkdirs();
            }
            mCropFile = new File(cropdir, DEVICES_FILE_NAME);
        }
        return mCropFile;
    }
}
