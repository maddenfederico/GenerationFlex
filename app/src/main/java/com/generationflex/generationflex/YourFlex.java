package com.generationflex.generationflex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;

public class YourFlex extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean("DARK_MODE_ON", false);
        if(darkMode){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_your_flex);
        GridLayout outfitContainer = (GridLayout) findViewById(R.id.outfit_container);
        Set<String> outfitList = sharedPreferences.getStringSet("outfit_list", null);
        if( outfitList != null) {
            final Iterator<String> iterator = outfitList.iterator();
            while (iterator.hasNext()) {
                Button outfit = new Button(this);
                String name = iterator.next();
                outfit.setText(name);
                outfit.setTag(name);
                outfit.setOnClickListener(this);
                outfitContainer.addView(outfit);
            }
        }
    };

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


    public void makeNewOutfit(View view){
        Intent intent = new Intent(this, Outfit.class);
        startActivity(intent);
    }

    public void signOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchToTheirFlex(View view){
        Intent intent = new Intent(this, TheirFlex.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, Outfit.class);
        intent.putExtra("com.generationflex.outfit_name", (String) view.getTag());
        startActivity(intent);
    }

    public void changeProfPic(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        ImageButton profPic = (ImageButton) findViewById(R.id.profile_picture);
        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try{
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                profPic.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
