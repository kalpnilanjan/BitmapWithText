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
                config = Bitmap.Config.ARGB_4444;
            }
            if(caption != null){

                // Setting up TextPaint for drawing text on canvas
                float scale = getResources().getDisplayMetrics().density;
                TextPaint paint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                paint.setTextSize((int)scale*14);
                paint.setStyle(TextPaint.Style.FILL);
                paint.setColor(Color.BLACK);
                paint.setTextAlign(TextPaint.Align.LEFT);

                int textFontSmall = (int)scale*14;
                int textFontBig = (int) scale*16;
                /*
                 FIND HEIGHT OF THE TEXT
                // StaticLayout for multiline texts
                StaticLayout textLayout1 = new StaticLayout(
                        text1, paint, inputBitmap.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                int text1Height = textLayout1.getHeight();

                //Creating bitmap of text1
                Bitmap bitmapText1 = Bitmap.createBitmap(inputBitmap.getWidth(),text1Height,config);
                //Mutable Bitmap
                bitmapText1 = bitmapText1.copy(config, true);
                Canvas canvas1 = new Canvas(bitmapText1);

                //To Center Align the text
                //float x = (input1.getWidth() - canvas1.getWidth())/2;
                //float y = (input1.getHeight() - canvas1.getHeight())/2;

                //To ALign the text towards left
                float x = 0, y = 0;
                canvas1.save();
                canvas1.translate(x, y);
                textLayout1.draw(canvas1);
                canvas1.restore();

                StaticLayout textLayout2 = new StaticLayout(
                        text2, paint, inputBitmap.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                int text2Height = textLayout2.getHeight();
                Bitmap bitmapText2 = Bitmap.createBitmap(inputBitmap.getWidth(),text2Height,config);
                bitmapText2 = bitmapText2.copy(config,true);
                Canvas canvas2 = new Canvas(bitmapText2);
                x = 0;
                y = 0;
                canvas2.save();
                canvas2.translate(x,y);
                textLayout2.draw(canvas2);
                canvas2.restore();


                StaticLayout textLayout3 = new StaticLayout(
                        text3, paint, inputBitmap.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                int text3height = textLayout3.getHeight();
                Bitmap bitmapText3 = Bitmap.createBitmap(inputBitmap.getWidth(),text3height,config);
                bitmapText3 = bitmapText3.copy(config,true);
                Canvas canvas3 = new Canvas(bitmapText3);
                x =0; y = 0;
                canvas3.save();
                canvas3.translate(x,y);
                textLayout3.draw(canvas3);
                canvas3.restore();

                //Changing text size to 16dp
                paint.setTextSize((int) scale*16);

                StaticLayout textLayout4 = new StaticLayout(
                        text4, paint, inputBitmap.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                int text4Height = textLayout4.getHeight();
                Bitmap bitmapText4 = Bitmap.createBitmap(inputBitmap.getWidth(), text4Height,config);
                bitmapText4 = bitmapText4.copy(config,true);
                Canvas canvas4 = new Canvas(bitmapText4);
                x = 0; y = 0;
                canvas4.save();
                canvas4.translate(x,y);
                textLayout4.draw(canvas4);
                canvas4.restore();
                */

                Bitmap bitmapText1 = createTextBitmap(paint,inputBitmap.getWidth(),textFontSmall,text1,inputBitmap.getConfig());
                Bitmap bitmapText2 = createTextBitmap(paint,inputBitmap.getWidth(),textFontSmall,text2,inputBitmap.getConfig());
                Bitmap bitmapText3 = createTextBitmap(paint,inputBitmap.getWidth(),textFontSmall,text3,inputBitmap.getConfig());
                Bitmap bitmapText4 = createTextBitmap(paint,inputBitmap.getWidth(),textFontBig,text4,inputBitmap.getConfig());
                //Creating the final bitmap with the image and text
                //Total height of the bitmap
                int heightOutput = inputBitmap.getHeight() + bitmapText1.getHeight() + bitmapText2.getHeight()
                        + bitmapText3.getHeight() + bitmapText4.getHeight() + 50;

                outputBitmap = Bitmap.createBitmap(inputBitmap.getWidth(), heightOutput , config);
                newCanvas = new Canvas(outputBitmap);
                newCanvas.drawBitmap(inputBitmap,0,0,null);
                newCanvas.drawBitmap(bitmapText1,2,inputBitmap.getHeight()+10,null);
                newCanvas.drawBitmap(bitmapText2,2,inputBitmap.getHeight() + bitmapText1.getHeight()+10,null);
                newCanvas.drawBitmap(bitmapText3,2,inputBitmap.getHeight()  + bitmapText1.getHeight() + bitmapText2.getHeight()+10,null);
                newCanvas.drawBitmap(bitmapText4,2,inputBitmap.getHeight() + bitmapText1.getHeight()
                        + bitmapText2.getHeight() + bitmapText3.getHeight()+10,null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return outputBitmap;
    }

    public Bitmap createTextBitmap( TextPaint paint, int width, int textSize, String text, Bitmap.Config config){
        int x,y;
        paint.setTextSize((int) textSize);
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
