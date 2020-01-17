package com.generationflex.generationflex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;
import java.util.Set;

public class Contest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_contest);
        Set<String> outfitSet = sharedPreferences.getStringSet("outfit_list", null);
        if(outfitSet == null || outfitSet.size() == 0){//Null case
            makeToast(R.string.no_outfits);
            cancel(null);
        }

        //Run-time outfit spinner set-up
        Object[] outfitArray = outfitSet.toArray();
        Spinner outfitSpinner = (Spinner) findViewById(R.id.outfit_spinner);
        outfitSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, outfitArray));
        outfitSpinner.setOnItemSelectedListener(this);
        //Contest spinner set-up
        Spinner contestSpinner = (Spinner) findViewById(R.id.contest_spinner);
        contestSpinner.setOnItemSelectedListener(this);
    }


    public void cancel(View view){
        Intent intent =  new Intent(this, TheirFlex.class);
        startActivity(intent);
    }

    //Helper function
    private void makeToast(@StringRes int id){
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(id);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void submit(View view){
        Spinner contestSpinner = (Spinner) findViewById(R.id.contest_spinner);
        Spinner outfitSpinner = (Spinner) findViewById(R.id.outfit_spinner);

        if((contestSpinner.getTag() != null) && (outfitSpinner.getTag() != null)){

            String outfit = (String) outfitSpinner.getTag();
            String contest = (String) contestSpinner.getTag();
            Intent intent = new Intent(this, TheirFlex.class);
            SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean((String) contestSpinner.getTag() + "_is_joined", true);
            editor.putString(contest + "_outfit", outfit);
            editor.apply();
            startActivity(intent);
        }else{
            makeToast(R.string.missing_contest);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//Saves the selected item as a tag on the spinner
        String item = adapterView.getSelectedItem().toString();
        adapterView.setTag(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.setTag(null);
    }
}
