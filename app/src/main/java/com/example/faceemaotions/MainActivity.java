package com.example.faceemaotions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    String URL;
    CaptureImage captureImage;
    Context context;
    Uploader uploader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button cameraBtn = (Button)findViewById(R.id.btnCamera);
        final EditText editText=(EditText)findViewById(R.id.IP);
        URL="http://192.168.1.110:5000";

        context=this;
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //class that Handels the the image
                captureImage=new CaptureImage(context);

                //start the camera intent
                startActivityForResult(captureImage.intent, 0);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                URL=s.toString();
                System.out.println(URL);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    // onActivityResult Handels the results of the Camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        uploader=new Uploader(this,URL);
        uploader.uploadToServer(captureImage.path,(ImageView) findViewById(R.id.image));



    }




}
