package com.example.teamplay_p.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.meeting.CreateMeetingRequest;
import com.example.teamplay_p.dto.meeting.Meeting;
import com.example.teamplay_p.dto.subject.subject;
import com.example.teamplay_p.method.SpinnerUtils;

import java.io.IOException;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wtProfileFragment extends Fragment {
    private ApiService apiService;
    private String teamType, title, description;
    private UUID majorUuid, subjectUuid;
    private int desiredCount, classNum;

    @Nullable
    @Override       // 프래그먼트가 뷰 계층을 처음 생성할 때 호출
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wt_profile, container, false);
    }


    @Override      // onCreateView 메서드가 호출된 이후에 호출
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView major_check = view.findViewById(R.id.major_check);
        TextView subject_check = view.findViewById(R.id.subject_check);

        // 전공, 과목 스피너 연결
        // Spinner 설정
        Spinner spn_major = view.findViewById(R.id.wtSpinner_major);
        Spinner spn_subject = view.findViewById(R.id.wtSpinner_lec);
        Spinner spn_Snum = view.findViewById(R.id.wtSpinner_dv);

        // 전공 목록과 스피너 연결
        SpinnerUtils.loadMajorList(getContext(), spn_major);

        // 분반 스피너 어댑터
        // ArrayAdapter를 사용하여 Spinner에 데이터를 설정
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Snum_array, android.R.layout.simple_spinner_item);

        // 드롭다운 레이아웃을 설정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner에 어댑터를 설정
        spn_Snum.setAdapter(adapter);

        // 전공 선택 유효성 검사
        spn_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){  // 전공 미선택 시
                    major_check.setHint("전공을 선택하세요");
                    major_check.setHintTextColor(Color.RED);
                }
                else{    // 전공 선택 시
                    major_check.setHint("전공이 선택되었습니다");
                    major_check.setHintTextColor(Color.GRAY);

                    Major selectedMajor = (Major) parent.getSelectedItem(); // 선택한 전공 item Major 변수로 받아오기
                    if (selectedMajor != null) {
                        UUID majorUUID = selectedMajor.getMajorUuid(); // Major UUID를 가져옴
                        // 전공에 따른 과목 리스트 연결
                        SpinnerUtils.loadSubjectList(getContext(), spn_subject, majorUUID);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 과목 선택 유효성 검사
        spn_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){   // 과목 미선택 시
                    subject_check.setHint("과목을 선택하세요");
                    subject_check.setHintTextColor(Color.RED);
                } else { // 과목이 선택된 경우
                    subject_check.setHint("과목이 선택되었습니다");
                    subject_check.setHintTextColor(Color.GRAY);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 작성완료 버튼 클릭 시
        AppCompatButton wtProfileButton = view.findViewById(R.id.btn_wtProfile);
        wtProfileButton.setOnClickListener(v -> {
            showToast("작성완료 버튼이 클릭되었습니다.");

            /// 팀형태 선택 처리
            RadioGroup radioGroup = view.findViewById(R.id.teamtype);
            //  선택된 라디오 버튼의 ID 가져오기
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId == -1) {  // 팀 형태를 선택하지 않은 경우 / 선택된 라디오 버튼이 없으면 -1 반환
                showToast("팀 형태를 선택하세요");
                return;
            }

            // 선택된 라디오 버튼 참조
            RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
            // 선택된 라디오 버튼의 텍스트 가져오기
            String selectedTeamtype = selectedRadioButton.getText().toString();
            switch (selectedTeamtype){                                   // teamtype
                case "팀플":
                    teamType = "TeamProject";
                    break;
                case "스터디":
                    teamType = "Study";
                    break;
                case "프로젝트":
                    teamType = "Project";
                    break;
                default:
                    showToast("팀 형태 처리가 잘못되었습니다.");
                    break;
            }
            /// 전공, 과목 선택 처리
            int selectedMajorPosition = spn_major.getSelectedItemPosition();
            if (selectedMajorPosition == 0) { // 전공을 선택하지 않은 경우
                showToast("전공을 선택하세요");
                return;
            }
            int selectedSubjectPosition = spn_subject.getSelectedItemPosition();
            if (selectedSubjectPosition == 0) {   // 과목을 선택하지 않은 경우
                showToast("과목을 선택하세요");
                return;
            }

            Major selectedMajor = (Major) spn_major.getSelectedItem();
            subject selectedSubject = (subject) spn_subject.getSelectedItem();

            // 전공 선택 확인
            if(selectedMajor==null){
                showToast("전공을 선택하세요");
                return;
            }
            // 과목 선택 확인
            if(selectedSubject==null){
                showToast("과목을 선택하세요");
                return;
            }

            majorUuid = selectedMajor.getMajorUuid();          // majorUuid
            subjectUuid = selectedSubject.getSubjectUuid();    // subjectUuid

            /// 분반 선택 처리
            String selectedClassNum = (String) spn_Snum.getSelectedItem();
            classNum = Integer.parseInt(selectedClassNum);// classNum (string -> integer)

            /// 희망 인원수 선택 처리
            Spinner spn_Pnum = view.findViewById(R.id.wtSpinner_num);
            desiredCount = Integer.parseInt(spn_Pnum.getSelectedItem().toString()); // meetingRecruitment

            /// 제목, 설명 처리  meetingInfo?
            EditText meetingT = view.findViewById(R.id.meetingTitle);
            EditText meetingD = view.findViewById(R.id.meetingDescription);
            title = meetingT.getText().toString();
            description = meetingD.getText().toString();

            if (TextUtils.isEmpty(title)) {    // 제목 미입력 시
                showToast("제목을 입력하세요");
                return;
            }

            if (TextUtils.isEmpty(description)) {  // 설명 미입력 시
                showToast("설명을 입력하세요");
                return;
            }


            // 토큰 읽어오기
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
            String authToken = sharedPreferences.getString("authToken", "");


            Log.d("TOKEN", "Retrieved token: " + authToken);

            apiService = RetrofitClient.getClient(authToken).create(ApiService.class);
            // apiService = RetrofitClient.getClient(authToken).create(ApiService.class);

            CreateMeetingRequest createMeetingRequest = new CreateMeetingRequest(teamType, majorUuid, subjectUuid, classNum, desiredCount, title, description);
            Call<Meeting> call = apiService.createMeeting(createMeetingRequest);
            call.enqueue(new Callback<Meeting>() {
                @Override
                public void onResponse(Call<Meeting> call, Response<Meeting> response) {
                    if (response.isSuccessful()) {
                        if (response.body() == null){
                            showToast("응답 내용이 NULL입니다");
                            return;
                        }
                        else{
                            Meeting meeting = response.body();
                            showToast("프로필이 생성되었습니다");

                            // 홈화면으로 돌아가기
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            // homeFragment로 교체
                            fragmentTransaction.replace(R.id.frame_layout, new homeFragment());
                            fragmentTransaction.commit();
                        }

                    } else {  // response.isSuccessful()이 false일 경우(서버 요청 실패)
                        // 서버 응답의 상태 코드를 가져와 statusCode 변수에 저장
                        int statusCode = response.code();   // 서버 응답 상태 코드
                        // 기본 오류 메시지를 생성
                        String errorMessage = "프로필 생성에 실패했습니다. 상태코드: " + statusCode;
                        if(response.errorBody() != null){   // 서버 응답에 오류 본문이 있는지 확인
                            String errorResponse = "";
                            try (ResponseBody errorBody = response.errorBody()){ // 오류 본문이 있을 경우
                                errorResponse = errorBody.string();
                                errorMessage += ", 오류 메세지: " + errorResponse;
                            } catch (IOException e){  // 예외처리
                                e.printStackTrace();
                            }
                        }
                        Log.e("API_ERROR", errorMessage);   // 오류 메세지 로그에 출력
                        showToast(errorMessage);
                    }

                }

                @Override
                public void onFailure(Call<Meeting> call, Throwable t) {
                    Log.e("API_FAILURE", "서버 요청에 실패했습니다",t);
                    showToast("서버 요청에 실패했습니다");
                }
            });
        });

        // Toolbar 설정
        Toolbar toolbar = view.findViewById(R.id.wt_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);    // 뒤로가기 버튼 만들기
        }
        // 뒤로가기 버튼 색상 변경
        toolbar.getNavigationIcon().setTint(ContextCompat.getColor(getContext(), R.color.white));

        // 뒤로가기 버튼 클릭 리스너 설정
        toolbar.setNavigationOnClickListener(v -> {
            // 뒤로가기 버튼 클릭시

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // homeFragment로 교체
            fragmentTransaction.replace(R.id.frame_layout, new homeFragment());
            fragmentTransaction.commit();
        });
    }

    // UI 스레드에서 안전하게 Toast 메시지를 출력하기 위한 메서드
    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }
}
