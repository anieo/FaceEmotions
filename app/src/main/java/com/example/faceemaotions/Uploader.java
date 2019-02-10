package com.example.faceemaotions;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;

public class Uploader
{
    String imgString;
    String surl;




    public Uploader(Bitmap bitmap, String urlString ) {

        //The Image is now turned into byte array and encoded into 64 base string ready to be sent
        imgString= Base64.encodeToString(getByteArrayFromBitmap(bitmap),Base64.NO_WRAP);
        surl=urlString;
        try{

        }catch(Exception ex){
            Log.i("URL FORMATION","MALFORMATED URL");
        }

    }
    private void httpSender (){
        OutputStream out=null;
        try {
            URL url=new URL(surl);


        }catch(Exception ex){
            Log.i("URL FORMATION","MALFORMATED URL");
        }
    }
    private byte[] getByteArrayFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        return stream.toByteArray();
    }

    private Bitmap getBitmapFromByteArray(byte[] byteImage)
    {
        return BitmapFactory.decodeByteArray(byteImage,0,byteImage.length);
    }


}
