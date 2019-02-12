package com.example.faceemaotions;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class Uploader
{
    private static String BASE_URL="";
    private static Retrofit retrofit;
    private         Context context;

    Uploader(Context context,String url){
        this.context=context;
        this.BASE_URL=url;

    }
    public static Retrofit getRetrofit(Context context){
        if(retrofit == null){
            OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public interface UploadAPIs {
        @Multipart
        @POST("/upload")
        Call uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);
    }


    private void uploadToServer(String filePath) {
        Retrofit retrofit = Uploader.getRetrofit(context);
        UploadAPIs uploadAPIs = retrofit.create(UploadAPIs.class);
        //Create a file object using file path
        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        Call call = uploadAPIs.uploadImage(part, description);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call call, IOException t) {
            }
        });
    }


}
