package com.pillowcase.plugin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 14:25
 * Description ： assets 资源读取
 */
public class AssetsUtils {
    private static AssetsUtils instance;

    public enum Type {
        File,
        Image,
        Video
    }

    private AssetsUtils() {
        try {

        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    public static AssetsUtils getInstance() {
        if (instance == null) {
            synchronized (AssetsUtils.class) {
                if (instance == null) {
                    instance = new AssetsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * @param type     读取的资源类型
     * @param context  上下文
     * @param filePath 资源路径
     * @return assets 资源读取
     */
    public Object loadData(Context context, Type type, String filePath) {
        try {
            AssetManager manager = context.getAssets();

            switch (type) {
                case File:
                    InputStream resourceDataStream = manager.open(filePath);
                    //获取文件的字节数
                    int length = resourceDataStream.available();
                    //创建byte数组
                    byte[] buffer = new byte[length];
                    //将文件中的数据写入到字节数组中
                    resourceDataStream.read(buffer);
                    resourceDataStream.close();
                    return new String(buffer, StandardCharsets.UTF_8);
                case Image:
                    resourceDataStream = manager.open(filePath);
                    return BitmapFactory.decodeStream(resourceDataStream);
                case Video:
                    return manager.openFd(filePath);
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return null;
    }
}
