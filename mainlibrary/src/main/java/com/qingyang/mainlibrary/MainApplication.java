package com.qingyang.mainlibrary;

import android.app.Application;
import android.content.Context;
import com.qingyang.mainlibrary.api.MainApiManager;
import com.qingyang.mainlibrary.util.LogUtil;

/**
 * Created by QingYang on 15/9/8.
 */
public abstract class MainApplication extends Application {
    private static Context sContext;
    private static MainApplication sMainApplication;
    public abstract MainConfiguration getConfiguration();

    @Override public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        MainConfiguration configuration = getConfiguration();
        LogUtil.setLogLevel(configuration.getLogLevel());
        sMainApplication = this;

        MainApiManager.getInstance().initService(configuration.getApiServiceType(), configuration.getServerUrl(), configuration.getUserAgent());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MainApplication getMainApplication() {
        return sMainApplication;
    }

    public static Context getContext() {
        return sContext;
    }

    public abstract void onUnAuthorized();
}
