package com.generationflex.generationflex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class Settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    SharedPreferences sharedPreferences;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean("DARK_MODE_ON", false);
//        if(darkMode){
//            setTheme(R.style.AppTheme_Dark);
//
//        }else{
//            setTheme(R.style.AppTheme);
//        }
        setContentView(R.layout.settings);
        Switch toggle = (Switch) findViewById(R.id.dark_mode);
        if(darkMode) {
            toggle.setChecked(true);
        }else{
            toggle.setChecked(false);
        }
        toggle.setOnCheckedChangeListener(this);
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean darkMode = sharedPreferences.getBoolean("DARK_MODE_ON", false);
        editor.putBoolean("DARK_MODE_ON", !darkMode);
//        if (b) {
//            setTheme(R.style.AppTheme_Dark);
//        } else {
//            setTheme(R.style.AppTheme);
//        }
        editor.apply();
    }

    public void switchToYourFlex(View view){
        Intent intent = new Intent(this, YourFlex.class);
        startActivity(intent);
    }

    public void changeUserName(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_un);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString("username", input.getText().toString());
                editor.apply();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void changePassword(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_pw);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString("password", input.getText().toString());
                editor.apply();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


}
