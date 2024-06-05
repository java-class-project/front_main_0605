package com.example.teamplay_p.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.meeting.MeetingResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class homeFragment extends Fragment {

    private ArrayList<ProfileList> profileArrayList;
    private ProfileAdapter profileAdapter;
    private RecyclerView recyclerView;

    private String[] className;
    private String[] classNumber;
    private String[] teamType;
    private String[] experience;
    private String[] mbti;
    private int profile_img;
    private String[] profileName;
    private String[] cmt;

    filterFragment FilterFragment;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 필터 버튼 찾기
        AppCompatButton filterButton = view.findViewById(R.id.btn_filter);
        // 필터 버튼 클릭 리스너 설정
        filterButton.setOnClickListener(v -> {
            FilterFragment = new filterFragment();

            // 필터 페이지와 연결 (다이얼로그 프래그먼트를 자식으로 추가)
            FragmentManager fragmentManager = getChildFragmentManager(); // 자식 프래그먼트 관리
            FilterFragment.show(fragmentManager, "FilterFragment");  // 다이얼로그 프래그먼트 추가
        });

        // 프로필 작성 버튼 찾기
        FloatingActionButton wtProfileButton = view.findViewById(R.id.btn_floating);
        // 프로필 작성 버튼 클릭 리스너 설정
        wtProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 프래그먼트로 교체
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // wtProfileFragment로 교체
                fragmentTransaction.replace(R.id.frame_layout, new wtProfileFragment());
                fragmentTransaction.commit();

            }
        });


        profileArrayList = new ArrayList<>();



        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileAdapter = new ProfileAdapter(getContext(), profileArrayList);
        recyclerView.setAdapter(profileAdapter);

        // 토큰 읽어오기
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");

        // 서버에서 데이터 불러오기
        fetchMeetings();

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileAdapter = new ProfileAdapter(getContext(), profileArrayList);
        recyclerView.setAdapter(profileAdapter);


        return view;




        }
        //미리 저장해둔 데이터 불러옴
        //dataInitialize();

    private void fetchMeetings() {
        // Retrofit 클라이언트 가져오기
        Retrofit retrofit = RetrofitClient.getClient(null);

        // ApiService 인터페이스 생성
        ApiService apiService = retrofit.create(ApiService.class);

        // 서버에 GET 요청 보내기
        Call<List<MeetingResponse>> call = apiService.getAllMeetings();
        call.enqueue(new Callback<List<MeetingResponse>>() {
            @Override
            public void onResponse(Call<List<MeetingResponse>> call, Response<List<MeetingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MeetingResponse> meetings = response.body();
                    if (meetings.isEmpty()) {
                        Toast.makeText(getContext(), "No meetings found", Toast.LENGTH_SHORT).show();
                    } else {
                        profileArrayList.clear(); // 기존 데이터를 지우고 새로운 데이터로 대체
                        for (MeetingResponse meeting : meetings) {
                            ProfileList profile = new ProfileList(
                                    R.mipmap.ic_launcher, // Example image resource
                                    meeting.getSubjectName(),
                                    meeting.getTeamType(),
                                    meeting.getUsername(),
                                    meeting.getuserMajor(),
                                    meeting.getuserstudentNumber(),
                                    String.valueOf(meeting.getDesiredCount()),
                                    meeting.getDate(),
                                    meeting.getTitle(),
                                    meeting.getDescription(),
                                    meeting.getMeetingUuid(),
                                    meeting.getUserId()
                            );


                            profileArrayList.add(profile);
                        }
                        // 데이터 저장



                        profileAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Meetings loaded successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load meetings. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MeetingResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }






    }

    /*
    private void dataInitialize() {
        className = new String[]{
                "객체지향프로그래밍",
                "컴퓨터아키텍쳐",
                "융사글",
                "논사소"
        };

        teamType = new String[]{
                "팀플",
                "스터디"
        };

        classNumber = new String[]{
                "1분반", "2분반", "3분반"
        };

        experience = new String[]{
                "유",
                "무"
        };

        mbti = new String[]{
                "intp",
                "isfp"
        };

        profile_img = R.mipmap.ic_launcher;

        profileName = new String[]{
                "현유리",
                "정은경",
                "이지민",
                "하지민",
                "최유정"
        };

        cmt = new String[]{
                "안녕하세요",
                "열심히 하겠습니다!"
        };

        ProfileList profileList = new ProfileList(profile_img, className[0], classNumber[0], teamType[0], experience[0], profileName[0], mbti[0], cmt[0]);
        profileArrayList.add(profileList);

        ProfileList profileList2 = new ProfileList(profile_img, className[1], classNumber[0], teamType[0], experience[0], profileName[1], mbti[1], cmt[1]);
        profileArrayList.add(profileList2);
    }

     */










