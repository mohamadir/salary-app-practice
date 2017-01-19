package com.example.mohamdib.filestutorial;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    Button btIn,btNext;
    TextView tvDate,tvRes;
    String timeIn,timeOut;
    Calendar c;
    boolean ifIn;
    String temp;// to add to the arrayList
    ArrayList<String> shifts;
    String[] str={"hi","bye"};
    ArrayAdapter<String> adapter;
    ListView lv;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ifIn=false;
        shifts=new ArrayList<String>();

        Button btNext=(Button)findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ShiftsActivity.class);
                i.putStringArrayListExtra("shifts", shifts);
                startActivity(i);

            }
        });


        btIn=(Button)findViewById(R.id.btFinger);
        tvDate =(TextView)findViewById(R.id.tvDate);
        tvRes=(TextView)findViewById(R.id.textView);


        c = Calendar.getInstance();
        int mints=c.get(Calendar.HOUR_OF_DAY);
        int seconds = c.get(Calendar.MINUTE);
        int month=c.get(Calendar.MONTH);
         timeIn="";
         timeOut="";
        month+=1;
        if(mints<10&&seconds<10)
            tvDate.setText("  תאריך ושעה"+
                    "0"+mints+":0"+seconds+"------"+c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));
        if(mints>=10&&seconds<10)
            tvDate.setText("  תאריך ושעה"+
                    ""+mints+":0"+seconds+"------"+c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));
        if(mints<10&&seconds>=10)
            tvDate.setText("  תאריך ושעה"+
                    "0"+mints+":"+seconds+"------"+c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));
        if(mints>10&&seconds>10)
            tvDate.setText("  תאריך ושעה"+
                    ""+mints+":"+seconds+"------"+c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));


        // dialog to confirm signature
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("אישור");
        builder.setMessage("האם ביצעת החתמה?");
        builder.setPositiveButton("כן תודה", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                c = Calendar.getInstance();


                if(!ifIn) {
                    c = Calendar.getInstance();
               ///     tvRes.setText(tvRes.getText().toString() + "כניסה:  " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
                    timeIn=c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    ifIn=true;
                    Toast.makeText(MainActivity.this, "חתמת בשעה:"+DateFormat.getDateTimeInstance().format(new Date()),
                            Toast.LENGTH_LONG).show();
                   temp= "כניסה:  " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                }
                else {
                    c = Calendar.getInstance();
                 //   tvRes.setText(tvRes.getText().toString() + " יציאה: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
                    ifIn=false;
                    timeOut=c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    String time1 = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                    String time2 = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                    Date date1,date2;
                    long difference;
                    Toast.makeText(MainActivity.this, "חתמת בשעה:"+DateFormat.getDateTimeInstance().format(new Date()),
                            Toast.LENGTH_LONG).show();
                    temp+=" יציאה: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    try {
                        date1 = format.parse(timeIn);
                        date2 = format.parse(timeOut);
                        difference=date2.getTime() - date1.getTime();
                        long diffMinutes = difference / (60 * 1000) % 60;
                        long diffHours = difference / (60 * 60 * 1000) % 24;
                        temp+="    סך הכל :"+ diffHours+"."+diffMinutes+"\n";
                 //       tvRes.setText(tvRes.getText()+"    סך הכל :"+ diffHours+"."+diffMinutes +"\n");

                    }
                    catch (Exception e)
                    {

                    }
                    shifts.add(temp);
                    temp="";
                 /*   adapter.notifyDataSetChanged();*/
                }

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("לא זה היה בטעות ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this, "החתימה לא בוצעה",
                        Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        alert= builder.create();







        btIn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                alert.show();

                return true;
            }
        });
    }
}
