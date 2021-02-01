package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： MuMu模拟器
 */
public class MuMuSimulator extends SimpleSimulator {
    public static JSONObject isSimulator(DeviceBean deviceBeanInfo, List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            for (AppBean info : appBeanList) {
                if (info.getLabel().equals("应用中心") && info.getPackageName().equals("com.mumu.store")) {
                    object.put(Constant.Simulator.IS_SIMULATOR, true);
                    object.put(Constant.Simulator.SIMULATOR_NAME, "MuMu模拟器");
                    object.put(Constant.Simulator.SIMULATOR_INFO, info);
                }
                if (info.getLabel().equals("多开助手") && info.getPackageName().equals("com.netease.mumu.cloner")) {
                    object.put("Package", info);
                }
            }
            //手机的型号(Model):MuMu
            //渠道信息(Flavor):cancro-user
            if (deviceBeanInfo.getModel().equals("MuMu") && deviceBeanInfo.getFlavor().equals("cancro-user")) {
                object.put("处理器信息(Board)", deviceBeanInfo.getModel());
                object.put("渠道信息(Flavor)", deviceBeanInfo.getFlavor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void initData() {
        SimulatorName = "MuMu模拟器";
        AppLabelName = "应用中心";
        PackageName = "com.mumu.store";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            //手机的型号(Model):MuMu,渠道信息(Flavor):cancro-user
            if (deviceBean.getModel().equals("MuMu") && deviceBean.getFlavor().equals("cancro-user")) {
                dataObject.put(SIMULATOR_MODEL_INFO, deviceBean.getModel());
                dataObject.put(SIMULATOR_FLAVOR_INFO, deviceBean.getFlavor());
                return LoggerInfo(true, dataObject);
            }

            for (AppBean bean : installAppList) {
                if (bean.checkSimulator(AppLabelName, PackageName)) {
                    dataObject.put(SIMULATOR_APP_INFO, bean);
                    return LoggerInfo(true, dataObject);
                }
                if (bean.checkSimulator("多开助手", "com.netease.mumu.cloner")) {
                    dataObject.put(SIMULATOR_APP_INFO, bean);
                    return LoggerInfo(true, dataObject);
                }
            }

        } catch (Exception e) {
            PluginLog.error(e);
        }
        return LoggerInfo(false, dataObject);
    }
}
