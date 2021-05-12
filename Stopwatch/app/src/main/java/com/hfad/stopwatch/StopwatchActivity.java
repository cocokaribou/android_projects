package com.hfad.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("⚠️", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
//        if(savedInstanceState != null){
//            seconds = savedInstanceState.getInt("seconds"); //bundle에서 값을 얻어서 액티비티의 상태를 복원
//            running = savedInstanceState.getBoolean("running");
//            wasRunning = savedInstanceState.getBoolean("wasRunning");
//        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("⚠️", "onSaveInstanceState()");
        super.onSaveInstanceState(savedInstanceState); //이거 책에는 없는 부분인데 super를 안 부르면 실행이 안되나..?

        //Activity.java에는 @Callsuper 어노테이션이 없는데...

        //bundle은 key-value 쌍의 데이터를 모아놓는 곳
        //java의 map과 비슷하지만 안드로이드에 필요한 기능이 좀 더 많음
        //put자료형()

        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onStop(){
        Log.d("⚠️", "onStop()");
        super.onStop();
        wasRunning = running; //onStop()가 호출됐을 때 스톱워치가 실행중이었는지, running 여부 상태를 저장
        running = false;
    }

    @Override
    protected void onStart(){
        Log.d("⚠️", "onStart()");

        super.onStart();
        if(wasRunning){
            running = true;
        }
    }
    @Override
    protected void onDestroy() {
        Log.d("⚠️", "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d("⚠️", "onResume()");
        super.onResume();
    }

    //start 버튼을 클릭하면 스톱워치 시작
    public void onClickStart(View view){
        running = true;
    }

    //stop 버튼을 클릭하면 스톱워치 중지
    public void onClickStop(View view){
        running = false;
    }

    //reset 버튼을 클릭하면 스톱워치 리셋
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    //왜 버튼 onclick 메서드들은 public인데 runTimer는 private이지..?
    //runTimer()
    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        //Handler 클래스의 객체 생성
        final Handler handler = new Handler();

        //handler의 메서드를 이용하려면 runnable 객체로 감싸야한다
        //handler.post()
        handler.post(new Runnable() {

            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }

                //handler.postDelayed()
                //running이 false일때 실행
                handler.postDelayed(this, 1000);
            }
        });
    }
}