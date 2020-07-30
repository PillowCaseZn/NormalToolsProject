package com.pillowcase.utils.interfaces.assets;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:41
 * Description : assets文件夹内容读取返回接口
 */
public interface IAssetsTextFileListener {
    /**
     * @param data 文本文件的内容
     */
    void TextFileResult(String data);

}
