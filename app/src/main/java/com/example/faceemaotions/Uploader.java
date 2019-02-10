package com.example.faceemaotions;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Uploader
{
    String imgString;
    String surl;
    String oImgString;



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
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            out = new BufferedOutputStream(conn.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.write(imgString);
            writer.flush();
            writer.close();
            out.close();

            conn.connect();
            int response = conn.getResponseCode();
            if( response== HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line=bufferedReader.readLine()) !=null)
                {
                    oImgString+=line;
                }
            }

        }catch(Exception ex){
            System.out.println(ex.getMessage());
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
