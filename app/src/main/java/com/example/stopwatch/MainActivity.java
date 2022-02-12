package com.example.stopwatch;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private int secondCounter, minutes, seconds, shortMilliseconds;
    private long milliseconds;
    private Button startStopButton, intervalButton;
    TextView timeTextView;
    private boolean isStart=false;
    ListView intervalList;
    LinkedList<Interval> intervalLinkedList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startStopButton = findViewById(R.id.startStopButton);
        intervalButton = findViewById(R.id.intervalButton);
        timeTextView = findViewById(R.id.time);
        intervalList = findViewById(R.id.interval_list);

        intervalLinkedList = new LinkedList<>();

        ArrayAdapter<Interval> arrayAdapter = new ArrayAdapter(this,
                R.layout.list_interval_item, intervalLinkedList);
        intervalList.setAdapter(arrayAdapter);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = !isStart;
                runStopwatch();
                changeButton();
            }
        });

        intervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart){
                    intervalLinkedList.add(new Interval(minutes, seconds, shortMilliseconds));
                }else{
                    arrayAdapter.clear();
                    milliseconds = 0;
                    secondCounter = 0;
                    timeTextView.setText("00:00.0");

                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

    }
    private void runStopwatch(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
               //int hours = seconds/3600;
               minutes = (secondCounter%3600)/60;
               seconds = secondCounter%60;
               shortMilliseconds = (int) ((milliseconds%1000)/100);

               String time = String.format("%02d:%02d.%d", minutes, seconds, shortMilliseconds);
               if (isStart){
                   timeTextView.setText(time);
                   milliseconds+=100;
                   if (milliseconds%1000 == 0){
                       secondCounter++;
                   }
                   handler.postDelayed(this, 100);
               }
            }
        });
    }

    private void changeButton(){
        if (isStart){
            startStopButton.setText("стоп");
            startStopButton.setBackgroundColor(Color.RED);
            intervalButton.setText("интервал");

        }else{
            startStopButton.setText("старт");
            startStopButton.setBackgroundColor(Color.rgb( 76, 175, 80));
            intervalButton.setText("Сбросить");
        }
    }
}