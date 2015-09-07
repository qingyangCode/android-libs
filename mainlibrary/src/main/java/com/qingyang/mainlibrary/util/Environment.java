package com.qingyang.mainlibrary.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public class Environment {

    public static String sDeviceIdentifier;
    public static String sChannelName;
    public static void init(Context context, String sportName) {
        sChannelName = ChannelUtil.getChannel(context);
        initDevice_identifier(context, sportName);
    }

    public static boolean isAppInForground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (processes == null) return false;
        for (ActivityManager.RunningAppProcessInfo tmp : processes) {
            if (tmp.processName.equals(context.getPackageName())) {
                if (tmp.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static  String getPackageName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception exp) {
        }
        return null;
    }

    public static boolean existPackage(Context context, String packageName) {
        if (packageName == null) return false;
        boolean exist = true;
        PackageManager pm = context.getPackageManager();
        try {
            pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            exist = false;
        }
        return exist;
    }

    public static boolean isPackageEnabled(Context context, String packageName) {
        if (packageName == null) return false;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            if (info != null) {
                return info.enabled;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public static String getPhoneType() {
        return Build.MODEL;
    }

    public static String getPhoneBrand() {
        return Build.BOARD;
    }

    public static int getRomSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getRomReleaseVesion() {
        return Build.VERSION.RELEASE;
    }

    public static String getPhoneInfo() {
        return "Device Info: " + getPhoneBrand() + "-" + getPhoneType() + "-" + getRomReleaseVesion()
                + "-"+ getRomSdkVersion();
    }

    public static long usedMemory() {
        final Runtime s_runtime = Runtime.getRuntime();
        return (s_runtime.totalMemory() - s_runtime.freeMemory());
    }

    public static int getPssMemory(Context context) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        android.os.Debug.MemoryInfo[] memoryInfoArray = am.getProcessMemoryInfo(
                new int[] { android.os.Process.myPid() });

        return memoryInfoArray[0].getTotalPss();
    }

    /**
     * Gets the total available memory (KB) on the system.
     *
     * @param context the context
     * @return the total available memory
     */
    public static long getTotalAvailableMemory(Context context) {
        try {
            final ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(outInfo);
            return (long)outInfo.availMem/1024;
        } catch(Exception e){
            return -1;
        }
    }

    /**
     * Gets the total memory (KB) on the system.
     *
     * @param context the context
     * @return the total memory
     */
    public static long getTotalMemory(Context context) {
        try {
            final ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(outInfo);
            if (getRomSdkVersion() >= Build.VERSION_CODES.JELLY_BEAN)
                return (long)outInfo.totalMem/1024;
            else
                return -1;
        } catch(Exception e){
            return -1;
        }
    }

    /**
     * Gets the available internal memory (ROM) size (KB).
     *
     * @return the available internal memory size
     */
    public long getAvailableInternalMemorySize() {
        File path = android.os.Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Gets the total internal memory (ROM) size (KB).
     *
     * @return the total internal memory size
     */
    public long getTotalInternalMemorySize() {
        File path = android.os.Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static void recycleDrawable(Drawable drawable) {
        if (drawable == null)
            return;
        Bitmap bitmap  = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    /**
     * when we define the layout through <include layout>, we may lose the child inner layout!
     */
    public static void unbindDrawablesAndRecyle(View view) {
        if (view == null)
        return;
        try {view.setOnClickListener(null);} catch (Throwable mayHappen) {};
        try {view.setOnCreateContextMenuListener(null);} catch (Throwable mayHappen) {};
        try {view.setOnFocusChangeListener(null);} catch (Throwable mayHappen) {};
        try {view.setOnKeyListener(null);} catch (Throwable mayHappen) {};
        try {view.setOnLongClickListener(null);} catch (Throwable mayHappen) {};
        try {view.setOnClickListener(null);} catch (Throwable mayHappen) {};
        try {view.setOnTouchListener(null);} catch (Throwable mayHappen) {};

        view.destroyDrawingCache();

        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
            view.setBackgroundDrawable(null);
        }
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            Drawable d = imageView.getDrawable();
            if (d!=null) d.setCallback(null);
            imageView.setImageDrawable(null);
            imageView.setImageBitmap(null);
        }

        if (view instanceof WebView) {
            WebView webView = (WebView) view;
            webView.stopLoading();
            webView.destroy();
        }

        if (view instanceof ImageButton) {
            ImageButton imageButton = (ImageButton) view;
            Drawable d = imageButton.getDrawable();
            if (d!=null) d.setCallback(null);
            imageButton.setImageDrawable(null);
            imageButton.setImageBitmap(null);
        }
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            try {listView.setAdapter(null);} catch (Throwable mayHappen) {};
            try {listView.setOnScrollListener(null);} catch (Throwable mayHappen) {};
            try {listView.setOnItemClickListener(null);} catch (Throwable mayHappen) {};
            try {listView.setOnItemLongClickListener(null);} catch (Throwable mayHappen) {};
            try {listView.setOnItemSelectedListener(null);} catch (Throwable mayHappen) {};
        }

        // view.destroyDrawingCache();
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawablesAndRecyle(((ViewGroup) view).getChildAt(i));
            }
            try {
                ((ViewGroup) view).removeAllViews();
            } catch (Throwable mayHappen) {
                // AdapterViews, ListViews and potentially other ViewGroups don't support the removeAllViews operation
            }
        }
    }


    static public int getPhoneVersion() {

        String deviceVersion = Build.VERSION.RELEASE;
        deviceVersion = deviceVersion.replace(".","");
        if (deviceVersion.length()<=2) {
            deviceVersion = deviceVersion+"0";
        }
        int version = 0;
        try {
            version = Integer.parseInt(deviceVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getApplicationVersion(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static void initDevice_identifier(Context context, String tag) {
        if (sDeviceIdentifier != null) {
            return;
        }

        final String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);

        final String deviceId = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

        final String serialId = Build.SERIAL;

        LogUtil.LOGD("androidId=", "" + androidId);
        LogUtil.LOGD("deviceId=", "" + deviceId);
        LogUtil.LOGD("serialId=", "" + serialId);

        try {

            sDeviceIdentifier = UUID.nameUUIDFromBytes(
                    (androidId + deviceId + serialId).getBytes("utf8"))
                    .toString()
                    + "/" + (tag != null ? tag.toLowerCase() : "");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }

}
