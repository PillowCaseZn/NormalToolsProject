package com.pillowcase.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.impl.ILoggerOperation;
import com.pillowcase.utils.interfaces.assets.IAssetImageFilesListener;
import com.pillowcase.utils.interfaces.assets.IAssetsTextFileListener;
import com.pillowcase.utils.interfaces.assets.IAssetsVideoFileListener;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:08
 * Description : 读取assets文件夹下的文件内容  (文本文件、图片、音频视频文件)
 */
public class AssetsUtils implements ILoggerOperation {
    private static final AssetsUtils ourInstance = new AssetsUtils();
    private LoggerUtils mLoggerUtils;

    public static AssetsUtils getInstance() {
        return ourInstance;
    }

    private AssetsUtils() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(false, getClass().getSimpleName());
        }
    }

    /**
     * 读取文本文件
     *
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名 包含文件后缀 ".txt"
     */
    public void loadTextFile(Context context, String folder, String fileName, IAssetsTextFileListener listener) {
        try {
            log("loadTextFile", "Folder : " + folder + " , File Name : " + fileName);
            String filePath = getFilePath(context, folder, fileName);
            log("loadTextFile", "File Path : " + filePath);

            AssetManager manager = context.getAssets();
            if (manager != null) {
                InputStream resourceDataStream = manager.open(filePath);
                //获取文件的字节数
                int length = resourceDataStream.available();
                //创建byte数组
                byte[] buffer = new byte[length];
                //将文件中的数据写入到字节数组中
                resourceDataStream.read(buffer);
                resourceDataStream.close();
                String resultData = new String(buffer, StandardCharsets.UTF_8);

                listener.TextFileResult(resultData);
            }
        } catch (Exception e) {
            error(e, "loadTextFile");
        }
    }

    /**
     * 读取图片文件
     *
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名 包含文件后缀 ".png"、".jpg"、".jpeg"
     */
    public void loadImageFile(Context context, String folder, String fileName, IAssetImageFilesListener listener) {
        try {
            log("loadImageFile", "Folder : " + folder + " , File Name : " + fileName);
            String filePath = getFilePath(context, folder, fileName);
            log("loadImageFile", "File Path : " + filePath);

            AssetManager manager = context.getAssets();
            if (manager != null) {
                InputStream resourceDataStream = manager.open(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(resourceDataStream);

                listener.ImageFileResult(bitmap);
            }
        } catch (Exception e) {
            error(e, "loadImageFile");
        }
    }

    /**
     * 读取音频视频文件
     *
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名 包含文件后缀 ".mp4"
     */
    public void loadVideoFile(Context context, String folder, String fileName, IAssetsVideoFileListener listener) {
        try {
            log("loadVideoFile", "Folder : " + folder + " , File Name : " + fileName);
            String filePath = getFilePath(context, folder, fileName);
            log("loadVideoFile", "File Path : " + filePath);

            AssetManager manager = context.getAssets();
            if (manager != null) {
                AssetFileDescriptor fileDescriptor = manager.openFd(filePath);

                listener.VideoFileResult(fileDescriptor);
            }
        } catch (Exception e) {
            error(e, "loadVideoFile");
        }
    }

    /**
     * 获取文件在assets文件夹下的路径
     *
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名 包含文件后缀
     */
    private String getFilePath(Context context, String folder, String fileName) {
        StringBuilder filePath = new StringBuilder();
        try {
            log("getFilePath", "Folder : " + folder + " , File Name : " + fileName);

            AssetManager manager = context.getAssets();
            if (manager != null) {
                String[] list;
                if (folder != null && !folder.isEmpty()) {
                    list = manager.list(folder);
                    filePath.append(folder).append("/");
                } else {
                    list = manager.list("");
                }
                if (list != null && list.length > 0) {
                    for (String f : list) {
                        if (f.equals(fileName)) {
                            filePath.append(fileName);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            error(e, "getFilePath");
        }
        return filePath.toString();
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
