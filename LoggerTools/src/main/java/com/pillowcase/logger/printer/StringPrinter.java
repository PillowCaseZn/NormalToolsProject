package com.pillowcase.logger.printer;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.pillowcase.logger.format.StringFormat;

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
            if (message.equals("") || TextUtils.isEmpty(message)) {
                return builder;
            }
            // 判断是否是Json
            if (message.startsWith("{") && message.endsWith("}")) {
                StringBuilder jsonBuilder = new JsonPrinter().printJsonObjectData(message);
                if (!jsonBuilder.toString().equals("") && !TextUtils.isEmpty(jsonBuilder.toString())) {
                    builder.append(jsonBuilder);
                    return builder.append(jsonBuilder).append(LINE_SEPARATOR);
                }
            }
            if (message.startsWith("[") && message.endsWith("]")) {
                StringBuilder jsonBuilder = new JsonPrinter().printJsonArrayData(message);
                if (!jsonBuilder.toString().equals("") && !TextUtils.isEmpty(jsonBuilder.toString())) {
                    builder.append(jsonBuilder);
                    return builder.append(jsonBuilder).append(LINE_SEPARATOR);
                }
            }
            // 判断是否是Html
            if (message.contains("<html>") && message.contains("</html>")) {
                Spanned html = Html.fromHtml(message);
                return builder.append(CONTENT_START_BORDER).append(html).append(LINE_SEPARATOR);
            }
            // 判断是否是Array
            if (object.getClass().isArray()) {
                return new ArrayPrinter().printData(object).append(LINE_SEPARATOR);
            }
            // 默认处理
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
