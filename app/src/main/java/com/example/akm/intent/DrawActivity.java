package com.example.akm.intent;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;




public class DrawActivity extends AppCompatActivity implements View.OnTouchListener {

    Button btn;
    ImageView i;
    Bitmap bmp;
    Bitmap alteredBitmap;
    byte[] byteArr;
    Canvas canvas;
    Paint paint;
    Matrix matrix;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        i = (ImageView) findViewById(R.id.image_view);
        btn=(Button) findViewById(R.id.button_save);

        final byte[][] byteArray = {getIntent().getByteArrayExtra("image")};
        bmp = BitmapFactory.decodeByteArray(byteArray[0], 0, byteArray[0].length);

        alteredBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp
                .getHeight(), bmp.getConfig());
        canvas = new Canvas(alteredBitmap);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        matrix = new Matrix();
        canvas.drawBitmap(bmp, matrix, paint);

        i.setImageBitmap(alteredBitmap);
        i.setOnTouchListener(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (alteredBitmap != null) {
                    ContentValues contentValues = new ContentValues(3);
                    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Draw On Me");

                    Uri imageFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    try {
                        OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
                        alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageFileOS);
                        Toast t = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT);
                        t.show();



                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                        alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
                        byteArr =bStream.toByteArray();

                        Context context=DrawActivity.this;
                        Class destinationActivity=ShowActivity.class;
                        Intent startShowActivityIntent=new Intent(context,destinationActivity);
                        startShowActivityIntent.putExtra("show", byteArr);
                        startActivity(startShowActivityIntent);

                    } catch (Exception e) {
                        Log.v("EXCEPTION", e.getMessage());
                    }
                }
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                i.invalidate();
                downx = upx;
                downy = upy;
                break;
            case MotionEvent.ACTION_UP:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                i.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }
}
