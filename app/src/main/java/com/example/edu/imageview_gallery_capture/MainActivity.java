package com.example.edu.imageview_gallery_capture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonGallery, buttonCapture;
    ImageView imageView;
    int LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //design link
        buttonGallery = findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(this);
        buttonCapture = findViewById(R.id.buttonCapture);
        buttonCapture.setOnClickListener(this);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, LOAD_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(data != null){
            Uri seletedImage = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = this.getContentResolver().openInputStream(seletedImage);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e ("inputStream", "FileNotFoundException");

            }

        }
        /*switch(LOAD_IMAGE){
            case 1:
                break;
            case 2:
                break;
        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }
}
