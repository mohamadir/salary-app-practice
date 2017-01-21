package com.example.mohamdib.filestutorial;

import android.content.Intent;
import android.hardware.SensorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.Buffer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShiftsActivity extends AppCompatActivity  {

    private static final int SHAKE_THRESHOLD = 800;
    ListView lv;
    ArrayList<String> shifts;
    ArrayList<String> shiftsFromFile;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shifts);
        shiftsFromFile=new ArrayList<String>();
        lv=(ListView)findViewById(R.id.listView);
        try {
            readFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent i = getIntent();
        shifts = i.getStringArrayListExtra("shifts");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shifts);
        lv.setAdapter(adapter);


    }

    private void readFromFile() throws IOException {
        String line;
        FileInputStream fileInputStream=openFileInput("salary.txt");
        InputStreamReader  inputStreamReader=new InputStreamReader(fileInputStream);
       //URL url = getClass().getResource("salary.txt");

        //Toast.makeText(ShiftsActivity.this,""+url.toString(), Toast.LENGTH_LONG).show();
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer=new StringBuffer();
        while((line=bufferReader.readLine())!=null)
        {
            shiftsFromFile.add(line);
        }
    }


}
