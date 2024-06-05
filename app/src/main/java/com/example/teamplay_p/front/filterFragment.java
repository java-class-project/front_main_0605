package com.example.teamplay_p.front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.method.SpinnerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public class filterFragment extends DialogFragment {

    private ApiService apiService;

    // 필터링 내용 저장을 위한 생성자 필요?

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

        apiService = RetrofitClient.getClient(null).create(ApiService.class);

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
                        UUID majorUUID = selectedMajor.getMajorUuid(); // Major UUID를 가져옴
                        // 전공에 따른 과목 목록과 스피너 연결
                        SpinnerUtils.loadSubjectList(getContext(), spn_subject, majorUUID);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 과목 선택 시
        spn_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){  // 분반 스피너 배열과 연결
                    // XML 파일에서 문자열 배열을 가져오기
                    String[] subjectNumArray = getResources().getStringArray(R.array.Snum_array);
                    // 배열을 ArrayList로 변환
                    ArrayList<String> subjectNumList = new ArrayList<>(Arrays.asList(subjectNumArray));
                    // ArrayList의 첫 번째 위치에 "전체"를 추가
                    subjectNumList.add(0, "전체");

                    // 어댑터 생성
                    ArrayAdapter<String > subjectNumAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, subjectNumList);
                    // 어댑터 연결
                    spn_dv.setAdapter(subjectNumAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 적용하기 버튼 클릭 리스너 설정
        AppCompatButton applyButton = view.findViewById(R.id.btn_ftAPPLY);
        applyButton.setOnClickListener(v -> {
            // 적용하기 버튼 클릭 시의 로직
        });

        return view;
    }

}