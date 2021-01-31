package com.pillowcase.plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

import com.pillowcase.plugin.interfaces.IGamePlugInDetectionCallback;
import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.utils.AppUtils;
import com.pillowcase.plugin.utils.AssetsUtils;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 13:52
 * Description ： 游戏外挂检测
 */
public class GamePlugInManager {
    @SuppressLint("StaticFieldLeak")
    private static GamePlugInManager instance;

    private Activity mActivity;
    /**
     * 本地配置文件路径
     * default : assets/GamePlugin.json
     */
    private String mLocalConfigFilePath = "GamePlugin.json";

    /**
     * mPluginAppList : 游戏外挂软件列表
     * mInstallPluginList : 检测到已安装的游戏外挂软件列表
     * mInstallAppList : 设备已安装的软件列表
     */
    private List<AppBean> mPluginAppList, mInstallPluginList, mInstallAppList;

    public GamePlugInManager() {
        try {
            if (mPluginAppList == null) {
                mPluginAppList = new ArrayList<>();
                initDefaultGamePluginData();
            }
            if (mInstallPluginList == null) {
                mInstallPluginList = new ArrayList<>();
            }
            if (mInstallAppList == null) {
                mInstallAppList = new ArrayList<>();
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    public static GamePlugInManager getInstance() {
        if (instance == null) {
            synchronized (GamePlugInManager.class) {
                if (instance == null) {
                    instance = new GamePlugInManager();
                }
            }
        }
        return instance;
    }

    /**
     * 调用检测接口
     */
    public void detection(Activity activity, final IGamePlugInDetectionCallback callback) {
        try {
            this.mActivity = activity;

            // 检测包体是否已经损坏
            boolean isDamage = findClassFile();
            if (isDamage) {
                PluginLog.log("Package is Damage");
                callback.onResult(true, this.mInstallPluginList);
            } else {
                loadLocalConfigData();

                this.mInstallAppList = AppUtils.getInstalledAppList(this.mActivity);

                if (this.mInstallPluginList.size() != 0) {
                    for (AppBean bean : this.mInstallAppList) {
                        AppBean checkBean = new AppBean(bean.getAppName(), bean.getPackageName());
                        if (this.mPluginAppList.contains(checkBean) && !this.mInstallPluginList.contains(checkBean)) {
                            this.mInstallPluginList.add(checkBean);
                        }
                    }
                }

                PluginLog.log("Install Plugin App : " + Arrays.toString(this.mInstallPluginList.toArray()));
                if (this.mInstallPluginList.size() == 0) {
                    callback.onResult(false, new ArrayList<AppBean>());
                } else {
                    callback.onResult(true, this.mInstallPluginList);
                }
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    /**
     * @param path 配置的本地文件路径
     */
    public void setLocalConfigFilePath(String path) {
        this.mLocalConfigFilePath = path;
        PluginLog.log("Change Local Config File Path , new Path is : " + this.mLocalConfigFilePath);
    }

    /**
     * 加载本地配置文件
     */
    private void loadLocalConfigData() {
        try {
            PluginLog.log("Read Local Config File Path is " + this.mLocalConfigFilePath);

            final String APP_NAME = "AppName";
            final String PACKAGE_NAME = "PackageName";

            Object data = AssetsUtils.getInstance().loadData(this.mActivity, AssetsUtils.Type.File, this.mLocalConfigFilePath);

            if (data instanceof String) {
                String dataS = (String) data;
                if (!TextUtils.isEmpty(dataS) && dataS.startsWith("[") && dataS.endsWith("]")) {
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        AppBean bean = new AppBean();
                        if (object.has(APP_NAME)) {
                            bean.setAppName(object.getString(APP_NAME));
                        }
                        if (object.has(PACKAGE_NAME)) {
                            bean.setPackageName(object.getString(PACKAGE_NAME));
                        }
                        if (!mPluginAppList.contains(bean)) {
                            PluginLog.log("Add Local Config App Info ---> " + bean);
                            mPluginAppList.add(bean);
                        }
                    }
                }
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    /**
     * 加载默认的游戏插件数据
     */
    private void initDefaultGamePluginData() {
        try {
            //默认配置的外挂软件
            mPluginAppList.add(new AppBean("万能加速器", "com.gh.universalaccelerator"));
            mPluginAppList.add(new AppBean("游戏蜂窝", "com.cyjh.gundam"));
            mPluginAppList.add(new AppBean("八门神器", "com.zhangkongapp.joke.bamenshenqi"));
            mPluginAppList.add(new AppBean("叉叉助手", "com.zhushou.cc"));
            mPluginAppList.add(new AppBean("烧饼修改器助手", "com.shavingxiugaiqi"));
            mPluginAppList.add(new AppBean("烧饼游戏大师", "org.sbtools.master"));
            mPluginAppList.add(new AppBean("葫芦侠", "com.huluxia.gametools"));
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    private boolean findClassFile() {
        try {
            Class.forName("com.gh.shell.FakeApplication");
            return true;
        } catch (Exception e) {
            PluginLog.error(e);
            return false;
        }
    }
}
