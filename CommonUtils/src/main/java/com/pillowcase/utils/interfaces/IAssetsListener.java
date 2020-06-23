package com.pillowcase.utils.interfaces;

import android.graphics.Bitmap;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:41
 * Description : assets文件夹内容读取返回接口
 */
public interface IAssetsListener {
    /**
     * @param data 文本文件的内容
     */
    void TextFileResult(String data);

    /**
     * @param bitmap 图片对象
     */
    void ImageFileResult(Bitmap bitmap);

    void VideoFileResult();
}
