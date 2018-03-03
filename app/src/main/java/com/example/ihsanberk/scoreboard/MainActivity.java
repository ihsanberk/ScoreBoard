package com.example.ihsanberk.scoreboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Timestamp;
import java.sql.Time;

import models.Match;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db=null;

    TextView txt_hand_count;

    TextView txt_max_score1;
    TextView txt_player1_score;
    TextView txt_player1_average;
    TextView txt_Pone_name;
    Button btn_player1_plus;
    Button btn_player1_minus;
    Button btn_player1_enter;

    TextView txt_max_score2;
    TextView txt_player2_score;
    TextView txt_player2_average;
    TextView txt_Ptwo_name;
    Button btn_player2_plus;
    Button btn_player2_minus;
    Button btn_player2_enter;

    Button btn_reset;
    Button btn_record;

    int cekilen_sayi_bir=0;
    int toplam_sayi_bir=0;
    int maks_sayi_bir=0;
    float ortalama_bir=(float)0;
    boolean hand_flag_bir=false;

    int cekilen_sayi_iki=0;
    int toplam_sayi_iki=0;
    int maks_sayi_iki=0;
    float ortalama_iki=(float)0;
    boolean hand_flag_iki=false;
    boolean hand_flag=false; //false player 2 true player 1

    int istaka_sayisi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db= openOrCreateDatabase("OyunArsiv", MODE_PRIVATE,null);
        String createQuery= "Create table if not exists matchs ( id integer primary key autoincrement,ponename text, ptwoname text, " +
                "handcount int, ponescore int, ptwoscore int, poneaverage text, ptwoaverage text, " +
                "ponemax int, ptwomax int , date text);";
        db.execSQL(createQuery);
        db.close();


        txt_hand_count= (TextView) findViewById(R.id.txtIstakaSayısı);
        txt_max_score1= (TextView) findViewById(R.id.txtMaksimumBir);
        txt_max_score2= (TextView) findViewById(R.id.txtMaksimumIki);
        txt_Pone_name= (TextView) findViewById(R.id.txtPoneName);
        txt_Ptwo_name= (TextView) findViewById(R.id.txtPtwoName);

        txt_player1_score= (TextView) findViewById(R.id.txtOyuncuBir);
        txt_player1_average= (TextView) findViewById(R.id.txtOrtalamaBir);
        btn_player1_plus= (Button) findViewById(R.id.btnOyuncuBirArttir);
        btn_player1_minus= (Button) findViewById(R.id.btnOyuncuBirAzalt);
        btn_player1_enter= (Button) findViewById(R.id.btnOyuncuBirEnter);

        txt_player2_score= (TextView) findViewById(R.id.txtOyuncuIki);
        txt_player2_average= (TextView) findViewById(R.id.txtOrtalamaIki);
        btn_player2_plus= (Button) findViewById(R.id.btnOyuncuIkiArttir);
        btn_player2_minus= (Button) findViewById(R.id.btnOyuncuIkiAzalt);
        btn_player2_enter= (Button) findViewById(R.id.btnOyuncuIkiEnter);

        btn_reset= (Button) findViewById(R.id.btnReset);
        btn_record = (Button) findViewById(R.id.btnKaydet);

        btn_player1_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponePlus();

            }
        });

        btn_player1_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                poneMinus();


            }
        });
        btn_player1_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poneEnter();

            }
        });
// player 2 actions
        btn_player2_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptwoPlus();

            }
        });

        btn_player2_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptwoMinus();


            }
        });
        btn_player2_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptwoEnter();

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InıtıalizedFields();

            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordMatch();

            }
        });

        txt_Pone_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(txt_Pone_name);
            }
        });

        txt_Ptwo_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(txt_Ptwo_name);
            }
        });

    }

    private void RecordMatch() {
        Match match=new Match();
        match.setpOneName(txt_Pone_name.getText().toString());
        match.setpTwoName(txt_Ptwo_name.getText().toString());
        match.setHandCount(istaka_sayisi);
        String str = String.format("%3.03f", ortalama_bir);
        match.setpOneAverage(str);
        String str2 = String.format("%3.03f", ortalama_iki);
        match.setpTwoAverage(str2);
        match.setpOneMax(maks_sayi_bir);
        match.setpTwoMax(maks_sayi_iki);
        match.setpOneScore(toplam_sayi_bir);
        match.setpTwoScore(toplam_sayi_iki);

        if(istaka_sayisi>0){
            AddTable(match);
            showToast("Skor Kaydedildi.");
            // Toast.makeText(this,"Skor Kaydedildi.",Toast.LENGTH_SHORT).show();
        }
        if(istaka_sayisi<1){
            showToast("Kaydedilecek Skor bulunmamaktadır!!!");
        }


    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void AddTable(Match match) {
        db =openOrCreateDatabase("OyunArsiv", MODE_PRIVATE,null);
        //tarih nasıl alınır yazılacak
        String matchDate = "23.09.1988 05:30:00";
        // java.util.Date date= new java.util.Date();

        // java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
        matchDate = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new java.util.Date());


        String insertQuery = "insert into matchs(ponename , ptwoname ," +
                "handcount , ponescore , ptwoscore , poneaverage , ptwoaverage ,"+
                "ponemax , ptwomax  , date ) values('";
        insertQuery+= match.getpOneName()+"','"+match.getpTwoName()+"',"+match.getHandCount()+","+match.getpOneScore()+",";
        insertQuery+= match.getpTwoScore()+",'"+match.getpOneAverage()+"','"+match.getpTwoAverage()+"',"+match.getpOneMax()+",";
        insertQuery+= +match.getpTwoMax()+",'"+matchDate+"');";

        db.execSQL(insertQuery);
        db.close();


    }

    public void SayiYazdir(int sayi,TextView nereye){

        if(sayi>-1 & sayi<10)
            nereye.setText("00"+sayi);
        if(sayi>9 & sayi<100)
            nereye.setText("0"+sayi);
        if(sayi>99)
            nereye.setText(""+sayi);


    }
    public void IstakaYazdir(int sayi,TextView nereye){
        if(sayi>0 & sayi<10)
            nereye.setText("0"+sayi);
        if(sayi>9)
            nereye.setText(""+sayi);
    }
    public void OrtalamaYazdir(){
        ortalama_bir=(float)toplam_sayi_bir/(float)istaka_sayisi;

        if(hand_flag){
            if(istaka_sayisi>1)
                ortalama_iki=(float)toplam_sayi_iki/(float)(istaka_sayisi-1);
            if(istaka_sayisi==1)
                ortalama_iki=0;
        }
        if(!hand_flag)
            ortalama_iki=(float)toplam_sayi_iki/(float)istaka_sayisi;
        String str = String.format("%3.03f", ortalama_bir);
        String str2 = String.format("%3.03f", ortalama_iki);
        if(istaka_sayisi>0){
            txt_player1_average.setText(str);
            txt_player2_average.setText(str2);
        }

//deneme
    }
    public void MaksimumYazdir(int maksimum,int oyuncu){
        if(oyuncu==1)
            txt_max_score1.setText("Yüksek Seri : "+maksimum);
        if(oyuncu==2)
            txt_max_score2.setText("Yüksek Seri : "+maksimum);
        if(oyuncu==0){
            txt_max_score1.setText("Yüksek Seri : "+0);
            txt_max_score2.setText("Yüksek Seri : "+0);
        }
    }

    public void YazdirHepsi(){
        SayiYazdir(toplam_sayi_bir,txt_player1_score);
        SayiYazdir(toplam_sayi_iki,txt_player2_score);
        IstakaYazdir(istaka_sayisi,txt_hand_count);
        OrtalamaYazdir();
        MaksimumYazdir(maks_sayi_bir,1);
        MaksimumYazdir(maks_sayi_iki,2);
    }

    public void InıtıalizedFields(){

        cekilen_sayi_bir=0;
        toplam_sayi_bir=0;
        ortalama_bir=(float)0;
        hand_flag_bir=false;
        hand_flag_iki=false;
        hand_flag=false;
        maks_sayi_bir=0;

        cekilen_sayi_iki=0;
        toplam_sayi_iki=0;
        ortalama_iki=(float)0;
        maks_sayi_iki=0;

        istaka_sayisi=0;
        txt_player1_average.setText("0000");
        txt_player2_average.setText("0000");
        txt_player1_score.setText("000");
        txt_player2_score.setText("000");
        txt_hand_count.setText("00");
        txt_max_score1.setText("00");
        txt_Pone_name.setText("Oyuncu A");
        txt_Ptwo_name.setText("Oyuncu B");

        MaksimumYazdir(0,0);
    }

    public void OpenHistory(View view){

        Intent OpenHistory = new Intent(this, Main2Activity.class);
        startActivity(OpenHistory);
    }

    public void ponePlus(){
        cekilen_sayi_bir=cekilen_sayi_bir+1;
        txt_player1_score.setText(""+cekilen_sayi_bir);
        hand_flag_bir=true;
    }
    public void poneMinus(){
        if(cekilen_sayi_bir>0){
            cekilen_sayi_bir=cekilen_sayi_bir-1;
            txt_player1_score.setText(""+cekilen_sayi_bir);
        }
        else if(!hand_flag_bir) {
            if(toplam_sayi_bir>0)
                toplam_sayi_bir--;
            YazdirHepsi();
        }

    }
    public void poneEnter(){
        toplam_sayi_bir=toplam_sayi_bir+cekilen_sayi_bir;
        SayiYazdir(toplam_sayi_bir,txt_player1_score);
        istaka_sayisi++;
        if(maks_sayi_bir<cekilen_sayi_bir)
            maks_sayi_bir=cekilen_sayi_bir;
        cekilen_sayi_bir=0;
        hand_flag_bir=false;
        hand_flag=true; //player 1 enter a bastı player 2 ıstaka arttırmayacak
        YazdirHepsi();

    }
    public void ptwoPlus(){
        cekilen_sayi_iki=cekilen_sayi_iki+1;
        txt_player2_score.setText(""+cekilen_sayi_iki);
        hand_flag_iki=true;

    }
    public void ptwoMinus(){
        if(cekilen_sayi_iki>0){
            cekilen_sayi_iki=cekilen_sayi_iki-1;
            txt_player2_score.setText(""+cekilen_sayi_iki);
        }
        else if(!hand_flag_iki) {
            if(toplam_sayi_iki>0)
                toplam_sayi_iki--;
            YazdirHepsi();
        }

    }
    public void ptwoEnter(){
        toplam_sayi_iki=toplam_sayi_iki+cekilen_sayi_iki;
        SayiYazdir(toplam_sayi_iki,txt_player2_score);
        if (!hand_flag)
            istaka_sayisi++;
        if(maks_sayi_iki<cekilen_sayi_iki)
            maks_sayi_iki=cekilen_sayi_iki;
        cekilen_sayi_iki=0;
        hand_flag_iki=false;
        hand_flag=false; //player 2 enter a basti
        YazdirHepsi();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_4:   //player 1 ileri tuşu
            {    //your Action code
                ponePlus();
                return true;
            }
            case KeyEvent.KEYCODE_7:   //player 1 geri tuşu
            {    //your Action code
                poneMinus();
                return true;
            }
            case KeyEvent.KEYCODE_1:   //player 1 enter tuşu
            {    //your Action code
                poneEnter();
                return true;
            }
            case KeyEvent.KEYCODE_5:   //player 2 ileri tuşu
            {    //your Action code
                ptwoPlus();
                return true;
            }
            case KeyEvent.KEYCODE_8:   //player 2 geri tuşu
            {    //your Action code
                ptwoMinus();
                return true;
            }
            case KeyEvent.KEYCODE_2:   //player 2 enter tuşu
            {    //your Action code
                ptwoEnter();
                return true;
            }case KeyEvent.KEYCODE_3:   //macı kaydet
            {    //your Action code
                RecordMatch();
                return true;
            }case KeyEvent.KEYCODE_ENTER:   //Reset
            {    //your Action code
                InıtıalizedFields();
                return true;
            }
            case KeyEvent.KEYCODE_6:   //player 1 ileri tuşu
            {    //your Action code
                ponePlus();
                return true;
            }







        }
        return super.onKeyDown(keyCode, event);
    }




    protected void showInputDialog(final TextView txtView) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Oyuncu ismi giriniz");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        editText.setHint("İsim Giriniz");
        editText.setMaxWidth(20);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name="";
                        name=editText.getText().toString();


                        if(!name.equals("")){
                            txtView.setText(name);

                        }
                        else
                            showToast("İsim alanına giriş yapınız.");
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


}
