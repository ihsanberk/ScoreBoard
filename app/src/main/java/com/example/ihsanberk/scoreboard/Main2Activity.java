package com.example.ihsanberk.scoreboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ihsanberk.scoreboard.Constant.FIRST_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.SECOND_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.THIRD_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.FOURTH_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.FIFTH_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.SIXTH_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.SEVENTH_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.EIGHTH_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.NINETH_COLUMN;
import static com.example.ihsanberk.scoreboard.Constant.TENTH_COLUMN;

import models.ListItem;
import models.Match;

public class Main2Activity extends AppCompatActivity {

    SQLiteDatabase db=null;
    ArrayList<String> myArrayList = new ArrayList<String>();
    Button backButton;
    Button btnTemizle;
    private ArrayList<HashMap> list;

    String password="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backButton = (Button)findViewById(R.id.btnGeri);
        btnTemizle = (Button)findViewById(R.id.btnSifirla);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lstview = (ListView) findViewById(R.id.listView2);
        populateList();
        listviewAdapter adapter = new listviewAdapter(this, list);
        lstview.setAdapter(adapter);







        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();

//                Intent mIntent = getIntent();
//                finish();
//                startActivity(mIntent);
            }
        });

    }
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Main2Activity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Yönetici Şifresi Giriniz:");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        password = editText.getText().toString();

                        if(password.equals(getResources().getString(R.string.pass))){
                            DbTableTemizle();
                            Intent mIntent = getIntent();
                            finish();
                            startActivity(mIntent);

                        }
                        else
                            showToast("Yönetici Şifresi Hatalı!!!");
                    }
                })
                .setNegativeButton("GERİ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();



    }



    public void DbTableTemizle() {
        db = openOrCreateDatabase("OyunArsiv", MODE_PRIVATE,null);
        String deleteQuery ="delete from matchs";
        db.execSQL(deleteQuery);
        db.close();
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(Main2Activity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateList() {

        list = new ArrayList<HashMap>();


        db = openOrCreateDatabase("OyunArsiv", MODE_PRIVATE,null);
        String selectQuery ="Select * from matchs order by id desc";
        Cursor c =db.rawQuery(selectQuery,null);
        c.moveToFirst();
        String dbString="";

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("ponename"))!=null){

                HashMap temp = new HashMap();
                temp.put(FIRST_COLUMN,c.getString(c.getColumnIndex("ponename")));
                temp.put(SECOND_COLUMN, c.getString(c.getColumnIndex("ptwoname")));
                temp.put(THIRD_COLUMN, new String(""+c.getInt(c.getColumnIndex("handcount"))));
                temp.put(FOURTH_COLUMN,new String(""+c.getInt(c.getColumnIndex("ptwoscore"))));
                temp.put(FIFTH_COLUMN,new String(""+c.getInt(c.getColumnIndex("ptwoscore"))));
                temp.put(SIXTH_COLUMN,c.getString(c.getColumnIndex("poneaverage")));
                temp.put(SEVENTH_COLUMN,c.getString(c.getColumnIndex("ptwoaverage")));
                temp.put(EIGHTH_COLUMN,new String(""+c.getInt(c.getColumnIndex("ponemax"))));
                temp.put(NINETH_COLUMN,new String(""+c.getInt(c.getColumnIndex("ptwomax"))));
                temp.put(TENTH_COLUMN,c.getString(c.getColumnIndex("date")));
                list.add(temp);

            }
            c.moveToNext();

        }
        db.close();


    }




}