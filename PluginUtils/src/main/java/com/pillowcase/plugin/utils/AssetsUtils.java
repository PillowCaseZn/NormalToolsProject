package com.pillowcase.plugin.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pillowcase.plugin.interfaces.assets.IImageFilesListener;
import com.pillowcase.plugin.interfaces.assets.ITextFileListener;
import com.pillowcase.plugin.interfaces.assets.IVideoFileListener;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 14:25
 * Description ： assets 资源管理
 */
public class AssetsUtils {
    /**
     * 读取文本文件
     *
     * @param context    上下文
     * @param fileFolder 上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName   文件名 包含文件后缀 ".txt"
     */
    public static void loadTextFile(Context context, String fileFolder, String fileName, ITextFileListener listener) {
        try {
            String filePath = getFilePath(context, fileFolder, fileName);

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
                if (listener != null) {
                    listener.onTextFileResult(resultData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取图片文件
     *
     * @param context    上下文
     * @param fileFolder 上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName   文件名 包含文件后缀 ".png"、".jpg"、".jpeg"
     */
    public static void loadImageFile(Context context, String fileFolder, String fileName, IImageFilesListener listener) {
        try {
            String filePath = getFilePath(context, fileFolder, fileName);

            AssetManager manager = context.getAssets();
            if (manager != null) {
                InputStream resourceDataStream = manager.open(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(resourceDataStream);
                if (listener != null) {
                    listener.onImageFileResult(bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取音频视频文件
     *
     * @param context  上下文
     * @param folder   上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName 文件名 包含文件后缀 ".mp4"
     */
    public static void loadVideoFile(Context context, String folder, String fileName, IVideoFileListener listener) {
        try {
            String filePath = getFilePath(context, folder, fileName);

            AssetManager manager = context.getAssets();
            if (manager != null) {
                AssetFileDescriptor fileDescriptor = manager.openFd(filePath);

                if (listener != null) {
                    listener.onVideoFileResult(fileDescriptor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件在assets文件夹下的路径
     *
     * @param context    上下文
     * @param fileFolder 上级文件夹名称 如果文件保存在asset根目录下，则为空或""
     * @param fileName   文件名 包含文件后缀
     */
    public static String getFilePath(Context context, String fileFolder, String fileName) {
        StringBuilder filePath = new StringBuilder();
        try {
            AssetManager manager = context.getAssets();
            if (manager != null) {
                String[] list;
                if (fileFolder != null && !fileFolder.isEmpty()) {
                    list = manager.list(fileFolder);
                    filePath.append(fileFolder).append("/");
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
            e.printStackTrace();
        }
        return filePath.toString();
    }
}
