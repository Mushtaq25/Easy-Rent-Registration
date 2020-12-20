package com.example.easyrentregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class PhotoView extends AppCompatActivity {

    ImageView view_your_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        view_your_image = findViewById(R.id.view_your_image);
    }
}