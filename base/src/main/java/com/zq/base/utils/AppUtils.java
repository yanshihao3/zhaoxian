package com.zq.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import static com.zq.base.utils.MD5Utils.md5;


/**
 * App相关的辅助类
 * Created by sen young on 2016/8/15 17:06.
 */
public class AppUtils {

    public static final String TAG = AppUtils.class.getSimpleName();

    private AppUtils() {
        /**cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get the version of the application
     *
     * @param mContext
     * @return version code.
     */
    public static int getAppVersionCode(Context mContext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionCode;
    }

    /**
     * 获得本机imei md5小写值
     *
     * @param context
     * @return
     */
    public static String getImeiMd5(Context context) {
        return md5(getImei(context)).toLowerCase();
    }

    /**
     * 获取mac
     *
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo().getMacAddress();
    }

    /**
     * 获得imei
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return new PackageInfo();
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL; // 手机型号
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机品牌：红米
     *
     * @return
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取手机号
     *
     * @param context
     * @return
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getPhoneNumber(Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number(); // 手机号码，有的可得，有的不可得
    }

    /**
     * 判断是否是模拟器
     *
     * @param context
     * @return
     */
    public static boolean isSimulator(Context context) {
        if (AntiEmulator.CheckDeviceIDS(context)) {
            Log.e(TAG, "CheckDeviceIDS");
            return true;
        }
        if (AntiEmulator.CheckImsiIDS(context)) {
            Log.e(TAG, "CheckImsiIDS");
            return true;
        }
        if (AntiEmulator.CheckPhoneNumber(context)) {
            Log.e(TAG, "CheckPhoneNumber");
            return true;
        }
        if (AntiEmulator.CheckEmulatorBuild()) {
            Log.e(TAG, "CheckEmulatorBuild");
            return true;
        }
//        if (AntiEmulator.CheckEmulatorFiles()) {
//            KLog.e("CheckEmulatorFiles");
//            return true;
//        }
        if (AntiEmulator.checkPipes()) {
            Log.e(TAG, "checkPipes");
            return true;
        }
        if (AntiEmulator.checkQEmuDriverFile()) {
            Log.e(TAG, "checkQEmuDriverFile");
            return true;
        }
        return false;
    }

    /**
     * 获取AndroidID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }

}
