package com.pillowcase.plugin.interfaces.assets;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 14:35
 * Description ： assets text内容读取
 */
public interface ITextFileListener {
    /**
     * @param data 文本文件的内容
     */
    void onTextFileResult(String data);
}
