package com.jc.practice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jc.practice.ServiceCall.ServiceGenerator;
import com.jc.practice.adapter.RcvRetrofitAdapter;
import com.jc.practice.listener.RetrofitService;
import com.jc.practice.model.Example;
import com.jc.practice.model.Person;
import com.jc.practice.model.Skin;
import com.jc.practice.utils.ImageCompresser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnGetRetrofit)
    Button btnGetRetrofit;
    @Bind(R.id.rcvRetrofit)
    RecyclerView rcvRetrofit;
    RcvRetrofitAdapter rcvRetrofitAdapter;
    List<Example> list;
    //    String req_url="http://api.androidhive.info/json/imdb_top_250.php";
    String req_url = "http://api.androidhive.info/json/imdb_top_250.php?offset=0";
    String baseUrl = "http://api.androidhive.info/json/imdb_top_250.php";
    String specific_url = "json/imdb_top_250.php";
    @Bind(R.id.etFn)
    EditText etFn;
    @Bind(R.id.etLn)
    EditText etLn;
    @Bind(R.id.etAge)
    EditText etAge;
    @Bind(R.id.tvPath)
    TextView tvPath;
    @Bind(R.id.btnImage)
    Button btnImage;
    @Bind(R.id.btnVideo)
    Button btnVideo;
    @Bind(R.id.btnAudio)
    Button btnAudio;
    @Bind(R.id.btnDoc)
    Button btnDoc;
    @Bind(R.id.llPost)
    LinearLayout llPost;
    @Bind(R.id.btnPostRetrofit)
    Button btnPostRetrofit;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    Person person;
    int IMAGE_FROM_CAMERA = 1;
    int IMAGE_FROM_GALLERY = 2;
    int VIDEO_FROM_CAMERA = 3;
    int VIDEO_FROM_GALLERY = 4;
    int AUDIO_FROM_MICK = 5;
    int AUDIO_FROM_GALLERY = 6;
    int DOC = 7;
    String img_path = null;
    String video_path = null;
    String audio_path = null;
    String doc_path = null;
    @Bind(R.id.imgPreView)
    ImageView imgPreView;
    @Bind(R.id.videoPerView)
    VideoView videoPerView;
    File imageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        rcvRetrofit.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RetrofitActivity.this);
        rcvRetrofit.setLayoutManager(linearLayoutManager);
        rcvRetrofitAdapter = new RcvRetrofitAdapter(RetrofitActivity.this, list);
        rcvRetrofit.setAdapter(rcvRetrofitAdapter);
    }

    @OnClick({R.id.btnGetRetrofit, R.id.btnImage, R.id.btnVideo, R.id.btnAudio, R.id.btnDoc, R.id.btnPostRetrofit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetRetrofit:
                getOrgFreeData();
                break;
            case R.id.btnPostRetrofit:
                fetchPersonData();
                break;
            case R.id.btnImage:
                browseImg();
                break;
            case R.id.btnVideo:
                browseVideo();
                break;
            case R.id.btnAudio:
                browseAudio();
                break;
            case R.id.btnDoc:
                browseDoc();
                break;

        }
    }

    public void browseImg() {
        String[] img_options = {"Gallery", "Camera"};
        AlertDialog.Builder bldr = new AlertDialog.Builder(RetrofitActivity.this);
        ArrayAdapter<String> adap = new ArrayAdapter<>(RetrofitActivity.this, R.layout.li_alr_d_tv, img_options);
        bldr.setAdapter(adap, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    /*Intent int_img_gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(int_img_gallery, IMAGE_FROM_GALLERY);*/
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select:"), IMAGE_FROM_GALLERY);
                }
                if (which == 1) {
                    Intent int_img_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(int_img_camera, IMAGE_FROM_CAMERA);
                    if (int_img_camera.resolveActivity(getPackageManager()) != null) {
                        imageFile = createImageFile();
                        if (imageFile != null) {
                            Log.v("in_put_path", "" + imageFile);
                            int_img_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                            startActivityForResult(int_img_camera, IMAGE_FROM_CAMERA);
                        }
                    }
                }

            }
        });
        bldr.show();
    }

    public void browseVideo() {
        String[] img_options = {"Gallery", "Capture Video"};
        AlertDialog.Builder bldr = new AlertDialog.Builder(RetrofitActivity.this);
        ArrayAdapter<String> adap = new ArrayAdapter<>(RetrofitActivity.this, R.layout.li_alr_d_tv, img_options);
        bldr.setAdapter(adap, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent int_video_gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                    Intent int_video_gallery = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(int_video_gallery, VIDEO_FROM_GALLERY);
                }
                if (which == 1) {
                    Intent int_video_camera = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(int_video_camera, VIDEO_FROM_CAMERA);
                }
            }
        });
        bldr.show();
    }

    public void browseAudio() {
        String[] img_options = {"Audio List", "Record"};
        AlertDialog.Builder bldr = new AlertDialog.Builder(RetrofitActivity.this);
        ArrayAdapter<String> adap = new ArrayAdapter<>(RetrofitActivity.this, R.layout.li_alr_d_tv, img_options);
        bldr.setAdapter(adap, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent int_audio_gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//                    Intent int_audio_gallery = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(int_audio_gallery, AUDIO_FROM_GALLERY);
                }
                if (which == 1) {
                    Intent int_audio_camera = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    startActivityForResult(int_audio_camera, AUDIO_FROM_MICK);
                }
            }
        });
        bldr.show();
    }

    public void browseDoc() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
//        startActivityForResult(Intent.createChooser(intent, "Select text"), DOC);
        startActivityForResult(intent, DOC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_FROM_GALLERY && resultCode == RESULT_OK/* && data != null*/) {
            Uri selectedImageUri = data.getData();
            Log.v("selectedImageUri",""+selectedImageUri);
            String tempPath = getPath(selectedImageUri, RetrofitActivity.this);
            Log.v("tempPath",""+tempPath);
            String url = data.getData().toString();
            Log.v("url",""+url);
            if (url.startsWith("content://com.google.android.apps.photos.content")){
                try {
                    InputStream is = getApplicationContext().getContentResolver().openInputStream(selectedImageUri);
                    if (is != null) {
                        Bitmap pictureBitmap = BitmapFactory.decodeStream(is);
                        //You can use this bitmap according to your purpose or Set bitmap to imageview
                        imgPreView.setImageBitmap(pictureBitmap);
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            /*img_path= getPath(selectedImageUri);
            if(selectedImageUri!=null){

            }else{
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(selectedImageUri);
                    imgPreView.setImageBitmap(BitmapFactory.decodeStream(is));
                    img_path=selectedImageUri.getPath();
                    tvPath.setText(img_path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }*/
            /*String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            img_path=cursor.getString(column_index);
            tvPath.setText(img_path);*/
            /*img_path = ImageCompresser.compressImage(img_path, RetrofitActivity.this);
            displayImageForPreview(img_path);*/
            imgPreView.setVisibility(View.VISIBLE);
            videoPerView.setVisibility(View.GONE);
           /* String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
            int cursor_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            img_path = cursor.getString(cursor_index);
            tvPath.setText(img_path);
            img_path = ImageCompresser.compressImage(img_path, RetrofitActivity.this);
            displayImageForPreview(img_path);
            imgPreView.setVisibility(View.VISIBLE);
            videoPerView.setVisibility(View.GONE);*/
        }
        if (requestCode == IMAGE_FROM_CAMERA && resultCode == RESULT_OK/* && data != null*/) {
            Log.v("getPath", "" + imageFile.getPath());
            Log.v("getAbsolutePath", "" + imageFile.getAbsolutePath());
            img_path = imageFile.getAbsolutePath();
            tvPath.setText(img_path);
            img_path = ImageCompresser.compressImage(img_path, RetrofitActivity.this);
            displayImageForPreview(img_path);
            //imgPreView.setImageBitmap(BitmapFactory.decodeFile(img_path));
            imgPreView.setVisibility(View.VISIBLE);
            videoPerView.setVisibility(View.GONE);
           /* Bitmap bmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bmap, "Title", null);
            Cursor cursor = getContentResolver().query(Uri.parse(path), null, null, null, null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            img_path = cursor.getString(idx);
            Bitmap bmapd = BitmapFactory.decodeFile(img_path);
            Log.v("img_path", "" + img_path);
            tvPath.setText(img_path);
            imgPreView.setImageBitmap(bmapd);*/
        }
        if (requestCode == VIDEO_FROM_GALLERY && resultCode == RESULT_OK && data != null) {
            String[] projection = {MediaStore.Video.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
            int cursor_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            video_path = cursor.getString(cursor_index);
            tvPath.setText(video_path);
            MediaController media_Controller = new MediaController(RetrofitActivity.this);
            imgPreView.setVisibility(View.GONE);
            videoPerView.setVisibility(View.VISIBLE);
            videoPerView.setMediaController(media_Controller);
            videoPerView.setVideoPath(video_path);
            videoPerView.start();
        }
        if (requestCode == VIDEO_FROM_CAMERA && resultCode == RESULT_OK && data != null) {
            String[] projection = {MediaStore.Video.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
            int cursor_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            video_path = cursor.getString(cursor_index);
            tvPath.setText(video_path);
            MediaController media_Controller = new MediaController(RetrofitActivity.this);
            imgPreView.setVisibility(View.GONE);
            videoPerView.setVisibility(View.VISIBLE);
            videoPerView.setMediaController(media_Controller);
            videoPerView.setVideoPath(video_path);
            videoPerView.start();
        }
        if (requestCode == AUDIO_FROM_GALLERY && resultCode == RESULT_OK && data != null) {
            String[] projection = {MediaStore.Audio.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
            int cursor_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            audio_path = cursor.getString(cursor_index);
            tvPath.setText(audio_path);
            try {
                MediaPlayer mp = new MediaPlayer();
                mp.setDataSource(audio_path);
                mp.prepare();
                mp.start();
            } catch (IllegalArgumentException | SecurityException
                    | IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == AUDIO_FROM_MICK && resultCode == RESULT_OK && data != null) {
            String[] projection = {MediaStore.Audio.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
            int cursor_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            audio_path = cursor.getString(cursor_index);
            tvPath.setText(audio_path);
            try {
                MediaPlayer mp = new MediaPlayer();
                mp.setDataSource(audio_path);
                mp.prepare();
                mp.start();
            } catch (IllegalArgumentException | SecurityException
                    | IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == DOC && resultCode == RESULT_OK && data != null) {
            doc_path = data.getData().getPath();
            tvPath.setText(doc_path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getPath(Uri uri, Activity activity) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            cursor = activity.getContentResolver().query(uri, projection, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }
        return "";
    }
    private File createImageFile() {
        File file = null;
        try {
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Practice");
            if (!folder.exists()) {
                folder.mkdir();
            }
            file = new File(folder.getAbsolutePath() + "/Image_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");
            img_path = file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("createImageFileError", "" + e.getMessage());
        }
        return file;
    }

    public void displayImageForPreview(String path) {
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(this)
                .load(path);
        Glide.with(this)
                .load(path)
                .placeholder(R.drawable.launcher)
                .error(R.drawable.ic_up_arrow)
                .override(200, 150)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .thumbnail(thumbnailRequest)
                .into(imgPreView);
        //.thumbnail( 0.1f ) first method
        //NONE,SOURCE,RESULT,ALL default
        //.skipMemoryCache( true )//false as default
        //.asGif(); .asBitmap();
            /*Bitmap bmap = BitmapFactory.decodeFile(img_path);
            imgPreView.setImageBitmap(bmap);*/
    }

    public void getDataUsingRetrofit(String url) {
        RetrofitService retrofitService = ServiceGenerator.createService(RetrofitService.class);
//        Call<List<Example>> exampleList = retrofitService.getData(url);
//        Call<List<Example>> exampleList = retrofitService.getData1(specific_url,String.valueOf(40));
        Call<List<Example>> exampleList = retrofitService.getDataWithParam(60);
        exampleList.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                list.clear();
                list.addAll(response.body());
                rcvRetrofitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Log.v("Throwable", "" + t.toString());
            }
        });
    }

    public void getResponseBody() {
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
        Call<ResponseBody> person = retrofitService.getOrgFreeResponseBody();
        person.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.v("ResponseBody", "" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("Throwable", "" + t.getMessage());
            }
        });

    }

    public void getOrgFreeData() {
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
//        RetrofitService retrofitService = ServiceGenerator.createServiceAnil(RetrofitService.class);
        Call<List<Person>> person = retrofitService.getOrgFreeData();
        person.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                Log.v("response", "" + response.toString());
                List<Person> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    /*Log.v("Fname", "" + list.get(i).getFname());
                    Log.v("Lname", "" + list.get(i).getLname());
                    Log.v("Age", "" + list.get(i).getAge());
                    Log.v("Filepath", "" + list.get(i).getFilepath());
                    Log.v("Dt", "" + list.get(i).getDt());*/
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.v("Throwable", "" + t.getMessage());
            }
        });
    }

    public void getSkinData() {
        RetrofitService retrofitService = ServiceGenerator.createServiceSudha(RetrofitService.class);
        Call<List<Skin>> skin = retrofitService.getSkinData();
        skin.enqueue(new Callback<List<Skin>>() {
            @Override
            public void onResponse(Call<List<Skin>> call, Response<List<Skin>> response) {
                List<Skin> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    Log.v("Fname", "" + list.get(i).getId());
                    Log.v("Lname", "" + list.get(i).getSkincare());
                }
            }

            @Override
            public void onFailure(Call<List<Skin>> call, Throwable t) {
                Log.v("Throwable", "" + t.getMessage());
            }
        });
    }

    public void fetchPersonData() {
        String id = "1";
        String fn = etFn.getText().toString();
        String ln = etLn.getText().toString();
        String age = etAge.getText().toString();
//        String filepath = "File Path: File url";
        String filepath = img_path;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = sdf.format(new Date());
        Log.v("dt", "" + dt);
        if (!fn.isEmpty() && !ln.isEmpty() && !age.isEmpty() && !filepath.isEmpty()) {
            /*//Static insert
            callInsertX();
            //Working*/

            /*//Object of class type Insert
            Person person = new Person();
            person.setId(id);
            person.setFname(fn);
            person.setLname(ln);
            person.setAge(age);
            person.setFilepath(filepath);
            person.setDt(dt);
            callInsertObject(person);
            //Partially working(Sent to php but empty values inserting in Database)*/

            /*//Map insert
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("fname", fn);
            map.put("lname", ln);
            map.put("age", age);
            map.put("filepath", filepath);
            map.put("dt", dt);
            callInsertMap(map);
            //Working*/

            /*//Fields insert
            callInsertFields(id,fn,ln,age,filepath,dt);
            //Working*/
            if (filepath != null) {
                callUploadImage(id, fn, ln, age, filepath, dt);
            } else {
                callSnackBar("Select Image");
            }
        } else {
            callSnackBar("Fill all the fields");
        }


    }

    public void callUploadImage(String id, String fname, String lname, String age, String filepath, String dt) {

        RequestBody mId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody mFname = RequestBody.create(MediaType.parse("multipart/form-data"), fname);
        RequestBody mLname = RequestBody.create(MediaType.parse("multipart/form-data"), lname);
        RequestBody mAge = RequestBody.create(MediaType.parse("multipart/form-data"), age);

        File file = new File(filepath);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part mulPart = MultipartBody.Part.createFormData("filepath", file.getName(), requestBody);

        RequestBody mDt = RequestBody.create(MediaType.parse("multipart/form-data"), dt);
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.runUploadImage(mId, mFname, mLname, mAge, mulPart, mDt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.v("onResponseSuccess", "" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v("onResponseFails", "" + response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("onFailure", "" + t.getMessage());
            }
        });
    }

    public void callInsertFields(String id, String fname, String lname, String age, String filepath, String dt) {
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.runInsertFields(id, fname, lname, age, filepath, dt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.v("response", "isSuccessful()" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v("response", "isNotSuccessful()");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("Throwable", "" + t);
            }
        });
    }

    public void callInsertMap(Map<String, String> map) {
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.runInsertMap(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.v("response", "isSuccessful()" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v("response", "isNotSuccessful()");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("Throwable", "" + t);
            }
        });
    }

    public void callInsertObject(Person person) {
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.runInsertObject(person);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.v("response", "isSuccessful()" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v("response", "isNotSuccessful()");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("Throwable", "" + t);
            }
        });
    }

    public void callInsertX() {
        RetrofitService retrofitService = ServiceGenerator.createServiceOrgFree(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.runInsertX();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.v("response", "isSuccessful()" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v("response", "isNotSuccessful()");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("Throwable", "" + t);
            }
        });
    }

    public void callSnackBar(String msg) {
        final Snackbar snackbar = Snackbar.make(linearLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                etFn.requestFocus();
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.CYAN);
        snackbar.show();
    }

}
