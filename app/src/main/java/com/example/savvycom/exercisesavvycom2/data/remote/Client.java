package com.example.savvycom.exercisesavvycom2.data.remote;

import android.content.Context;

import com.example.savvycom.exercisesavvycom2.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by savvycom on 12/21/2017.
 */

public class Client {
    private static Retrofit retrofit;
    private static Client instance;

    public static Client getInstance(Context context){
        if(instance==null){
            instance = new Client(context);
        }
        return instance;
    }

    private Client(Context context){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        });
        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).callFactory(httpClientBuilder.build()).build();
    }

    public <T> T create (final  Class<T> service){
        return retrofit.create(service);
    }
}
