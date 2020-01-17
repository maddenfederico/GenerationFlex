package com.generationflex.generationflex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean("DARK_MODE_ON", false);
        if(darkMode){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
    }

    public void switchToTheirFlex(View view){
        EditText un = (EditText) findViewById(R.id.username);
        EditText pw = (EditText) findViewById(R.id.pw);
        String username = un.getText().toString();
        String password = pw.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("username", "maddenfederico");
        String currentPassword = sharedPreferences.getString("password", "password1234");
        if(username.equals(currentUsername) && password.equals(currentPassword)){
            Intent intent = new Intent(this, TheirFlex.class);
            startActivity(intent);
        }else{
            Context context = getApplicationContext();
            CharSequence text = getResources().getString(R.string.sign_in_fail);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}
