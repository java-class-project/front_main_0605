package com.example.teamplay_p.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.user.JoinRequest;
import com.example.teamplay_p.dto.user.UserResponse;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    private ApiService apiService;

    private EditText Edt_username, Edt_userId, Edt_password, Edt_passwordCheck, Edt_studentNumber;
    private String username, userId, password, confirmPassword, studentNumber;
    private String mainMajor, subMajor1, subMajor2;

    private boolean isIdChecked, isStudentNumChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Retrofit 클라이언트 생성
        apiService = RetrofitClient.getClient(null).create(ApiService.class);

        // spinner
        Spinner major_spinner = findViewById(R.id.major_spinner);
        Spinner double_spinner = findViewById(R.id.double_spinner);
        Spinner minor_spinner = findViewById(R.id.minor_spinner);

        // 전공 목록 불러와서 스피너에 설정
        loadMajorList(major_spinner);
        loadMajorList(double_spinner);
        loadMajorList(minor_spinner);

        // view 초기화
        Edt_username = findViewById(R.id.edt_username);
        Edt_userId = findViewById(R.id.edt_userId);
        Edt_password = findViewById(R.id.edt_pw);
        Edt_passwordCheck = findViewById(R.id.edt_pw_check);
        Edt_studentNumber = findViewById(R.id.edt_studentNum);

        // 비밀번호, id, 학번 확인
        TextView txt_check = findViewById(R.id.txt_check);
        TextView txt_idCheck = findViewById(R.id.txt_idCheck);
        TextView txt_snCheck = findViewById(R.id.txt_snCheck);

        // 비밀번호 중복 확인
        Edt_passwordCheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {      // 포커스를 잃었을 때
                    if (Edt_password.getText().toString().equals(Edt_passwordCheck.getText().toString())) {
                        // 비밀번호 확인이 일치하는 경우
                        txt_check.setHint("비밀번호가 일치합니다");
                        txt_check.setHintTextColor(Color.GRAY);
                    } else {
                        // 비밀번호 확인이 불일치 하는 경우
                        txt_check.setHint("비밀번호가 일치하지 않습니다");
                        txt_check.setHintTextColor(Color.RED);
                    }
                }
            }
        });

        isIdChecked = false;  // 아이디 중복 확인 버튼 클릭 여부 저장
        // 아이디 중복 확인 버튼 클릭 리스너
        AppCompatButton Btn_IDcheck = findViewById(R.id.btn_idCheck);

        Btn_IDcheck.setOnClickListener(v -> {
            userId = Edt_userId.getText().toString();
            if(TextUtils.isEmpty(userId)){
                txt_idCheck.setHint("아이디를 입력하세요");
                txt_idCheck.setHintTextColor(Color.RED);
            }
            else{
                // 아이디 중복 확인 로직
                Call<Boolean> call = apiService.checkUserIdDuplicate(userId);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body()){  // (response.body() = true) 아이디 사용 가능(중복X)
                                    isIdChecked = true;

                                    txt_idCheck.setHint("사용 가능한 아이디입니다");
                                    txt_idCheck.setHintTextColor(Color.GRAY);
                                }
                                else {   // (response.body() = false) 아이디 중복
                                    Edt_userId.setText("");  // 아이디 입력창 비우기
                                    userId = "";

                                    txt_idCheck.setHint("이미 존재하는 아이디입니다");
                                    txt_idCheck.setHintTextColor(Color.RED);
                                }
                            }
                            else{
                                Log.e("Signup", "[아이디 버튼] 응답 본문이 null입니다.");
                            }
                        }
                        else{
                            Log.e("Signup", "[아이디 버튼] 응답이 실패했습니다. 코드: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.e("Signup", "[아이디 버튼] 서버 요청에 실패하였습니다.", t);
                        Toast.makeText(Signup.this, "[아이디 버튼] 서버 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        isStudentNumChecked = false;  // 아이디 중복 확인 버튼 클릭 여부 저장

        // 학번 중복 확인 버튼 클릭 리스너
        AppCompatButton Btn_SNcheck = findViewById(R.id.btn_studentNumCheck);

        Btn_SNcheck.setOnClickListener(v -> {
            studentNumber = Edt_studentNumber.getText().toString();
            if(TextUtils.isEmpty(studentNumber)){
                txt_snCheck.setHint("학번을 입력하세요");
                txt_snCheck.setHintTextColor(Color.RED);
            }
            else{
                // 학번 중복 확인 로직
                Call<Boolean> call = apiService.checkStudentNumberDuplicate(studentNumber);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body()){  // (response.body() = true) 학번 사용 가능(중복x)
                                    isStudentNumChecked = true;

                                    txt_snCheck.setHint("사용 가능한 학번입니다");
                                    txt_snCheck.setHintTextColor(Color.GRAY);
                                }
                                else {   // (response.body() = false) 학번 중복
                                    Edt_studentNumber.setText("");  // 학번 입력창 비우기
                                    studentNumber = "";

                                    txt_snCheck.setHint("이미 존재하는 학번입니다");
                                    txt_snCheck.setHintTextColor(Color.RED);
                                }
                            }
                            else{
                                Log.e("Signup", "[학번 버튼] 응답 본문이 null입니다.");
                            }
                        }
                        else{
                            Log.e("Signup", "[학번 버튼] 응답이 실패했습니다. 코드: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.e("Signup", "[학번 버튼] 서버 요청에 실패하였습니다.", t);
                        Toast.makeText(Signup.this, "[학번 버튼] 서버 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        // 백 작업

        // 회원가입 버튼 클릭 시
        AppCompatButton signupButton = findViewById(R.id.signupBT);
        signupButton.setOnClickListener(v -> {

            username = Edt_username.getText().toString();
            userId = Edt_userId.getText().toString();
            password = Edt_password.getText().toString();
            confirmPassword = Edt_passwordCheck.getText().toString();
            studentNumber = Edt_studentNumber.getText().toString();

            // 유효성 검사
            if(TextUtils.isEmpty(username)){     // 이름 미입력 시
                Toast.makeText(Signup.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            }
            else{
                if(TextUtils.isEmpty(userId)){     // 아이디 미입력 시
                    Toast.makeText(Signup.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!isIdChecked){      // 아이디 중복 확인 미수행 시
                        Toast.makeText(Signup.this,"사용 가능한 아이디인지 확인하세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(TextUtils.isEmpty(password)){     // 비밀번호 미입력 시
                            Toast.makeText(Signup.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (TextUtils.isEmpty(confirmPassword)) {     // 비밀번호 재확인 미입력 시
                                Toast.makeText(Signup.this, "비밀번호 재확인을 입력하세요", Toast.LENGTH_SHORT).show();
                            } else {
                                if (TextUtils.isEmpty(studentNumber)) {     // 학번 미입력 시
                                    Toast.makeText(Signup.this, "학번을 입력하세요", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!isStudentNumChecked) {        // 학번 중복 확인 미수행 시
                                        Toast.makeText(Signup.this, "사용 가능한 학번인지 확인하세요", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // 스피너(전공 선택)
                                        // 본전공(필수 선택) 유효성 검사
                                        int selectedPosition = major_spinner.getSelectedItemPosition();
                                        if (selectedPosition == 0) {      // 본전공 미선택 시
                                            Toast.makeText(Signup.this, "본전공을 선택하세요", Toast.LENGTH_SHORT).show();
                                        } else {                          // 본전공 선택한 경우
                                            Major selectedMainmajor = (Major) major_spinner.getSelectedItem(); // 스피너 선택 아이템 major 타입으로 변환해서 major 객체 selectedMainmajor에 받아옴
                                            mainMajor = selectedMainmajor.getMajorUuid().toString(); // getter로 UUID를 가져와서 mainMajor에 저장(String타입)
                                            Major selectedSubmajor1 = (Major) double_spinner.getSelectedItem();
                                            UUID subMajor1Uuid = selectedSubmajor1.getMajorUuid();
                                            if (subMajor1Uuid != null) {
                                                subMajor1 = subMajor1Uuid.toString();
                                            } else {
                                                Log.i("Signup", "복수전공 미선택");
                                            }

                                            Major selectedSubmajor2 = (Major) minor_spinner.getSelectedItem();
                                            UUID subMajor2Uuid = selectedSubmajor2.getMajorUuid();
                                            if (subMajor2Uuid != null) {
                                                subMajor2 = subMajor2Uuid.toString();
                                            } else {
                                                Log.i("Signup", "부전공 미선택");
                                            }

                                            // 비밀번호 일치 여부 유효성 검사
                                            if (password.equals(confirmPassword)) {   // 비밀번호 확인이 일치하는 경우
                                                //JoinRequest 객체 생성
                                                JoinRequest joinRequest = new JoinRequest(username, userId, password, confirmPassword, studentNumber, mainMajor, subMajor1, subMajor2);

                                                Call<UserResponse> call = apiService.registerUser(joinRequest);
                                                call.enqueue(new Callback<UserResponse>() {
                                                    @Override
                                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                                        if (response.isSuccessful() && response.body() != null) { // 회원가입 성공 시
                                                            UserResponse userResponse = response.body();
                                                            Toast.makeText(Signup.this, "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Signup.this, MainActivity.class);  // 로그인 화면으로 이동
                                                            startActivity(intent);
                                                        } else {  // 회원가입 실패 시
                                                            // response.message(): 서버 응답이 실패했을 때, 서버에서 제공하는 메시지를 표시
                                                            Toast.makeText(Signup.this, "signup failed." + response.message(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<UserResponse> call, Throwable t) {
                                                        // t.getMessage(): 네트워크 호출이 실패했을 때 발생한 예외의 메시지를 표시
                                                        Toast.makeText(Signup.this, "Signup error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            } else {    // 비밀번호 확인이 불일치 하는 경우
                                                Toast.makeText(Signup.this, "비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                                            } // 비밀번호 일치 여부 유효성 검사
                                        } // 본전공 선택 유효성 검사
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        // Toolbar, ActionBar
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);    // 뒤로가기 버튼 만들기


    } // OnCreate()

    // 전공 목록을 가져와서 스피너에 연결하는 메소드
    private void loadMajorList(Spinner spinner){
        // Retrofit 클라이언트 생성
        apiService = RetrofitClient.getClient(null).create(ApiService.class);
        Call<List<Major>> call = apiService.getAllMajors();
        call.enqueue(new Callback<List<Major>>() {
            @Override
            public void onResponse(Call<List<Major>> call, Response<List<Major>> response) {
                if(response.isSuccessful()){
                    List<Major> majorList = response.body();
                    if(majorList!=null){
                        // 힌트 텍스트 추가
                        Major hint = new Major("전공 선택", null);
                        majorList.add(0,hint);

                        // 전공 목록을 받아와서 스피너에 연결하는 어댑터 생성
                        // ArrayAdapter는 Major 객체 리스트를 받아서 각 객체의 toString() 메소드를 호출하여 문자열로 변환하고 스피너에 표시
                        // 스피너는 문자열 형태로 화면에 표시!
                        ArrayAdapter<Major> adapter = new ArrayAdapter<Major>(Signup.this, android.R.layout.simple_spinner_dropdown_item, majorList){
                            /*
                            @Override
                            public boolean isEnabled(int position){
                                // 첫 번째 항목은 선택할 수 없도록 비활성화
                                return position != 0;
                            }
                            */
                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // 힌트 항목은 회색으로 표시
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };

                        // 스피너에 어댑터 설정
                        spinner.setAdapter(adapter);

                    }
                }
                else {
                    // 오류 처리
                }
            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                // 네트워크 오류 등의 처리
            }
        });
    }

    @SuppressLint("NonConstantResourcedID")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home :   // 뒤로가기 버튼 눌렀을 때
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 기존의 MainActivity를 최상위로
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}