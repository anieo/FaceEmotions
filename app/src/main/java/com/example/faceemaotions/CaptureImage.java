package com.example.faceemaotions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureImage  {

    public ImageView image;
    public Intent intent ;
    private Context context;

    CaptureImage( ImageView images,Context context){
        image=images;
        intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.context=context;
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile=null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.android.fileprovider",photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                System.out.println(photoFile.getAbsolutePath());

            }

        };
    }
    //will be modified after Uploader is finished
    //should receive a new bitmap from the model
    protected void setImage(Bitmap nBitmap)
    {

        if(nBitmap!=null)
        {
            image.setImageBitmap(nBitmap);
        }
        else
        {
            Toast.makeText(context, "No image received", Toast.LENGTH_SHORT).show();
        }
    }
    //return the captured image
    protected Bitmap getImage()
    {
        Bitmap bitmap=null;
        try{
            Uri tmp = (Uri)intent.getExtras().get("output");
            bitmap =MediaStore.Images.Media.getBitmap(context.getContentResolver(),tmp);
        }
        catch(IOException ex){
            System.out.println(ex.toString()+"\nopps no image found");
        }
        return  bitmap;

    }
    //Create A file image to save the taken image in it to get a better Quality
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        System.out.println(storageDir.getAbsolutePath());

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
}
