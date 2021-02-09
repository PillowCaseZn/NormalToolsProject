package com.pillowcase.logger.printer;

import android.text.TextUtils;

import com.pillowcase.logger.format.JsonFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:08
 * Description ： Json Include JsonObject JsonArray
 */
public class JsonPrinter extends LoggerPrinter {
    public StringBuilder printJsonObjectData(String message) {
        try {
            JSONObject object = new JSONObject(message);
            return printData(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new StringBuilder();
    }

    public StringBuilder printJsonArrayData(String message) {
        try {
            JSONArray array = new JSONArray(message);
            return printData(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new StringBuilder();
    }

    @Override
    public StringBuilder printData(Object object) {
        StringBuilder builder = new StringBuilder();
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            String format = JsonFormat.format(jsonObject, DATA_SEPARATOR);
            if (!format.equals("") && !TextUtils.isEmpty(format)) {
                builder.append(CONTENT_START_BORDER)
                        .append("{")
                        .append(LINE_SEPARATOR)
                        .append(format)
                        .append(LINE_SEPARATOR)
                        .append(CONTENT_START_BORDER)
                        .append("}");
            } else {
                builder.append(CONTENT_START_BORDER).append("{}");
            }
            return builder.append(LINE_SEPARATOR);
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            if (jsonArray.length() == 0) {
                builder.append(CONTENT_START_BORDER)
                        .append("[]");
            } else {
                builder.append(CONTENT_START_BORDER)
                        .append("[")
                        .append(LINE_SEPARATOR)
                        .append(JsonFormat.format(jsonArray))
                        .append(LINE_SEPARATOR)
                        .append(CONTENT_START_BORDER)
                        .append("]");
            }
            return builder.append(LINE_SEPARATOR);
        }
        return builder;
    }
}
