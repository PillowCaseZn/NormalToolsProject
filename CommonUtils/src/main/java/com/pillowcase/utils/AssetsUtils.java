package com.pillowcase.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.impl.ILoggerOperation;

import java.io.InputStream;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:08
 * Description : 读取assets文件夹下的文件内容
 */
public class AssetsUtils implements ILoggerOperation {
    private LoggerUtils mLoggerUtils;

    public AssetsUtils() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    /**
     * 读取文本文件
     *
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名 包含后缀".txt"
     */
    public void loadTextFile(Context context, String folder, String fileName) {
        try {
            log("loadTextFile", "");

        } catch (Exception e) {
            error(e, "loadTextFile");
        }
    }

    /**
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名
     */
    public void load(Context context, String folder, String fileName) {
        try {
            log("load", "Folder : " + folder + " , File Name : " + fileName);
            AssetManager manager = context.getAssets();
            if (manager != null) {
                String[] list;
                StringBuilder filePath = new StringBuilder();
                if (folder != null && !folder.isEmpty()) {
                    list = manager.list(folder);
                    filePath.append(folder).append("/");
                } else {
                    list = manager.list("");
                }
                if (list != null && list.length > 0) {
                    for (String f : list) {
//                        log("load", "File : " + f);
                        if (f.equals(fileName)) {
                            filePath.append(fileName);
                            break;
                        }
                    }
                    log("load", "File Path : " + filePath);
                    if (fileName.endsWith(".txt")) {
                        InputStream resourceDataStream = manager.open(filePath.toString());
                        //获取文件的字节数
                        int length = resourceDataStream.available();
                        //创建byte数组
                        byte[] buffer = new byte[length];
                        //将文件中的数据写入到字节数组中
                        resourceDataStream.read(buffer);
                        resourceDataStream.close();
                        String resultData = new String(buffer);
                        log("load", "resultData : " + resultData);
                    }
                }
            }
        } catch (Exception e) {
            error(e, "load");
        }
    }

    @Override
    public void log(String method, Object object) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(method, object);
        }
    }

    @Override
    public void warn(String method, String message) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(method, message);
        }
    }

    @Override
    public void error(Throwable throwable, String method) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, method);
        }
    }
}
