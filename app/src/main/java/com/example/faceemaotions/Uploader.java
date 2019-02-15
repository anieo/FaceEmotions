package com.example.faceemaotions;

import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class Uploader
{
    private static String BASE_URL="";
    private static Retrofit retrofit;
    private         Context context;
    public          String newImageURl;
    public          int modelResult;

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
        @POST("/")
        Call<JsonObject> uploadImage(@Part MultipartBody.Part file);
    }


    public void uploadToServer(String filePath,final ImageView image) {
        Retrofit retrofit = Uploader.getRetrofit(context);
        UploadAPIs uploadAPIs = retrofit.create(UploadAPIs.class);
        //Create a file object using file path
        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("Input", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        Call<JsonObject> call = uploadAPIs.uploadImage(part);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("user Info :" + response.body().getAsJsonObject());
                newImageURl= BASE_URL + response.body().getAsJsonObject().get("Data").getAsString();
                modelResult= Integer.parseInt(response.body().getAsJsonObject().get("Name").getAsString());

                Toast.makeText(context,Integer.toString(modelResult),Toast.LENGTH_LONG).show();

                Picasso.with(context).load(newImageURl).into(image);


            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context,"Request Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
