package com.generationflex.generationflex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Outfit extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean("DARK_MODE_ON", false);
        if(darkMode){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_outfit);
        final String outfitName = getIntent().getStringExtra("com.generationflex.outfit_name");
        final EditText inputName = (EditText) findViewById(R.id.name);
        final EditText inputTop = (EditText) findViewById(R.id.top);
        final EditText inputBottom = (EditText) findViewById(R.id.bottom);
        final EditText inputShoes = (EditText) findViewById(R.id.shoes);
        final EditText inputAccessories = (EditText) findViewById(R.id.accessories);
        if(outfitName != null){
            inputName.setText(outfitName);
            inputTop.setText(sharedPreferences.getString(outfitName + "_top", ""));
            inputBottom.setText(sharedPreferences.getString(outfitName + "_bottom", ""));
            inputShoes.setText(sharedPreferences.getString(outfitName + "_shoes", ""));
            inputAccessories.setText(sharedPreferences.getString(outfitName + "_accessories", ""));
        }else{
            Button delete = (Button) findViewById(R.id.delete_outfit);
            delete.setVisibility(View.INVISIBLE);
        }
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editOutfit(outfitName, inputName, inputTop, inputBottom, inputShoes, inputAccessories);
            }
        });


    }

    //Currently only supports one outfit under each name
    private void editOutfit(String oldName, EditText name, EditText top, EditText bottom, EditText shoes, EditText accessories) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(oldName != null){
            editor.remove(oldName + "_top");
            editor.remove(oldName + "_bottom");
            editor.remove(oldName + "_shoes");
            editor.remove(oldName + "_accessories");
        }
        String newName = name.getText().toString();
        editor.putString(newName + "_top", top.getText().toString());
        editor.putString(newName + "_bottom", bottom.getText().toString());
        editor.putString(newName + "_shoes", shoes.getText().toString());
        editor.putString(newName + "_accessories", accessories.getText().toString());
        Set<String> outfitList = new HashSet<String>(sharedPreferences.getStringSet("outfit_list", null));
        if(outfitList.contains(oldName)){
            outfitList.remove(oldName);
        }
        outfitList.add(newName);
        editor.putStringSet("outfit_list", outfitList);
        editor.apply();
        //Don't know which view to pass if I were to use switchToYourFlex()
        Intent intent = new Intent(this, YourFlex.class);
        startActivity(intent);
    }


    public void switchToYourFlex(View view){
        Intent intent = new Intent(this, YourFlex.class);
        startActivity(intent);
    }

    public void deleteOutfit(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String outfitName = getIntent().getStringExtra("com.generationflex.outfit_name");
        Set<String> outfitList = new HashSet<String>(sharedPreferences.getStringSet("outfit_list", new HashSet<String>()));
        outfitList.remove(outfitName);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(outfitName + "_top");
        editor.remove(outfitName + "_bottom");
        editor.remove(outfitName + "_shoes");
        editor.remove(outfitName + "_accessories");
        editor.putStringSet("outfit_list", outfitList);
        editor.apply();
        Intent intent = new Intent(this, YourFlex.class);
        startActivity(intent);
    }


}
