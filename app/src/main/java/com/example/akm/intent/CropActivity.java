package com.example.akm.intent;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;


import java.io.ByteArrayOutputStream;
import java.io.File;


public class CropActivity extends AppCompatActivity {


    CropImageView mCropView;

    Button b_crop;
    Uri contentUri;
    Bitmap bm;
    byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);


        b_crop=(Button) findViewById(R.id.btn_crop);
        mCropView = (CropImageView) findViewById(R.id.cropImageView);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

            File f = new File(textEntered);
            contentUri = Uri.fromFile(f);
            mCropView.load(contentUri).execute(mLoadCallback);
            mCropView.setCustomRatio(8,5);

        }

        b_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCropView.crop(contentUri)
                        .execute(new CropCallback() {
                            @Override public void onSuccess(Bitmap cropped) {

                                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                                cropped.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
                                byteArray = bStream.toByteArray();

                                Context context=CropActivity.this;
                                Class destinationActivity=DrawActivity.class;
                                Intent startDrawActivityIntent=new Intent(context,destinationActivity);
                                startDrawActivityIntent.putExtra("image",byteArray);
                                startActivity(startDrawActivityIntent);
                            }

                            @Override public void onError(Throwable e) {
                            }
                        });
                }
        });

    }

    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override public void onSuccess() {
        }

        @Override public void onError(Throwable e) {
        }
    };



}
