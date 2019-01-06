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
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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
    private ImageView main;
    private ImageView[] colors = new ImageView[7];
    private int xMax, yMax, color;
    private float xChange, yChange;
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore;
    private SharedPreferences settings;
    private SensorManager sensorManager;
    private float [] xRand = new float[7];
    private float [] yRand = new float[7];
    private boolean start_flg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLayout = findViewById(R.id.startLayout);


        main = findViewById(R.id.main);

        colors[0] = findViewById(R.id.red);
        colors[1] = findViewById(R.id.green);
        colors[2] = findViewById(R.id.yellow);
        colors[3] = findViewById(R.id.orange);
        colors[4] = findViewById(R.id.purple);
        colors[5] = findViewById(R.id.blue);
        colors[6] = findViewById(R.id.white);

        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE",0);
        highScoreLabel.setText("REKORD = " +highScore);



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
        colors[6].setX(-666);                                                                       //white



        main.setX(xMax/2);
        main.setY(yMax/2);


        addBall();
        addBall();
        addBall();

        pickColor();


        scoreLabel.setText("Wynik = 0");

        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText("REKORD = " + highScore);

    }

    public void pickColor() {
        Random rand = new Random();
        color = rand.nextInt(7);
        while (colors[color].getX() == -666) {
            color = rand.nextInt(7);
        }
        if (color == 0) {
            main.setImageResource(R.drawable.red_ball);
        } else if (color == 1) {
            main.setImageResource(R.drawable.green_ball);
        } else if (color == 2) {
            main.setImageResource(R.drawable.yellow_ball);
        } else if (color == 3) {
            main.setImageResource(R.drawable.orange_ball);
        } else if (color == 4) {
            main.setImageResource(R.drawable.purple_ball);
        } else if (color == 5) {
            main.setImageResource(R.drawable.blue_ball);
        } else if (color == 6) {
            main.setImageResource(R.drawable.white_ball);
        }
    }


    public void addBall(){
        Random rand = new Random();
        int number = rand.nextInt(7);

        ImageView random = (colors[number]);
        while (random.getX() != -666) {                                                            //warunek na kolor ktory nie jest na planszy
            number = rand.nextInt(7);
            random = (colors[number]);
        }
        int x,y;
        x = rand.nextInt(xMax);
        y = rand.nextInt(yMax-150)+150;

        float r = 600;

        while ((Math.abs(main.getX() - x) < r) && (Math.abs(main.getY() - y) < r)) {
            x = rand.nextInt(xMax);
            y = rand.nextInt(yMax-150)+150;
        }

        random.setX(x);                                                                            //pozycja poczatkowa
        random.setY(y);
        if(score>5 & score <11) {
            xRand[number] = rand.nextInt(4) + 2;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(4) + 2;
        }else if(score>10 & score <21){
            xRand[number] = rand.nextInt(5) + 2;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(5) + 2;
        }else if(score>20 & score <25) {
            xRand[number] = rand.nextInt(6) + 3;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(6) + 3;
        }else if(score>24 & score <30){
            xRand[number] = rand.nextInt(7) + 4;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(7) + 4;
        }else if(score>29 & score <35){
            xRand[number] = rand.nextInt(8) + 5;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(8) + 5;
        }else if(score>34) {
            xRand[number] = rand.nextInt(9) + 6;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(9) + 6;                                                    //przypisanie predkosci poruszania sie kulki
        }else {
            xRand[number] = rand.nextInt(3) + 1;                                                    //przypisanie predkosci poruszania sie kulki
            yRand[number] = rand.nextInt(3) + 1;
        }
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
            xChange = (sensorEvent.values[0])*3;
            yChange = (-sensorEvent.values[1])*3;
            updateWhiteBall();
            ballMovement();
        }
    }

    public void updateWhiteBall() {
            if (main.getX() > xMax-8) {
                xChange = xChange/10;
                main.setX(xMax-8);
            } else if (main.getX() < -21) {
                xChange = xChange/10;
                main.setX(-21);
            }
            if (main.getY() > yMax-8) {
                yChange = yChange/10;
                main.setY(yMax-8);
            } else if (main.getY() < 113) {
                yChange = yChange/10;
                main.setY(113);
            }


            main.setX(main.getX() - xChange);
            main.setY(main.getY() - yChange);
    }

    public void ballMovement(){
        for(int i=0; i<7; i++){
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
            float r = 55;

            if (java.lang.Math.abs(main.getX() - colors[k].getX()) < r && java.lang.Math.abs(main.getY() - colors[k].getY()) < r) {
                if (k == color) {
                    score += 1;
                    scoreLabel.setText("Wynik = " + score);

                    colors[k].setX(-666);
                    addBall();

                    if(score == 5){
                        addBall();
                    }else if (score == 10){
                        addBall();
                    }else if (score == 15) {
                        addBall();
                    }else if (score == 20){
                        addBall();
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

        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(score>highScore){
            highScore = score;
            highScoreLabel.setText("REKORD = " + highScore);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", highScore);
            editor.commit();
        }
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
