package com.example.akhilbatchu.notepad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
   static ArrayAdapter<String> adapater;
   static ArrayList<String> notes;
    static  ArrayList<String> title;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notes = new ArrayList<>();
        title = new ArrayList<>();
        lv = (ListView)findViewById(R.id.myListView);
        notes.clear();
        title.clear();
        preferences = (SharedPreferences)getSharedPreferences("com.example.akhilbatchu.notepad", Context.MODE_PRIVATE);
        try {
            notes = (ArrayList<String>)ObjectSerializer.deserialize(preferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
            title = (ArrayList<String>)ObjectSerializer.deserialize(preferences.getString("title",ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {
            e.printStackTrace();
        }
        if(!(notes.size()>0 && title.size() >0))
        {
            title.add("Add new notes Folder");
            notes.add("Untitled");
        }

        adapater = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,title);
        lv.setAdapter(adapater);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("notes",i);
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {
                if(position!=0) {
                    new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are you sure").setMessage("Do you want to delete this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (position != 0) {
                                        notes.remove(position);
                                        title.remove(position);
                                    }

                                    adapater.notifyDataSetChanged();
                                }
                            }).setNegativeButton("No", null).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Only used for Creating the folder",Toast.LENGTH_LONG).show();
                }




                return true;
            }

        });




    }
}
