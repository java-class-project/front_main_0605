package com.example.teamplay_p.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.meeting.MeetingResponse;
import com.example.teamplay_p.dto.subject.subject;
import com.example.teamplay_p.method.SpinnerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class filterFragment extends DialogFragment {

    private ApiService apiService;

    private UUID majorUuid;
    private UUID subjectUuid;
    private String teamType;
    private CheckBox checkBoxTeam, checkBoxStudy, checkBoxProject;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {    // getDialog() : 프래그먼트가 다이얼로그로 표시되고 있을 때 다이얼로그 객체 반환
            // 다이얼로그의 크기 설정
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        // 토큰 읽어오기
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");


        Log.d("TOKEN", "Retrieved token: " + authToken);

        apiService = RetrofitClient.getClient(authToken).create(ApiService.class);

        // 전공, 과목 스피너 연결
        // 스피너 설정
        Spinner spn_major = view.findViewById(R.id.ftSpinner_major);
        Spinner spn_subject = view.findViewById(R.id.ftSpinner_lec);
        Spinner spn_dv = view.findViewById(R.id.ftSpinner_dv);

        //전공 목록과 스피너 연결
        SpinnerUtils.loadMajorList(getContext(),spn_major);
        // 전공 선택 시
        spn_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){  // 전공 선택 시
                    Major selectedMajor = (Major) parent.getSelectedItem(); // 선택한 전공 item Major 변수로 받아오기
                    if (selectedMajor != null) {
                        majorUuid = selectedMajor.getMajorUuid(); // Major UUID를 가져옴
                        // 전공에 따른 과목 목록과 스피너 연결
                        SpinnerUtils.loadSubjectList(getContext(), spn_subject, majorUuid);
                    }
                }
                majorUuid = null;     // 전공 선택하지 않았을 경우, null 전달
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                majorUuid = null;       // 전공 선택하지 않았을 경우, null 전달
            }
        });

        // 과목 선택 시
        spn_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){  // 과목이 선택된 경우
                    // 선택된 과목 UUID 받아와서 저장
                    subject selectedSubject = (subject) parent.getSelectedItem(); // 선택한 전공 item subject 변수로 받아오기
                    if(selectedSubject!=null){
                        subjectUuid = selectedSubject.getSubjectUuid();     // 과목 UUID를 가져와서 저장

                        // 분반 스피너 배열과 연결
                        // XML 파일에서 문자열 배열을 가져오기
                        String[] subjectNumArray = getResources().getStringArray(R.array.Snum_array);
                        // 배열을 ArrayList로 변환
                        ArrayList<String> subjectNumList = new ArrayList<>(Arrays.asList(subjectNumArray));
                        // ArrayList의 첫 번째 위치에 "전체"를 추가
                        subjectNumList.add(0, "전체");

                        // 어댑터 생성
                        ArrayAdapter<String> subjectNumAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, subjectNumList);
                        // 어댑터 연결
                        spn_dv.setAdapter(subjectNumAdapter);
                    }
                }
                subjectUuid = null;   // 과목 선택하지 않았을 경우, null 전달
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subjectUuid = null;   // 과목 선택하지 않았을 경우, null 전달
            }
        });

        // 분반 드롭다운 선택 이벤트 처리 (전체 선택 시 추가)


        // 적용하기 버튼 클릭 리스너 설정
        AppCompatButton applyButton = view.findViewById(R.id.btn_ftAPPLY);
        applyButton.setOnClickListener(v -> {
            // 적용하기 버튼 클릭 시의 로직
            // 항목들 타입 맞춰서 받아오기
            // 선택된 필터링 조건들 전달해서 필터링 -> MeetingResponse 리스트 응답
            // 필터링이 반영된 홈 화면으로 전환

            /// 팀형태 선택 처리
            checkBoxTeam = view.findViewById(R.id.ftType_teamplay);
            checkBoxStudy = view.findViewById(R.id.ftType_study);
            checkBoxProject = view.findViewById(R.id.ftType_project);

            List<String> selectedTeamtypes = new ArrayList<>();
            if(checkBoxTeam.isChecked()){
                selectedTeamtypes.add(checkBoxTeam.getText().toString());
            }
            if(checkBoxStudy.isChecked()){
                selectedTeamtypes.add(checkBoxStudy.getText().toString());
            }
            if(checkBoxProject.isChecked()){
                selectedTeamtypes.add(checkBoxProject.getText().toString());
            }

            Call<List<MeetingResponse>> call = apiService.filterAndSearchMeetings(majorUuid, subjectUuid, selectedTeamtypes, null, null);
            call.enqueue(new Callback<List<MeetingResponse>>() {
                @Override
                public void onResponse(Call<List<MeetingResponse>> call, Response<List<MeetingResponse>> response) {
                    if(response.isSuccessful()){         // response.isSuccessful() == true
                        if(response.body()!=null){
                            // 서버 응답 성공 시
                            // 필터링된 모임 리스트 response.body() 에 저장됨
                            List<MeetingResponse> FilterMeetingList = response.body();    // 필터링된 모임 리스트 -> 홈화면에 출력되어야함
                            // 번들에 담아서 홈화면에 전달해서 출력?

                            showToast("필터링 검색을 수행합니다");
                            // 홈화면으로 이동
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            // homeFragment로 교체
                            fragmentTransaction.replace(R.id.frame_layout, new homeFragment());
                            fragmentTransaction.commit();
                        }
                        else{    // response.body() 가 없을 경우, 즉 필터링 결과 일치하는 내용이 없는 경우
                            // '검색 결과가 존재하지 않습니다' 토스트 메세지 출력
                            showToast("검색 결과가 존재하지 않습니다");

                        }
                    }
                    else{              // response.isSuccessful() == false(서버 요청 실패)
                        // 서버 응답의 상태 코드를 가져와 statusCode 변수에 저장
                        int statusCode = response.code();   // 서버 응답 상태 코드
                        // 기본 오류 메시지를 생성
                        String errorMessage = "프로필 생성에 실패했습니다. 상태코드: " + statusCode;
                        if(statusCode == 403){
                            if(response.errorBody() != null){
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Log.e("API_ERROR", "403 Forbidden: " + errorResponse);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("API_ERROR", "403 Forbidden: No error body");
                            }
                        }

                        showToast(errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<List<MeetingResponse>> call, Throwable t) {
                    Log.e("API_FAILURE", "서버 요청에 실패했습니다",t);
                    showToast("서버 요청에 실패했습니다");
                }
            });
        });


        return view;
    }

    // UI 스레드에서 안전하게 Toast 메시지를 출력하기 위한 메서드
    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

}