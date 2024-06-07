package com.example.teamplay_p.front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.meeting.MeetingResponse;
import com.example.teamplay_p.dto.user.UserResponse;
import com.example.teamplay_p.front.InfoEditFragment;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.teamplay_p.R;

import java.io.IOException;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myPageFragment extends Fragment{

    private ApiService apiService;
    public static String uuid;

    public myPageFragment() {
        // Required empty public constructor

    }

    public static myPageFragment newInstance(String param1, String param2) {
        myPageFragment fragment = new myPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        // 토큰 읽어오기
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("authToken", "");

         uuid = RetrofitClient.Uuid;
        // Retrofit 클라이언트 생성
        apiService = RetrofitClient.getClient(authToken).create(ApiService.class);
        Call<UserResponse> call = apiService.getUserInfo(uuid);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        String usernameText = userResponse.getUsername() != null ? userResponse.getUsername() : "N/A";
                        String studentidText = userResponse.getStudentNumber();
                        String mainMajorName = userResponse.getMainMajorName() != null ? userResponse.getMainMajorName() : "N/A";
                        String subMajor1Name = userResponse.getSubMajor1Name() != null ? userResponse.getSubMajor1Name() : "";
                        String subMajor2Name = userResponse.getSubMajor2Name() != null ? userResponse.getSubMajor2Name() : "";

                        TextView username = view.findViewById(R.id.user_name);
                        TextView studentid = view.findViewById(R.id.user_id);
                        TextView usermajor = view.findViewById(R.id.user_major);
                        Log.d("TAG", usernameText);
                        username.setText(usernameText);
                        studentid.setText(studentidText);
                        usermajor.setText(mainMajorName + "\n" + subMajor1Name + "\n" + subMajor2Name);
                    } else {
                        Log.d("UserInfo", "Response body is null");
                        Toast.makeText(getActivity(), "Response body is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    String message = response.message();
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("UserInfo", "Response is not successful: " + statusCode + " " + message + " " + errorBody);
                    Toast.makeText(getActivity(), "Edit Info failed. Status code: " + statusCode + ", Message: " + message, Toast.LENGTH_SHORT).show();

                    if (statusCode == 403) {
                        Log.d("UserInfo", "403 Forbidden: Access is denied. Check your token and permissions.");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("UserInfo", "Network request failed", t);
                Toast.makeText(getActivity(), "Network request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<MeetingResponse> teamcall = apiService.GetTeamInfo(uuid);
        teamcall.enqueue(new Callback<MeetingResponse>() {
            @Override
            public void onResponse(Call<MeetingResponse> call, Response<MeetingResponse> response) {
                if (response.isSuccessful()) {
                    MeetingResponse meetingResponse = response.body();
                    if (meetingResponse != null) {
                        CardView teamView = view.findViewById(R.id.cardview2);
                        teamView.setVisibility(View.INVISIBLE);
                        String teamName = meetingResponse.getSubjectName();
                        String teamMemberName = meetingResponse.getUsername();
                        String teamMemberMajor = meetingResponse.getuserMajor();
                        String nowMemberCount = meetingResponse.getuserstudentNumber();
                        String desireMemberCount = Integer.toString(meetingResponse.getDesiredCount());

                        TextView teamname = view.findViewById(R.id.team_course);
                        TextView teamMemberInfo = view.findViewById(R.id.team_info);
                        TextView teamMemberCount = view.findViewById(R.id.team_members);
                        Button isTeamAlive = view.findViewById(R.id.btn_recruit);

                        teamname.setText(teamName);
                        teamMemberInfo.setText(teamMemberName+"("+teamMemberMajor+")");
                        teamMemberCount.setText(nowMemberCount+" / "+desireMemberCount);

                        if(nowMemberCount.equals(desireMemberCount))
                            isTeamAlive.setText("모집 완료");
                        else
                            isTeamAlive.setText("인원 모집중");
                    }
                    else {
                        CardView teamView = view.findViewById(R.id.cardview2);
                        teamView.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MeetingResponse> call, Throwable t) {
                CardView teamView = view.findViewById(R.id.cardview2);
                teamView.setVisibility(View.INVISIBLE);
            }
        });

        // btn_infoEdit 버튼을 찾습니다.
        Button btnInfoEdit = view.findViewById(R.id.btn_InfoEdit);

        // btn_infoEdit 버튼에 클릭 리스너를 설정합니다.
        btnInfoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // InfoEditFragment로 이동합니다.
                InfoEditFragment infoEditFragment = new InfoEditFragment();
                // FragmentManager를 사용하여 Fragment를 추가합니다.
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) // 애니메이션 효과 추가 (선택사항)
                        .replace(R.id.frame_layout, infoEditFragment)
                        .addToBackStack(null) // 뒤로 가기 버튼을 눌렀을 때 이전 Fragment로 돌아갈 수 있도록 백 스택에 추가합니다.
                        .commit();
            }
        });

        return view;
    }



}

