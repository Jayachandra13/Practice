package com.jc.practice.listener;

import com.jc.practice.model.Example;
import com.jc.practice.model.Person;
import com.jc.practice.model.Skin;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by jcskh on 29-04-2016.
 */
public interface RetrofitService {
    //String specific_url = "json/imdb_top_250.php";
    @GET
    Call<List<Example>> getData(@Url String offset);

    //String specific_url = "json/imdb_top_250.php?offse=0";
    @GET("/json/imdb_top_250.php/{offset}")
    Call<List<Example>> getDataWithParam(@Query("offset") int index);

    //String specific_url = "?offse=0";
    @GET
    Call<List<Example>> getData1(@Url String url, @Query("offset") String index);

    /*
     @GET
     Call<ResponseBody> getData1(@Url String url,@Query("offset") String index);
     */
    @GET("/practice/retrive.php")
    Call<List<Person>> getOrgFreeData();

    @GET("/practice/retrive.php")
    Call<ResponseBody> getOrgFreeResponseBody();

    @GET("/skincare.php")
    Call<List<Skin>> getSkinData();

    // @POST("/practice/insert.php")
    // Call<ResponseBody> postPersonData(@Body Person person);
    @FormUrlEncoded
    @POST("/insertsampleregister.php")
    Call<ResponseBody> postPersonData(@FieldMap Map<String, String> values);

    // @FormUrlEncoded
    @POST("/practice/insert.php")
    Call<ResponseBody> runInsertFile(@Body Person person);

    @POST("/practice/insertx.php")
    Call<ResponseBody> runInsertX();

    @POST("/practice/insert.php")
    Call<ResponseBody> runInsertObject(@Body Person person);

    @FormUrlEncoded
    @POST("/practice/insert.php")
    Call<ResponseBody> runInsertMap(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/practice/insert.php")
    Call<ResponseBody> runInsertFields(@Field("id") String id, @Field("fname") String fname, @Field("lname") String lname, @Field("age") String age, @Field("filepath") String filepath, @Field("dt") String dt);

    @Multipart
    @POST("/practice/upload_image.php")
    Call<ResponseBody> runUploadImage(@Part("id") RequestBody id, @Part("fname") RequestBody fname, @Part("lname") RequestBody lname, @Part("age") RequestBody age, @Part MultipartBody.Part filepath, @Part("dt") RequestBody dt);
}
