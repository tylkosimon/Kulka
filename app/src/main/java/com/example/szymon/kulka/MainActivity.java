package com.example.szymon.kulka;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener2 {

    private LinearLayout startLayout;
    private ImageView white;
    private ImageView[] colors = new ImageView[6];
    private int xMax, yMax, color;
    private float xChange, yChange;
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount;
    private SharedPreferences settings;
    private SensorManager sensorManager;
    private float [] xRand = new float[6];
    private float [] yRand = new float[6];
    private boolean start_flg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLayout = findViewById(R.id.startLayout);

        white = findViewById(R.id.white);
        colors[0] = findViewById(R.id.red);
        colors[1] = findViewById(R.id.green);
        colors[2] = findViewById(R.id.yellow);
        colors[3] = findViewById(R.id.orange);
        colors[4] = findViewById(R.id.purple);
        colors[5] = findViewById(R.id.blue);



        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        Point size = new Point();                                                                   //max wymiary
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = size.x - 97;
        yMax = size.y - 50;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    public void startGame(View view) {
        startLayout.setVisibility(View.INVISIBLE);
        start_flg = true;

        colors[0].setX(-666);                                                                       //red
        colors[1].setX(-666);                                                                       //green
        colors[2].setX(-666);                                                                       //yellow
        colors[3].setX(-666);                                                                       //orange
        colors[4].setX(-666);                                                                       //purple
        colors[5].setX(-666);                                                                       //blue



        white.setX(xMax/2);
        white.setY(yMax/2);


        firstBall();
        secondBall();
        thirdBall();
        pickColor();


        scoreLabel.setText("Wynik = 0");

        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText("Rekord = " + highScore);

    }

    public void pickColor(){
        Random rand = new Random();
        color = rand.nextInt(6);
        while(colors[color].getX() == -666){
            color = rand.nextInt(6);
        }
        if (color == 0){
            scoreLabel.setBackgroundColor(Color.rgb(220,20,60));
        }else if(color == 1){
            scoreLabel.setBackgroundColor(Color.rgb(0,255,0));
        }else if(color == 2){
            scoreLabel.setBackgroundColor(Color.rgb(255,255,0));
        }else if(color == 3){
            scoreLabel.setBackgroundColor(Color.rgb(255,165,0));
        }else if(color == 4){
            scoreLabel.setBackgroundColor(Color.rgb(148,0,211));
        }else if(color == 5){
            scoreLabel.setBackgroundColor(Color.rgb(0,191,255));
        }
    }


    public void firstBall(){
        Random rand = new Random();
        int number = rand.nextInt(6);
        int x,y;
        x = rand.nextInt(xMax);
        y = rand.nextInt(yMax-150)+150;
        ImageView random1 = (colors[number]);
        float r = 700;

        while (java.lang.Math.abs(white.getX() - colors[number].getX()) < r && java.lang.Math.abs(white.getY() - colors[number].getY()) > r) {
            x = rand.nextInt(xMax);
            y = rand.nextInt(yMax);
        }

        random1.setX(x);                                                                            //pozycja poczatkowa
        random1.setY(y);

        xRand[number] = rand.nextInt(5)+1;                                                    //przypisanie predkosci poruszania sie kulki
        yRand[number] = rand.nextInt(5)+1;
    }

    public void secondBall(){
        Random rand = new Random();
        int number = rand.nextInt(6);

        ImageView random2 = (colors[number]);
        while (random2.getX() != -666) {                                                            //warunek na kolor ktory nie jest na planszy
            number = rand.nextInt(6);
            random2 = (colors[number]);
        }
        int x,y;
        x = rand.nextInt(xMax);
        y = rand.nextInt(yMax-150)+150;

        float r = 700;

        while (java.lang.Math.abs(white.getX() - colors[number].getX()) < r && java.lang.Math.abs(white.getY() - colors[number].getY()) > r) {
            x = rand.nextInt(xMax);
            y = rand.nextInt(yMax);
        }


        random2.setX(x);                                                                            //pozycja poczatkowa
        random2.setY(y);

        xRand[number] = rand.nextInt(7)+1;                                                    //przypisanie predkosci poruszania sie kulki
        yRand[number] = rand.nextInt(7)+1;
    }

    public void thirdBall(){
        Random rand = new Random();
        int number = rand.nextInt(6);

        ImageView random3 = (colors[number]);
        while (random3.getX() != -666) {
            number = rand.nextInt(6);
            random3 = (colors[number]);
        }
        random3.setX(xMax/2 + rand.nextInt(500));                                            //pozycja poczatkowa
        random3.setY(yMax/2 + rand.nextInt(500));

        xRand[number] = rand.nextInt(7)+1;                                                    //przypisanie predkosci poruszania sie kulki
        yRand[number] = rand.nextInt(7)+1;
    }

    public void fourthBall(){
        Random rand = new Random();
        int number = rand.nextInt(6);

        ImageView random4 = (colors[number]);
        while (random4.getX() != -666) {
            number = rand.nextInt(6);
            random4 = (colors[number]);
        }
        random4.setX(xMax/2 + rand.nextInt(500));                                            //pozycja poczatkowa
        random4.setY(yMax/2 + rand.nextInt(500));

        xRand[number] = rand.nextInt(7)+1;                                                    //przypisanie predkosci poruszania sie kulki
        yRand[number] = rand.nextInt(7)+1;
    }

    public void fifthBall(){
        Random rand = new Random();
        int number = rand.nextInt(6);

        ImageView random5 = (colors[number]);
        while (random5.getX() != -666) {
            number = rand.nextInt(6);
            random5 = (colors[number]);
        }
        random5.setX(xMax/2 + rand.nextInt(500));                                            //pozycja poczatkowa
        random5.setY(yMax/2 + rand.nextInt(500));

        xRand[number] = rand.nextInt(7)+1;                                                    //przypisanie predkosci poruszania sie kulki
        yRand[number] = rand.nextInt(7)+1;
    }

    public void sixthBall(){
        Random rand = new Random();
        int number = rand.nextInt(6);

        ImageView random6 = (colors[number]);
        while (random6.getX() != -666) {
            number = rand.nextInt(6);
            random6 = (colors[number]);
        }
        random6.setX(xMax/2 + rand.nextInt(500));                                            //pozycja poczatkowa
        random6.setY(yMax/2 + rand.nextInt(500));

        xRand[number] = rand.nextInt(7)+1;                                                    //przypisanie predkosci poruszania sie kulki
        yRand[number] = rand.nextInt(7)+1;
    }
    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xChange = (sensorEvent.values[0])*5;
            yChange = (-sensorEvent.values[1])*5;
            updateWhiteBall();
            ballMovement();
        }
    }

    public void updateWhiteBall() {
            if (white.getX() > xMax) {
                white.setX(xMax);
            } else if (white.getX() < -15) {
                white.setX(-15);
            }
            if (white.getY() > yMax) {
                white.setY(yMax);
            } else if (white.getY() < 112) {
                white.setY(112);
            }


            white.setX(white.getX() - xChange);
            white.setY(white.getY() - yChange);
    }

    public void ballMovement(){
        for(int i=0; i<6; i++){
            if(colors[i].getX() != -666) {
                do {
                    if (colors[i].getX() > xMax) {
                        xRand[i] = - xRand[i];
                    } else if (colors[i].getX() < -15) {
                        xRand[i] = - xRand[i];
                    }
                    if (colors[i].getY() > yMax) {
                        yRand[i] = - yRand[i];
                    } else if (colors[i].getY() < 112) {
                        yRand[i] = - yRand[i];
                    }

                    colors[i].setX(colors[i].getX()+xRand[i]);
                    colors[i].setY(colors[i].getY()+yRand[i]);


                } while (hitCheck(i));
            }
        }
    }

    public boolean hitCheck(int k) {
        if (start_flg) {
            float r = 70;

            if (java.lang.Math.abs(white.getX() - colors[k].getX()) < r && java.lang.Math.abs(white.getY() - colors[k].getY()) < r) {
                if (k == color) {
                    score += 1;
                    scoreLabel.setText("Wynik = " + score);

                    colors[k].setX(-666);
                    secondBall();
                    if(score == 5){
                        fourthBall();
                    }else if (score == 10){
                        fifthBall();
                    }else if (score == 15){
                        sixthBall();
                    }

                    pickColor();
                } else {
                    gameOver();
                }
                return true;
            }
        }return false;
    }

    public void gameOver(){
        startLayout.setVisibility(View.VISIBLE);
        scoreLabel.setText("");
        start_flg = false;
        score = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    public void quitGame (View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finish();
        }
    }
}






/////////////////////////////////////////////////////////////////////////////////////////////////
