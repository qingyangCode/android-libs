package com.qingyang.mainlibrary.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qingyang.mainlibrary.BuildConfig;
import com.qingyang.mainlibrary.util.Environment;
import com.qingyang.mainlibrary.util.TimeUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Locale;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by QingYang on 15/9/8.
 */
public class MainApiManager {
    private static MainApiManager sMainApiManager;

    private Object mApiService;
    private Object mApiCustomerService;

    private MainApiManager() {

    }

    public static MainApiManager getInstance() {
        if (sMainApiManager == null)
            sMainApiManager = new MainApiManager();
        return sMainApiManager;
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

    public void initializeApiCustomerService(Class type, String serverUrl, final String userAgent) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(serverUrl).setConverter(new BaseConverter())
                .setRequestInterceptor(getRequestInterceptor(userAgent))
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setErrorHandler(new RetrofitErrorHandler())
                .build();

        mApiCustomerService = restAdapter.create(type);
    }

    public class BaseConverter implements Converter {
        @Override
        public Object fromBody(TypedInput body, Type type) throws ConversionException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            String response = "";
            try {
                while ((i = body.in().read()) != -1) {
                    baos.write(i);
                }
                response = baos.toString();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        public TypedOutput toBody(final Object object) {
            return new TypedOutput() {
                @Override
                public String fileName() {
                    return null;
                }

                @Override
                public String mimeType() {
                    return "String";
                }

                @Override
                public long length() {
                    return object == null ? 0 : object.toString().length();
                }

                @Override
                public void writeTo(OutputStream out) throws IOException {
                    out.write(object.toString().getBytes());
                }
            };
        }
    }


    public Object getApiService() {
        return mApiService;
    }

    public Object getApiCustomerService(){
        return mApiCustomerService;
    }
}
