package com.generationflex.generationflex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Iterator;
import java.util.Set;

public class TheirFlex extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_theirflex);

        String[] contestList = getResources().getStringArray(R.array.contests_array);
        LinearLayout contestContainer = (LinearLayout) findViewById(R.id.contests);
            for(int i = 0; i < contestList.length; i++){
                String contest = contestList[i];
                if(sharedPreferences.getBoolean(contest + "_is_joined", false)){
                    Button contestButton = new Button(this);
                    contestButton.setText(contest);
                    contestButton.setTag(R.string.contest_button_name, contest);
                    contestButton.setTag(R.string.contest_button_outfit, sharedPreferences.getString(contest + "_outfit", getString(R.string.not_found)));
                    contestButton.setOnClickListener(this);
                    contestContainer.addView(contestButton);
                }

            }

    }

    public void switchToYourFlex(View view){
        Intent intent = new Intent(this, YourFlex.class);
        startActivity(intent);
    }

    public void switchToContest(View view){
        Intent intent = new Intent(this, Contest.class);
        startActivity(intent);
    }


    public void onClick(View view){
        Intent intent = new Intent(this, DailyContest.class);
        intent.putExtra("com.generationflex.contest", (String) view.getTag(R.string.contest_button_name));//Retrieve contest tag and put in intent
        intent.putExtra("com.generationflex.outfit", (String)view.getTag(R.string.contest_button_outfit));//Outfit tag
        startActivity(intent);
    }
}
