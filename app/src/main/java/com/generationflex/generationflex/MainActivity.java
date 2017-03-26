package com.generationflex.generationflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void switchToTheirFlex(View view){
        Intent intent = new Intent(this, TheirFlex.class);
        startActivity(intent);
    }

    public void switchToYourFlex(View view){
        Intent intent = new Intent(this, YourFlex.class);
        startActivity(intent);
    }
}
