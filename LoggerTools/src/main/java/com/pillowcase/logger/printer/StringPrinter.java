package com.pillowcase.logger.printer;

import android.text.TextUtils;

import com.pillowcase.logger.format.StringFormat;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:08
 * Description ： String 字符串
 */
public class StringPrinter extends LoggerPrinter {

    @Override
    public StringBuilder printData(Object object) {
        StringBuilder builder = new StringBuilder();
        try {
            String message = (String) object;
            // 判断是否是Json 、Xml 、Array
            if (message.equals("") || TextUtils.isEmpty(message)) {
                return builder;
            }
            // 判断是否是Json
            if (message.startsWith("{") && message.endsWith("}")) {
                JSONObject jsonObject = new JSONObject(message);
                StringBuilder jsonBuilder = new JsonPrinter().printData(jsonObject);
                return builder.append(jsonBuilder).append(LINE_SEPARATOR);
            }
            if (message.startsWith("[") && message.endsWith("]")) {
                JSONArray jsonArray = new JSONArray(message);
                StringBuilder jsonBuilder = new JsonPrinter().printData(jsonArray);
                return builder.append(jsonBuilder).append(LINE_SEPARATOR);
            }
            // 判断是否是Xml
            // 判断是否是Array
            String format = StringFormat.format(message, false);
            if (format != null && !format.equals("") && !TextUtils.isEmpty(format)) {
                builder.append(format);
            } else {
                builder.append(CONTENT_START_BORDER).append(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.append(LINE_SEPARATOR);
    }
}
