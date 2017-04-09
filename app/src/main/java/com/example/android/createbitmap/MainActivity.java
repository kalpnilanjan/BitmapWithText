package com.example.android.createbitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 20;
    ImageView imgView;
    Button selectImageBtn, generateButton;
    EditText editText1, editText2, editText3, editText4;
    Bitmap bitmap;
    Intent finalIntent;
    Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.imageViewDisplay);
        selectImageBtn = (Button) findViewById(R.id.selectImageButton);
        editText1 = (EditText) findViewById(R.id.edit_text_1);
        editText2 = (EditText) findViewById(R.id.edit_text_2);
        editText3 = (EditText) findViewById(R.id.edit_text_3);
        editText4 = (EditText) findViewById(R.id.edit_text_4);
        generateButton = (Button) findViewById(R.id.generate_button);
        selectImageBtn.setOnClickListener(this);
        generateButton.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE) {
                path = data.getData();
                InputStream mInputStream;
                try {
                    mInputStream = getContentResolver().openInputStream(path);
                    bitmap = BitmapFactory.decodeStream(mInputStream);
                    imgView.setImageBitmap(bitmap);
                    //Toast.makeText(MainActivity.this,path + " ",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(MainActivity.this, "FILE NOT FOUND", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.generate_button:
                String text1 = "", text2 = "", text3 = "", text4 = "";
                text1 = editText1.getText().toString();
                text2 = editText2.getText().toString();
                text3 = editText3.getText().toString();
                text4 = editText4.getText().toString();
                /*
                Toast.makeText(MainActivity.this,"TEXT 1: " +text1 + "\nTEXT 2: " + text2 + "\nTEXT 3: " + text3 + "\nTEXT 4: " + text4 + "PATH: "
                         ,Toast.LENGTH_SHORT).show(); */
                if (path == null) {
                    Toast.makeText(MainActivity.this, "Please select an image.", Toast.LENGTH_SHORT).show();
                } else {
                    finalIntent = new Intent(getApplicationContext(), TextActivity.class);
                    finalIntent.setData(path);
                    finalIntent.putExtra("Text1", text1);
                    finalIntent.putExtra("Text2", text2);
                    finalIntent.putExtra("Text3", text3);
                    finalIntent.putExtra("Text4", text4);
                    startActivity(finalIntent);
                }

                break;
            case R.id.selectImageButton:
                //Intent to open gallery and select photo.
                Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent, REQUEST_CODE);
                break;
        }
    }
}
