package com.example.teamplay_p.front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.auth.LoginRequest;
import com.example.teamplay_p.dto.auth.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView sign;
    private ApiService apiService;
    private EditText editID, editPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 회원가입 버튼
        sign = findViewById(R.id.signup);

        // 회원가입 버튼 클릭시, 회원가입 페이지로 이동
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });


        // 백 작업
        // view 초기화
        editID = findViewById(R.id.inID);
        editPW = findViewById(R.id.inPW);
        AppCompatButton loginButton = findViewById(R.id.loginBT);

        // Retrofit 클라이언트 생성
        apiService = RetrofitClient.getClient(null).create(ApiService.class);

        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener(v -> {
            String userId = editID.getText().toString();
            String password = editPW.getText().toString();

            // 입력받은 id, pw 데이터를 담는 LoginRequest 객체 생성
            LoginRequest loginRequest = new LoginRequest(userId, password);

            // apiService : Retrofit 인터페이스 객체
            // loginUser(loginRequest) : 로그인 요청 메서드, 서버로 로그인 요청을 보냄
            // Call<> : Call 객체는 요청을 실행, 서버로부터의 응답을 처리할 수 있는 메서드를 가짐
            // Call<LoginResponse> : 요청의 응답 타입, 서버 응답이 LoginResponse로 기대됨
            Call<LoginResponse> call = apiService.loginUser(loginRequest);

            // enqueue 메서드 : 비동기적으로 요청을 보냄
            // onResponse 메서드 : 서버로부터 응답이 왔을 때 자동 호출됨. 요청이 성공했는지 확인하고 성공시 응답 데이터 처리
            // onFailure 메서드 : 요청이 실패했을 때 자동 호출됨. 여기서 오류를 처리함.
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {  // call은 요청 객체, response는 응답 객체
                    if(response.isSuccessful() && response.body()!=null){    // 로그인 성공 시
                        LoginResponse loginResponse = response.body();

                        // 토큰 저장
                        // SharedPreferences 간단한 데이터 저장 및 읽기 (여기서는 토큰)
                        SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();  // edit(): SharedPreferences를 수정하기 위해 Editor 객체를 얻음
                        editor.putString("authToken",loginResponse.getToken()); // putString(String key, String value) 문자열 데이터 저장
                        editor.apply();  // 변경 사항 저장(비동기적)

                        // userId 저장
                        editor.putString("userId", userId);
                        editor.apply();

                        // Toast : 짧은 시간 동안 메시지 화면에 표시
                        // MainActivity.this: 메시지를 표시할 화면
                        // "Login Successful!": 화면에 표시할 메세지
                        // Toast.LENGTH_LONG: 메세지를 표시할 시간 길이
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();   // 화면에 로그인 성공 토스트 메세지 표시
                        // Toast.makeText(MainActivity.this, "Login Successful! JWT: " + loginResponse.getJwt(), Toast.LENGTH_LONG).show();

                        // 홈화면으로 이동
                        Intent intent = new Intent(MainActivity.this, mainHomeActivity.class);
                        startActivity(intent);

                    }
                    else {     // 로그인 실패 시
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show(); // 화면에 로그인 실패 토스트 메세지 표시
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Login error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

}