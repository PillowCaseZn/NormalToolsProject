package com.pillowcase.logger.printer;

import com.pillowcase.logger.format.LoggerFormat;
import com.pillowcase.logger.format.StringFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:19
 * Description ： Map
 */
public class MapPrinter extends LoggerPrinter {
    @Override
    public StringBuilder printData(Object object) {
        StringBuilder builder = new StringBuilder(CONTENT_START_BORDER);

        Map map = (Map) object;
        ArrayList keyList = new ArrayList<>(map.keySet());
        ArrayList valueList = new ArrayList<>(map.values());

        builder.append("Map length : ")
                .append(map.size())
                .append(LINE_SEPARATOR)
                .append(MIDDLE_BORDER)
                .append(CONTENT_START_BORDER)
                .append("{")
                .append(LINE_SEPARATOR);

        for (int i = 0; i < keyList.size(); i++) {
            String key = "", value = "";
            if (ObjectToString(keyList.get(i)) == null) {
                key = keyList.get(i).toString();
            }else {
                key = ObjectToString(keyList.get(i));
            }
            if (ObjectToString(valueList.get(i)) == null) {
                value = valueList.get(i).toString();
            }else {
                value = ObjectToString(valueList.get(i));
            }
            if (key.contains("\n")) {
                key = key.replace("\n" ,"");
            }
            if (value.contains("\n")) {
                value = value.replace("\n" ,"");
            }

            String line = DATA_SEPARATOR + key + " = " + value;
            builder.append(CONTENT_START_BORDER)
                    .append(LoggerFormat.singleLineMaxFormat(line , CONTENT_START_BORDER + DATA_SEPARATOR , DOUBLE_DIVIDER.length() - 3))
                    .append(LINE_SEPARATOR);

            if (i != keyList.size() - 1) {
                builder.deleteCharAt(builder.lastIndexOf(LINE_SEPARATOR))
                        .append(",")
                        .append(LINE_SEPARATOR);
            }
        }


        builder.append(CONTENT_START_BORDER).append("}");
        return builder.append(LINE_SEPARATOR);
    }
}
