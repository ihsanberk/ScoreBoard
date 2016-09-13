package com.example.ihsanberk.scoreboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView txt_hand_count;

    TextView txt_max_score1;
    TextView txt_player1_score;
    TextView txt_player1_average;
    Button btn_player1_plus;
    Button btn_player1_minus;
    Button btn_player1_enter;

    TextView txt_max_score2;
    TextView txt_player2_score;
    TextView txt_player2_average;
    Button btn_player2_plus;
    Button btn_player2_minus;
    Button btn_player2_enter;

    Button btn_reset;

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

        txt_hand_count= (TextView) findViewById(R.id.txtIstakaSayısı);
        txt_max_score1= (TextView) findViewById(R.id.txtMaksimumBir);
        txt_max_score2= (TextView) findViewById(R.id.txtMaksimumIki);

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

        btn_player1_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekilen_sayi_bir=cekilen_sayi_bir+1;
                txt_player1_score.setText(""+cekilen_sayi_bir);
                hand_flag_bir=true;
            }
        });

        btn_player1_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        btn_player1_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
// player 2 actions
        btn_player2_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekilen_sayi_iki=cekilen_sayi_iki+1;
                txt_player2_score.setText(""+cekilen_sayi_iki);
                hand_flag_iki=true;
            }
        });

        btn_player2_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        btn_player2_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InıtıalizedFields();

            }
        });


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

        if(hand_flag)
            if(istaka_sayisi>1)
                ortalama_iki=(float)toplam_sayi_iki/(float)(istaka_sayisi-1);
        if(!hand_flag)
            ortalama_iki=(float)toplam_sayi_iki/(float)istaka_sayisi;
        String str = String.format("%2.03f", ortalama_bir);
        String str2 = String.format("%2.03f", ortalama_iki);
        if(istaka_sayisi>1){
            txt_player1_average.setText(str);
            txt_player2_average.setText(str2);
        }
//deneme
    }
    public void MaksimumYazdir(int maksimum,int oyuncu){
        if(oyuncu==1)
            txt_max_score1.setText("En Yüksek Seri : "+maksimum);
        if(oyuncu==2)
            txt_max_score2.setText("En Yüksek Seri : "+maksimum);
        if(oyuncu==0){
            txt_max_score1.setText("En Yüksek Seri : "+0);
            txt_max_score2.setText("En Yüksek Seri : "+0);
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

        MaksimumYazdir(0,0);




    }


}
