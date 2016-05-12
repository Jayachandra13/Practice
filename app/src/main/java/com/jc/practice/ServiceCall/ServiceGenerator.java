package com.jc.practice.ServiceCall;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jcskh on 02-05-2016.
 */
public class ServiceGenerator {
    //android hive example
    public static String API_BASE_URL = "http://api.androidhive.info/";
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    //org free example
    public static String BASE_URL_ORG_FREE = "http://jayachandrajc.orgfree.com/";
    public static Retrofit.Builder builder_org_free = new Retrofit.Builder().baseUrl(BASE_URL_ORG_FREE).addConverterFactory(GsonConverterFactory.create());

    //org free example
    public static String BASE_URL_ORG_SUDHA = "http://sudhameruva.freeoda.com/";
    public static Retrofit.Builder builder_org_sudha = new Retrofit.Builder().baseUrl(BASE_URL_ORG_SUDHA).addConverterFactory(GsonConverterFactory.create());

    //org free example
    public static String BASE_URL_ORG_ANIL = "http://anilandroid.orgfree.com/Library/";
    public static Retrofit.Builder builder_org_anil = new Retrofit.Builder().baseUrl(BASE_URL_ORG_ANIL).addConverterFactory(GsonConverterFactory.create());

    //android hive createService example
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
    //org free createService example
    public static <S> S createServiceOrgFree(Class<S> serviceClass) {
        Retrofit retrofit = builder_org_free.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    //org free sudha createService example
    public static <S> S createServiceSudha(Class<S> serviceClass) {
        Retrofit retrofit = builder_org_sudha.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
    //org free anil createService example
    public static <S> S createServiceAnil(Class<S> serviceClass) {
        Retrofit retrofit = builder_org_anil.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
