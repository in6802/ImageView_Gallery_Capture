package com.example.edu.imageview_gallery_capture;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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
    final int LOAD_IMAGE = 1;
    final int IMAGE_CAPTURE = 2;

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
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.buttonGallery:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, LOAD_IMAGE);
                break;
            case R.id.buttonCapture:
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    Intent intent1 = new Intent();
                    intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1, IMAGE_CAPTURE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        int a = 1;
        switch(requestCode) {
            case LOAD_IMAGE:
                if (data != null) {
                    Uri seletedImage = data.getData();
                    InputStream inputStream = null;
                    try {
                        inputStream = this.getContentResolver().openInputStream(seletedImage);

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //decodeStream 일렬로 가지고 오는 비트를 재배치
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e("inputStream", "FileNotFoundException");
                    }
                }
                break;

            case IMAGE_CAPTURE:
                if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap)extras.get("data");
                    imageView.setImageBitmap(bitmap);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
