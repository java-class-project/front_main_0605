package com.example.teamplay_p.method;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.subject.subject;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerUtils {

    // 전공 목록 가져와서 스피너에 연결하는 메소드
    public static void loadMajorList(Context context, Spinner spinner) {
        ApiService apiService = RetrofitClient.getClient(null).create(ApiService.class);
        Call<List<Major>> call = apiService.getAllMajors();
        call.enqueue(new Callback<List<Major>>() {
            @Override
            public void onResponse(Call<List<Major>> call, Response<List<Major>> response) {
                if (response.isSuccessful()) {
                    List<Major> majorList = response.body();
                    if (majorList != null) {
                        Major hint = new Major("전공 선택", null);
                        majorList.add(0, hint);

                        ArrayAdapter<Major> adapter = new ArrayAdapter<Major>(context, android.R.layout.simple_spinner_dropdown_item, majorList) {
                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        spinner.setAdapter(adapter);
                    }
                } else {
                    // 오류 처리
                }
            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                // 네트워크 오류 등의 처리
            }
        });
    }

    public static void loadSubjectList(Context context, Spinner spinner, UUID major) {
        // Retrofit 클라이언트 생성
        ApiService apiService = RetrofitClient.getClient(null).create(ApiService.class);
        Call<List<subject>> call = apiService.getAllSubjects(major);
        call.enqueue(new Callback<List<subject>>() {
            @Override
            public void onResponse(Call<List<subject>> call, Response<List<subject>> response) {
                if (response.isSuccessful()) {
                    List<subject> subjectList = response.body();
                    if (subjectList != null) {
                        // 힌트 텍스트 추가
                        subject hint = new subject(null, "과목 선택", null);
                        subjectList.add(0, hint);

                        // 과목리스트를 받아와서 스피너에 연결하는 어댑터 생성
                        ArrayAdapter<subject> adapter = new ArrayAdapter<subject>(context, android.R.layout.simple_spinner_dropdown_item, subjectList) {
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
                } else {
                    // 오류 처리
                }

            }

            @Override
            public void onFailure(Call<List<subject>> call, Throwable t) {

            }
        });
    }
}

