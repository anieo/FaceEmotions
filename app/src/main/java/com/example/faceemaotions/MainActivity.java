package com.example.faceemaotions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btnCamera);
        URL="http://192.168.1.110";
        image=(ImageView) findViewById(R.id.image);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,1);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap =(Bitmap)data.getExtras().get("data");
        if(bitmap!=null)
        {

            image.setImageBitmap(sendImage(bitmap));
            Uploader uploader = new Uploader(bitmap,"http://192.168.1.101");
        }
        else
        {
            Toast.makeText(this, "No image Tacken", Toast.LENGTH_SHORT).show();
        }

    }

    protected Bitmap sendImage(Bitmap bitmap)
    {
        return  bitmap;

    }
}
