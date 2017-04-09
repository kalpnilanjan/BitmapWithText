package com.example.android.createbitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class TextActivity extends AppCompatActivity {
    ImageView mImageView;
    Uri pathImage;
    String text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        mImageView = (ImageView) findViewById(R.id.bitmap_image_view);

        Intent intent = getIntent();
        pathImage = intent.getData();
        text1 = intent.getStringExtra("Text1");
        text2 = intent.getStringExtra("Text2");
        text3 = intent.getStringExtra("Text3");
        text4 = intent.getStringExtra("Text4");
        if(pathImage != null){
            Bitmap output = BitmapProcessing();
            if(output != null){
                mImageView.setImageBitmap(output);
            }else{
                Toast.makeText(TextActivity.this,"ERROR couldn't get the output." , Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(TextActivity.this,"ERROR source not found." , Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap BitmapProcessing(){
        Bitmap inputBitmap;
        Bitmap outputBitmap = null;
        Canvas newCanvas;
        String caption = text1;
        try {
             inputBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(pathImage));
            Bitmap.Config config = inputBitmap.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }
            if(caption != null){

                // Setting up TextPaint for drawing text on canvas
                float scale = getResources().getDisplayMetrics().density;
                TextPaint paint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                paint.setStyle(TextPaint.Style.FILL);
                paint.setColor(Color.BLACK);
                paint.setTextAlign(TextPaint.Align.LEFT);
                float textFontSmall = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 14,
                        getBaseContext().getResources().getDisplayMetrics() );
                float textFontBig = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 16,
                        getBaseContext().getResources().getDisplayMetrics());;

                Bitmap bitmapText1 = createTextBitmap(paint,inputBitmap.getWidth(),textFontSmall,text1,inputBitmap.getConfig());
                Bitmap bitmapText2 = createTextBitmap(paint,inputBitmap.getWidth(),textFontSmall,text2,inputBitmap.getConfig());
                Bitmap bitmapText3 = createTextBitmap(paint,inputBitmap.getWidth(),textFontSmall,text3,inputBitmap.getConfig());
                Bitmap bitmapText4 = createTextBitmap(paint,inputBitmap.getWidth(),textFontBig,text4,inputBitmap.getConfig());
                //Creating the final bitmap with the image and text
                //Total height of the bitmap
                int heightOutput = inputBitmap.getHeight() + bitmapText1.getHeight() + bitmapText2.getHeight()
                        + bitmapText3.getHeight() + bitmapText4.getHeight() + 60;

                outputBitmap = Bitmap.createBitmap(inputBitmap.getWidth(), heightOutput , config);
                newCanvas = new Canvas(outputBitmap);
                newCanvas.drawBitmap(inputBitmap, 0, 0, null);
                newCanvas.drawBitmap(bitmapText4, 2, inputBitmap.getHeight() + 10, null);
                newCanvas.drawBitmap(bitmapText1, 2, inputBitmap.getHeight() + bitmapText4.getHeight() + 15, null);
                newCanvas.drawBitmap(bitmapText2, 2, inputBitmap.getHeight() + bitmapText4.getHeight() + bitmapText1.getHeight() + 10, null);
                newCanvas.drawBitmap(bitmapText3, 2, inputBitmap.getHeight() + bitmapText4.getHeight()
                        + bitmapText1.getHeight() + bitmapText2.getHeight() + 10, null);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return outputBitmap;
    }

    public Bitmap createTextBitmap( TextPaint paint, int width, float textSize, String text, Bitmap.Config config){
        int x,y;
        paint.setTextSize(textSize);
        StaticLayout textLayout = new StaticLayout(text, paint,width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int textLayoutHeight = textLayout.getHeight();
        Bitmap outputBitmap = Bitmap.createBitmap(width,textLayoutHeight, config);
        outputBitmap = outputBitmap.copy(config,true);
        Canvas canvas = new Canvas(outputBitmap);
        x = 0; y = 0;
        canvas.save();
        canvas.translate(x,y);
        textLayout.draw(canvas);
        canvas.restore();
        return outputBitmap;
    }
}
