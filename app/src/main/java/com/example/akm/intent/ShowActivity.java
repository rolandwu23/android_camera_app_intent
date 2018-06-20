package com.example.akm.intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        iv=(ImageView)findViewById(R.id.show_image_view);

        byte[] byteArray = getIntent().getByteArrayExtra("show");
       Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

       iv.setImageBitmap(bmp);

    }
}
