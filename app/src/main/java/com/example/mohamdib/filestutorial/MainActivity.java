package com.example.mohamdib.filestutorial;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    Button btIn,btNext,btPdf;
    TextView tvDate,tvRes,tvTime,tvIn;
    String timeIn,timeOut;
    String lines;
    String outPath;
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
        tvTime=(TextView)findViewById(R.id.tvTime);
        tvIn=(TextView)findViewById(R.id.tvIn);
        ifIn=false;
        lines="";
        shifts=new ArrayList<String>();
        btPdf=(Button )findViewById(R.id.btPdf);
        btPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            //    createandDisplayPdf("hi hi hi hi ");
                createPdf("hi hi hi \n hi hi hi ");
       //         viewPdf();
            }
        });

        Button btNext=(Button)findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ShiftsActivity.class);
                i.putStringArrayListExtra("shifts", shifts);
                startActivity(i);

            }
        });
        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                    tvTime.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));

            }
            public void onFinish() {

            }
        };
        newtimer.start();

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
            tvDate.setText("  תאריך "+
                    /*"0"+mints+":0"+seconds+"------"+*/c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));
        if(mints>=10&&seconds<10)
            tvDate.setText("  תאריך"+
                    /*""+mints+":0"+seconds+"------"+*/c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));
        if(mints<10&&seconds>=10)
            tvDate.setText("  תאריך "+
                   /* "0"+mints+":"+seconds+"------"+*/c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));
        if(mints>10&&seconds>10)
            tvDate.setText("  תאריך"+
                   /* ""+mints+":"+seconds+"------"+*/c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+c.get(Calendar.YEAR));


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
                    tvIn.setText("חתמת כניסה ב:  " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

                    timeIn=c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    ifIn=true;
                    Toast.makeText(MainActivity.this, "חתמת  בשעה:"+DateFormat.getDateTimeInstance().format(new Date()),
                            Toast.LENGTH_LONG).show();
                    int month=c.get(Calendar.MONTH);
                    month+=1;
                    temp=c.get(Calendar.DAY_OF_MONTH) + "/" + month+"   ";
                    temp+= "כניסה:  " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
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
                    tvIn.setText("");
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
                    try {
                        readFromFile();
                        writeToFile(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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

    private void createPdf() {
        PdfDocument document = new PdfDocument();
        outPath= Environment.getExternalStorageDirectory()+"/mypdf.pdf";

    }

    private void writeToFile(String temp) throws IOException {

        String fileName="salary.txt";
       // readFromFile();
        try {
            FileOutputStream fileOutputStream =openFileOutput(fileName,MODE_PRIVATE);
        //    lines+=temp;
            fileOutputStream.write(temp.getBytes());
            fileOutputStream.close();
            Toast.makeText(MainActivity.this, "קובץ נוצר",
                    Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
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
            stringBuffer.append(line+"\n");
        }
      //  lines=line;

    }

    public void createPdf(String text)
    {
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();

        String outPath= Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/mypdf.pdf";
        Toast.makeText(MainActivity.this, ""+outPath,
                Toast.LENGTH_LONG).show();
        try{

            PdfWriter.getInstance(doc,new FileOutputStream(outPath));
            doc.open();
            doc.add(new Paragraph(text));
            doc.close();
            Toast.makeText(MainActivity.this, "file created",
                    Toast.LENGTH_LONG).show();


        }
        catch(DocumentException e)
        {
            Toast.makeText(MainActivity.this, "document exep",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "file not found",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }



    /// ----------------------------------------------    FROM STACKOVERFLOW ----------------------------
   /* public void createandDisplayPdf(String text) {

        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            //Font paraFont= new Font(Font.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            //p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        viewPdf("newFile.pdf", "Dir");
    }
*/
    private void viewPdf() {

        File pdfFile = new File(getFilesDir()+"/mypdf.pdf");
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}
