package com.generationflex.generationflex;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DailyContest extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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
        loadActivity();
    }

    public void leaveComment(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.leave_comment);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        Set<String> temp = sharedPreferences.getStringSet(getIntent().getStringExtra("com.generationflex.contest")+ "_comments", new HashSet<String>());
        final Set<String> comments = new HashSet<String>(temp);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comments.add(input.getText().toString());
                editor.putStringSet(getIntent().getStringExtra("com.generationflex.contest") + "_comments", comments);
                editor.apply();
                loadActivity();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void loadActivity(){
        setContentView(R.layout.activity_daily_contest);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        TextView contest = (TextView) findViewById(R.id.contest);
        TextView outfit = (TextView) findViewById(R.id.outfit);
        String sContest = getIntent().getStringExtra("com.generationflex.contest");
        contest.setText(sContest);
        outfit.setText(getIntent().getStringExtra("com.generationflex.outfit"));
        Set<String> temp = sharedPreferences.getStringSet(getIntent().getStringExtra("com.generationflex.contest")+ "_comments", new HashSet<String>());
        final Set<String> commentList = new HashSet<String>(temp);
        LinearLayout commentContainer = (LinearLayout) findViewById(R.id.comment_list);
        if(commentList != null){
            final Iterator<String> iterator = commentList.iterator();
            while(iterator.hasNext()){
                LinearLayout commentTemplate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_template, null);
                Button comment = (Button) commentTemplate.getChildAt(0);
                comment.setText(iterator.next());
                comment.setOnClickListener(this);
                ToggleButton like = (ToggleButton) commentTemplate.getChildAt(1);
                ToggleButton dislike = (ToggleButton) commentTemplate.getChildAt(2);
                like.setTag(dislike);//Allows buttons to easily interact with each other
                dislike.setTag(like);
                like.setOnCheckedChangeListener(this);
                dislike.setOnCheckedChangeListener(this);

                commentContainer.addView(commentTemplate);
            }
        }
    }



    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit_comment);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        Set<String> temp = sharedPreferences.getStringSet(getIntent().getStringExtra("com.generationflex.contest")+ "_comments", new HashSet<String>());
        final Set<String> comments = new HashSet<String>(temp);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Set up the input
        final Button currComment = (Button) view;

        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comments.remove(currComment.getText().toString());
                comments.add(input.getText().toString());
                editor.putStringSet(getIntent().getStringExtra("com.generationflex.contest") + "_comments", comments);
                editor.apply();
                currComment.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNeutralButton(R.string.report, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reportComment();
            }
        });

        builder.show();
    }

    public void reportComment(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.report_comment);

        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);
        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.comment_reported);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){//isChecked
            CompoundButton other = (CompoundButton) compoundButton.getTag();
            other.setChecked(false);
        }
    }

    public void abandonContest(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String contest = getIntent().getStringExtra("com.generationflex.contest");
        editor.putBoolean(contest + "_is_joined", false);
        editor.putStringSet(contest + "_comments", null);
        editor.apply();
        switchToTheirFlex();

    }

    private void switchToTheirFlex(){
        Intent intent = new Intent(this, TheirFlex.class);
        startActivity(intent);
    }
}
