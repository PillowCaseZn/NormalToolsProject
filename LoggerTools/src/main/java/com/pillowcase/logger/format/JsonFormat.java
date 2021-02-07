package com.pillowcase.logger.format;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 18:11
 * Description ：
 */
public class JsonFormat extends LoggerFormat {
    /**
     * @param array JsonArray
     * @return 格式化JsonArray
     */
    public static String format(JSONArray array) {
        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < array.length(); i++) {
                builder.append(CONTENT_START_BORDER)
                        .append(DATA_SEPARATOR)
                        .append("{")
                        .append(LINE_SEPARATOR)
                        .append(format(array.getJSONObject(i) , DATA_SEPARATOR + DATA_SEPARATOR))
                        .append(LINE_SEPARATOR)
                        .append(CONTENT_START_BORDER)
                        .append(DATA_SEPARATOR)
                        .append("}");

                if (i != array.length() - 1) {
                    builder.append(",").append(LINE_SEPARATOR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * @param object JsonObject
     * @return 格式化JsonObject
     */
    public static String format(JSONObject object ,String separator) {
        StringBuilder builder = new StringBuilder();
        try {
            Iterator<String> iterator = object.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.append(CONTENT_START_BORDER)
                        .append(separator)
                        .append('\"')
                        .append(key)
                        .append('\"')
                        .append(":")
                        .append('\"')
                        .append(singleLineMaxFormat(object.getString(key) , CONTENT_START_BORDER + DATA_SEPARATOR , DOUBLE_DIVIDER.length() - 3))
                        .append('\"')
                        .append(',')
                        .append(LINE_SEPARATOR);
            }
            // 去除最后的逗号
            if (builder.length() > 2) {
                builder.deleteCharAt(builder.length() - 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
