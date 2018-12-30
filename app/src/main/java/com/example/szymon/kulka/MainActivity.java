package com.example.szymon.kulka;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener2 {

    private LinearLayout startLayout;
    private ImageView red, blue, white, green, orange, purple, yellow;
    private float redX, redY;
    private float whiteX, whiteY;
    private float greenX, greenY;
    private float orangeX, orangeY;
    private float blueX, blueY;
    private float purpleX, purpleY;
    private float yellowX, yellowY;
    private int xMax, yMax;
    private float xChange, yChange;
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount;
    private SharedPreferences settings;
    private SensorManager sensorManager;
    private Timer timer;
    private float xRand = 3;
    private float yRand = 3;

    public void startGame(View view) {
        startLayout.setVisibility(View.INVISIBLE);
        white.setX(xMax/2);
        white.setY(yMax/2);
        whiteX = red.getX();
        whiteY = red.getY();

        blueX = blue.getX();
        blueY = blue.getY();

        red.setVisibility(View.VISIBLE);
        blue.setVisibility(View.VISIBLE);

        timeCount = 0;
        score = 0;
        scoreLabel.setText("Wynik = 0");

        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText("Rekord = " + highScore);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLayout = findViewById(R.id.startLayout);
        red = findViewById(R.id.red);
        white = findViewById(R.id.white);
        green = findViewById(R.id.green);
        yellow = findViewById(R.id.yellow);
        orange = findViewById(R.id.orange);
        purple = findViewById(R.id.purple);
        blue = findViewById(R.id.blue);
        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        Point size = new Point();                                                                   //max wymiary
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = size.x - 97;
        yMax = size.y - 50;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
            xChange = (sensorEvent.values[0])*2;
            yChange = (-sensorEvent.values[1])*2;
            updateWhiteBall();
            ballMovement();
        }
    }

    public void updateWhiteBall() {
        whiteX -= xChange;
        whiteY -= yChange;

        if (whiteX > xMax) {
            whiteX = xMax;
        } else if (whiteX < -15) {
            whiteX = -15;
        }
        if (whiteY > yMax) {
            whiteY = yMax;
        } else if (whiteY < 112) {
            whiteY = 112;
        }
        white.setX(whiteX);
        white.setY(whiteY);
    }

    public void ballMovement(){


        do {

            if (blueX > xMax) {
                xRand = -3;
            } else if (blueX < -15) {
                xRand = 3;
            }
            if (blueY > yMax) {
               yRand= -3;
            } else if (blueY < 112) {
                yRand = 3;
            }
            blueX += xRand;
            blueY += yRand;
            blue.setX(blueX);
            blue.setY(blueY);
        }while(hitCheck(whiteX,whiteY,blueX,blueY));

    }

    public boolean hitCheck(float xMain, float yMain, float xCpu, float yCpu){
       float  r=70;
        if(java.lang.Math.abs(xMain - xCpu) < r && java.lang.Math.abs(yMain- yCpu) <r){
            score += 1;
            scoreLabel.setText("Wynik = "+ score);
            Random rand = new Random();
            blueX = rand.nextInt(xMax);
            blueY = rand.nextInt(yMax);

           // blueX = blue.getX();
           // blueY = blue.getY();
            return true;
            }
            return false;
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
