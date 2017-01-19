package com.example.mohamdib.filestutorial;

import android.content.Intent;
import android.hardware.SensorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShiftsActivity extends AppCompatActivity  {

    private static final int SHAKE_THRESHOLD = 800;
    ListView lv;
    ArrayList<String> shifts;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shifts);
        lv=(ListView)findViewById(R.id.listView);
        Intent i = getIntent();
        shifts = i.getStringArrayListExtra("shifts");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shifts);
        lv.setAdapter(adapter);



    }


}
