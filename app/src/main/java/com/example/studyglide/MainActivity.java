package com.example.studyglide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.studyglide.life.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView imageView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);

        Glide.with(this).load("").into(imageView);

    }
}
