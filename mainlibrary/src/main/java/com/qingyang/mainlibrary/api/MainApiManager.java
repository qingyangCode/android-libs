package com.qingyang.mainlibrary.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qingyang.mainlibrary.BuildConfig;
import com.qingyang.mainlibrary.util.Environment;
import com.qingyang.mainlibrary.util.TimeUtil;
import java.util.Locale;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by QingYang on 15/9/8.
 */
public class MainApiManager {
    private static MainApiManager sQLBApiService;

    private Object mApiService;

    private MainApiManager() {

    }

    public static MainApiManager getInstance() {
        if (sQLBApiService == null)
            sQLBApiService = new MainApiManager();
        return sQLBApiService;
    }


    //public

    public void initService(Class type, String serverUrl, final String userAgent) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        RequestInterceptor requestInterceptor = getRequestInterceptor(userAgent);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(serverUrl)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(
                        BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setClient(new OkClient())
                .setErrorHandler(new RetrofitErrorHandler()).build();

        mApiService = restAdapter.create(type);
    }

    private RequestInterceptor getRequestInterceptor(final String userAgent){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", userAgent);
                request.addHeader("Accept-Language",
                        Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
                request.addHeader("X-Timezone", TimeUtil.getTimeZoneInt());
                request.addHeader("X-Device-Identifier", Environment.sDeviceIdentifier);
            }
        };
        return requestInterceptor;
    }
}
