package com.qingyang.mainlibrary;

/**
 * Created by QingYang on 15/9/8.
 */
public interface MainConfiguration {
    int getLogLevel();

    String getServerUrl();

    String getUserAgent();

    Class getApiServiceType();

    boolean getEnableCrashReport();


    String getMATAdvertiserId();

    String getMATConversionId();

    int getDBVersion();
}
