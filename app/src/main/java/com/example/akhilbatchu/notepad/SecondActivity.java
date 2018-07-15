package com.example.akhilbatchu.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    String saveNotes,saveTitle;
    EditText title,notes;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
         title = (EditText)findViewById(R.id.title) ;
        notes = (EditText)findViewById(R.id.notes);
        preferences = (SharedPreferences)this.getSharedPreferences("com.example.akhilbatchu.notepad", Context.MODE_PRIVATE);
        Intent intent =getIntent();
        if(intent.getIntExtra("notes",0)!=0)
        {
            title.setText(MainActivity.title.get(intent.getIntExtra("notes",0)));
            notes.setText(MainActivity.notes.get(intent.getIntExtra("notes",0)));
            }
    }
    public void onSave(View view){
        saveNotes = notes.getText().toString();
        saveTitle = title.getText().toString();
        MainActivity.notes.add(saveNotes);
        MainActivity.title.add(saveTitle);
        MainActivity.adapater.notifyDataSetChanged();
        try {
            preferences.edit().putString("title",ObjectSerializer.serialize(MainActivity.title)).apply();
            preferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        makeToast("Saved");

    }
    public void makeToast(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
