package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Window;

import com.pillowcase.plugin.utils.AssetsUtils;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： 本地配置文件处理
 */
public class LocalConfigNotch extends SimpleNotch {
    /**
     * 本地配置文件路径
     * default : assets/PhoneNotch.json
     */
    private String mLocalConfigFilePath = "PhoneNotch.json";
    private String mModel, mManufacturer;

    @Override
    public boolean isNotch(Activity activity) {
        try {
            Object data = AssetsUtils.getInstance().loadData(activity, AssetsUtils.Type.File, mLocalConfigFilePath);
            if (data instanceof String) {
                String dataS = (String) data;
                if (!TextUtils.isEmpty(dataS) && dataS.startsWith("[") && dataS.endsWith("]")) {
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        if (object.has("MODEL") && object.has("MANUFACTURER")) {
                            if (mModel.equals(object.getString("MODEL")) && mManufacturer.equals(object.getString("MANUFACTURER"))) {
                                PluginLog.log("Operation ==> Match Model : " + object);
                                if (object.has("isOffset") && object.getInt("isOffset") == 1) {
                                    return true;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    public boolean isNotch(Activity activity, String model, String manufacturer) {
        try {
            this.mModel = model;
            this.mManufacturer = manufacturer;

            return isNotch(activity);
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    @Override
    public boolean FitNotch(Window window) {
        try {

            return true;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    /**
     * @param path 配置的本地文件路径
     */
    public void setLocalConfigFilePath(String path) {
        this.mLocalConfigFilePath = path;
    }
}
