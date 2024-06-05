package com.example.teamplay_p.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.teamplay_p.R;

public class Popup extends AppCompatActivity {

    AppCompatButton btn_done;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        // 타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        btn_done = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(v1 ->{
            Intent intent = new Intent(this, Signup.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 기존의 Signup 액티비티를 최상위로
            startActivity(intent);
            //finish(); // 현재 액티비티 종료
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        // 바깥레이어 클릭시 닫히지 않도록
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}