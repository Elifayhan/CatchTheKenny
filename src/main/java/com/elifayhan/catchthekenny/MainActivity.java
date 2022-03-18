package com.elifayhan.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView scoreText;
    TextView timeText;
    int score;
    //kenny nin hareket etmesini istiyoruz. bunun için dizi ve loop kullanmamız lazım
    //kennylerimizin hepsini tanımamız lazım.
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    //bir dizi oluşturarlım:
    ImageView imageArray[];
    Handler handler;
    Runnable runnable;
    //Runnable bir işlemi birden fazla kez belirttiğimiz sayıda, periyotta yapmamızı sağlayan bir arayüzdür.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageArray = new ImageView[]{imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};
        hideImages();
        score = 0;
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {
                timeText.setText("Time:" + l / 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time Off");
                //handlerın çalışmasını durdurmalıyız. çünkü artık oyun bitti.
                handler.removeCallbacks(runnable);
                //durdurduktan sonra kenny i tamamen saklayabiliriz:
                for(ImageView image :imageArray){
                    image.setVisibility(View.INVISIBLE);

                }
                //kullanıcıya oyun bitti baştan oynamak ister misin diye bir alert mesajo göstereceğiz:
                AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart");
                alert.setMessage("Are you sure the restart the game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // yes e tıklanırsa burada ne olacağını yazacağız. restart olacak.
                        Intent intent=getIntent(); //intent, aktiviteler arası geçişi sağlamak içindi.
                        //uygulamayı çok yormamak için ilk önce güncel aktivitemizi destroy etmemiz gerekiyor.
                        //bunun içinde finish çalıştırırılır.
                        finish();
                        startActivity(intent); //bize yollanan bir intent yok.
                        // dolayısıyıla böyle diyerek kendi aktivitemizi baştan başlatırız.
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"Game Over", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();

            }
        }.start();


    }

    public void increaseScore(View view) {

        //her tıklandığında score u bir arttır diyoruz.
        //imagelere onclick methodu verdik, dolayısıyla imagelere her tıklandığında score 1 artacak.
        score++;
        scoreText.setText("Score:" + score);

    }
    public void hideImages(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView image :imageArray){ //imageArray içindeki herbir image ı image ile erişecek.
                    image.setVisibility(View.INVISIBLE);
                    //run içerisinde bütün diziyi görünmez hale getiricez her saniyede bir mesela.
                    //sonrasında rastegele değer bulup, bu rastegele değeri dizi içerisinde görünür hale getireceğiz.

            }
                Random random=new Random(); //Random objesi oluşturduk.
                int i =random.nextInt(9); // int objesini randomdan oluşturduk. 9 yazdığımız için
                //0 ile 8 arasında rastegele bir sayı üret diyoruz.
                imageArray[i].setVisibility(View.VISIBLE); //böylece rastgele birini görünür hale getiriyoruz.
                handler.postDelayed(this,500); //yarım saniyede bir kenny hareket ediyor.
                //this bir üstünü referans ediyordu. bir üstümüz Runnabel olduğu için Runnable a referans ettik.

        }
        };
        handler.post(runnable);
    }
}

