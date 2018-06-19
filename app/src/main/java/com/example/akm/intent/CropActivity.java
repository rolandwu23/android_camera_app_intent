package com.example.akm.intent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yashoid.instacropper.InstaCropperView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class CropActivity extends AppCompatActivity {

    InstaCropperView mInstaCropper;
    ImageView i;
    Button b_crop;
    Bitmap bm;
    byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        mInstaCropper=(InstaCropperView)findViewById(R.id.instacropper);
        b_crop=(Button)findViewById(R.id.btn_crop);
        i=(ImageView)findViewById(R.id.crop_image_view);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

            File f = new File(textEntered);
            Uri contentUri = Uri.fromFile(f);
            mInstaCropper.setImageUri(contentUri);
            mInstaCropper.setRatios(3,2,5);
        }

        b_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInstaCropper.crop(
                        View.MeasureSpec.makeMeasureSpec(1024, View.MeasureSpec.AT_MOST),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        new InstaCropperView.BitmapCallback() {

                            @Override
                            public void onBitmapReady(Bitmap bitmap) {


                                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                byte[] byteArray = bStream.toByteArray();

                Context context=CropActivity.this;
                Class destinationActivity=DrawActivity.class;
               Intent startDrawActivityIntent=new Intent(context,destinationActivity);
                startDrawActivityIntent.putExtra("image",byteArray);
                startActivity(startDrawActivityIntent);
                            }

                        }
                );


//                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.PNG, 100, bStream);
//                byte[] byteArray = bStream.toByteArray();

//                Context context=CropActivity.this;
//                Class destinationActivity=DrawActivity.class;
//
//                Intent startDrawActivityIntent=new Intent(context,destinationActivity);
//                startDrawActivityIntent.putExtra("image",byteArray);
//                startActivity(startDrawActivityIntent);
            }
        });


    }


}
