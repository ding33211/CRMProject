package com.soubu.crmproject.server;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.common.ApiConfig;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.PhoneUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 网络请求引擎类
 */
public class RetrofitService {

    //设缓存有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static OkHttpClient mOkHttpClient;

    private static boolean mNeedAuthentication = true;

    private RetrofitService() {
    }

    private volatile static RetrofitService instance = null;

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }

    private volatile static RetrofitApi api = null;

    public static RetrofitApi createApi(boolean needAuthentication) {
        mNeedAuthentication = needAuthentication;
        if (api == null) {
            synchronized (RetrofitService.class) {
                if (api == null) {
                    initOkHttpClient();
                    //特地对date做解析,防止出现date返回空字符串,无法解析的情况
                    GsonBuilder gBuilder = new GsonBuilder();
                    final DateFormat enUsFormat
                            = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US);
                    final DateFormat localFormat
                            = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
                    gBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

                        @Override
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            if(TextUtils.isEmpty(json.getAsString())){
                                return null;
                            }
                            try {
                                return localFormat.parse(json.getAsString());
                            } catch (ParseException ignored) {
                            }
                            try {
                                return enUsFormat.parse(json.getAsString());
                            } catch (ParseException ignored) {
                            }
                            try {
                                return ISO8601Utils.parse(json.getAsString(), new ParsePosition(0));
                            } catch (ParseException e) {
                                throw new JsonSyntaxException(json.getAsString(), e);
                            }
                        }
                    });
                    Gson gSon = gBuilder.create();

                    api = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(ApiConfig.API_HOST)
                            .addConverterFactory(GsonConverterFactory.create(gSon))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build().create(RetrofitApi.class);
                }
            }
        }
        return api;
    }

    // 配置OkHttpClient
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            File cacheFile = new File(CrmApplication.getContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            // 云端响应头拦截器，用来配置缓存策略
            Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!PhoneUtil.isConnected(CrmApplication.getContext())) {
                        if (mNeedAuthentication) {
                            request = request.newBuilder()
                                    .addHeader("uid",
                                            CrmApplication.getContext().getUid())
                                    .addHeader("sign", "")
                                    .cacheControl(CacheControl.FORCE_CACHE).build();
                        }
                    }
                    if (mNeedAuthentication) {
                        if (TextUtils.equals(request.method(), "GET")) {
                            request = request.newBuilder()
                                    .addHeader("uid",
                                            CrmApplication.getContext().getUid())
                                    .addHeader("sign", ConvertUtil.hmacsha256(request.url(), CrmApplication.getContext().getToken()))
                                    .build();
                        } else {
                            request = request.newBuilder()
                                    .addHeader("uid",
                                            CrmApplication.getContext().getUid())
                                    .build();
                        }
                    }
                    Response originalResponse = chain.proceed(request);
                    if (PhoneUtil.isConnected(CrmApplication.getContext())) {
                        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma").build();
                    } else {
                        return originalResponse.newBuilder().header("Cache-Control",
                                "public, only-if-cached," + CACHE_STALE_SEC)
                                .removeHeader("Pragma").build();
                    }
                }
            };
//            mOkHttpClient = new OkHttpClient();
//            mOkHttpClient.cache(cache);
//            mOkHttpClient.networkInterceptors().add(rewriteCacheControlInterceptor);
//            mOkHttpClient.interceptors().add(rewriteCacheControlInterceptor);
//            mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
            //okhttp 3
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder().cache(cache)
//                    .addNetworkInterceptor(rewriteCacheControlInterceptor)
                    .addInterceptor(rewriteCacheControlInterceptor)
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS).build();

        }
    }

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    @NonNull
    public static String getCacheControl() {
        return PhoneUtil.isConnected(CrmApplication.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }
}
