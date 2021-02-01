package com.pillowcase.plugin;

import android.app.Activity;
import android.os.Build;
import android.view.Window;

import com.pillowcase.plugin.interfaces.INotchCallback;
import com.pillowcase.plugin.modules.PhoneBean;
import com.pillowcase.plugin.notch.ApiNotch;
import com.pillowcase.plugin.notch.FlyMeNotch;
import com.pillowcase.plugin.notch.HuaWeiNotch;
import com.pillowcase.plugin.notch.LocalConfigNotch;
import com.pillowcase.plugin.notch.MiUiNotch;
import com.pillowcase.plugin.notch.OppoNotch;
import com.pillowcase.plugin.notch.SamSungNotch;
import com.pillowcase.plugin.notch.VivoNotch;
import com.pillowcase.plugin.utils.PluginLog;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 11:48
 * Description ： 全面屏配置，刘海屏、点滴屏界面处理
 */
public class NotchManager {
    private static NotchManager instance;

    private Activity mActivity;
    private Window mWindow;
    private INotchCallback mCallback;
    /**
     * 是否检测到全面屏
     */
    private boolean isNotchDetection = false;
    /**
     * 是否已经进行过处理
     */
    private boolean isFitNotched = false;
    private String mModel, mManufacturer;

    private ApiNotch mApiNotch;
    private LocalConfigNotch mLocalConfigNotch;
    private FlyMeNotch mFlyMeNotch;
    private HuaWeiNotch mHuaWeiNotch;
    private MiUiNotch mMiUiNotch;
    private OppoNotch mOppoNotch;
    private SamSungNotch mSamSungNotch;
    private VivoNotch mVivoNotch;

    private NotchManager() {
        try {
            mModel = Build.MODEL;
            mManufacturer = Build.MANUFACTURER.toLowerCase();

            if (mApiNotch == null) {
                mApiNotch = new ApiNotch();
            }
            if (mLocalConfigNotch == null) {
                mLocalConfigNotch = new LocalConfigNotch();
            }
            if (mFlyMeNotch == null) {
                mFlyMeNotch = new FlyMeNotch();
            }
            if (mHuaWeiNotch == null) {
                mHuaWeiNotch = new HuaWeiNotch();
            }
            if (mMiUiNotch == null) {
                mMiUiNotch = new MiUiNotch();
            }
            if (mOppoNotch == null) {
                mOppoNotch = new OppoNotch();
            }
            if (mSamSungNotch == null) {
                mSamSungNotch = new SamSungNotch();
            }
            if (mVivoNotch == null) {
                mVivoNotch = new VivoNotch();
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    public static NotchManager getInstance() {
        if (instance == null) {
            synchronized (NotchManager.class) {
                if (instance == null) {
                    instance = new NotchManager();
                }
            }
        }
        return instance;
    }

    public void init(Activity activity, INotchCallback callback) {
        try {
            this.mActivity = activity;
            this.mWindow = this.mActivity.getWindow();
            this.mCallback = callback;

            new Thread(() -> {
                mApiNotch.isNotch(mActivity, isNotch -> {
                    if (!isNotchDetection && isNotch) {
                        isNotchDetection = isNotch;
                        outPutCallback();
                    }
                    if (!isFitNotched && isNotch) {
                        fitNotch();
                    }
                });

                if (mManufacturer.equals(PhoneBean.PHONE_XIAOMI)) {
                    isNotchDetection = mMiUiNotch.isNotch(mActivity);
                }
                if (mManufacturer.equals(PhoneBean.PHONE_HUAWEI) || mManufacturer.equals(PhoneBean.PHONE_HONOR)) {
                    isNotchDetection = mHuaWeiNotch.isNotch(mActivity);
                }
                if (mManufacturer.equals(PhoneBean.PHONE_OPPO) || mManufacturer.equals(PhoneBean.PHONE_ONEPLUS) || mManufacturer.equals(PhoneBean.PHONE_REALME)) {
                    isNotchDetection = mOppoNotch.isNotch(mActivity);
                }
                if (mManufacturer.equals(PhoneBean.PHONE_VIVO)) {
                    isNotchDetection = mVivoNotch.isNotch(mActivity);
                }
                if (mManufacturer.equals(PhoneBean.PHONE_SAMSUNG)) {
                    isNotchDetection = mSamSungNotch.isNotch(mActivity);
                }
                if (mManufacturer.equals(PhoneBean.PHONE_MEIZU)) {
                    isNotchDetection = mFlyMeNotch.isNotch(mActivity);
                }
                if (!isNotchDetection) {
                    isNotchDetection = mLocalConfigNotch.isNotch(mActivity, mModel, mManufacturer);
                }

                outPutCallback();
                if (isNotchDetection) {
                    fitNotch();
                }
            }).start();
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    /**
     * @param path 配置的本地文件路径
     */
    public void setLocalConfigFilePath(String path) {
        this.mLocalConfigNotch.setLocalConfigFilePath(path);
    }

    private void outPutCallback() {
        try {
            this.mActivity.runOnUiThread(() -> mCallback.isNotch(isNotchDetection));
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    private void fitNotch() {
        try {
            if (isFitNotched) {
                PluginLog.log("Already Fit Notch");
                return;
            }

            if (Build.VERSION.SDK_INT >= 28) {
                mApiNotch.FitNotch(this.mWindow);
            }

            if (mManufacturer.equals(PhoneBean.PHONE_XIAOMI)) {
                isFitNotched = mMiUiNotch.FitNotch(this.mWindow);
            }
            if (mManufacturer.equals(PhoneBean.PHONE_HUAWEI) || mManufacturer.equals(PhoneBean.PHONE_HONOR)) {
                isFitNotched = mHuaWeiNotch.FitNotch(this.mWindow);
            }
            if (mManufacturer.equals(PhoneBean.PHONE_OPPO) || mManufacturer.equals(PhoneBean.PHONE_ONEPLUS) || mManufacturer.equals(PhoneBean.PHONE_REALME)) {
                isFitNotched = mOppoNotch.FitNotch(this.mWindow);
            }
            if (mManufacturer.equals(PhoneBean.PHONE_VIVO)) {
                isFitNotched = mVivoNotch.FitNotch(this.mWindow);
            }
            if (mManufacturer.equals(PhoneBean.PHONE_SAMSUNG)) {
                isFitNotched = mSamSungNotch.FitNotch(this.mWindow);
            }
            if (mManufacturer.equals(PhoneBean.PHONE_MEIZU)) {
                isFitNotched = mFlyMeNotch.FitNotch(this.mWindow);
            }
        } catch (Exception e) {
            isFitNotched = false;
            PluginLog.error(e);
        }
    }
}
