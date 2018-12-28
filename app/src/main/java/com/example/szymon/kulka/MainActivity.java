package com.example.szymon.kulka;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener2 {

    private FrameLayout gameFrame;
    private LinearLayout startLayout;


    private ImageView red, blue;

    //private int BoxSize;

    private float redX, redY;
    private float blueX, blueY;
    private float xMax, yMax;
    private float xChange, yChange;
    private float xPos, xVel, yPos, yVel;

    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount;

    private SensorManager sensorManager;


    private Timer timer;
    private Handler handler = new Handler();

    private boolean start_flg = false;
    private boolean action_flg = false;
   // private boolean pink_flg = false;


    //sensor

    public void startGame(View view) {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);

        red.setX(0);
        red.setY(0);
        //blue.setX(10.0f);

        redX = red.getX();
        redY = red.getY();

        //blueX = blue.getX();

        red.setVisibility(View.VISIBLE);
        blue.setVisibility(View.INVISIBLE);


        timeCount = 0;
        score = 0;
        scoreLabel.setText("Wynik = 0");

        timer = new Timer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        //max wymiary
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 97;
        yMax = (float) size.y - 50;

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
            xChange = sensorEvent.values[0];
            yChange = -sensorEvent.values[1];
            updateBall();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    private void updateBall() {
        redX -= xChange;
        redY -= yChange;

        if (redX > xMax) {
            redX = xMax;
        } else if (redX < -15) {
            redX = -15;
        }

        if (redY > yMax) {
            redY = yMax;
        } else if (redY < 112) {
            redY = 112;
        }


        red.setX(redX);
        red.setY(redY);



        /* float frameTime = 0.666f;
        xVel += (redX * frameTime);
        yVel += (redY * frameTime);

        float xS = (xVel / 2) * frameTime;
        float yS = (yVel / 2) * frameTime;

        redX -= xS;
        redY -= yS;
*/
    }

    public boolean hitCheck(){
        
    }









}
     /*   timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (start_flg){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateBall();
                        }
                    });
            }
        }, 0, 20);}
*/
 /*  public void quitGame (View view){

    }
};
*/