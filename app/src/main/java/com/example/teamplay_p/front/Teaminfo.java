package com.example.teamplay_p.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.meeting.MeetingUserResponse;
import com.example.teamplay_p.dto.user.User;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Teaminfo extends Fragment {

    private ArrayList<UserList> userListArrayList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teaminfo, container, false);
        userListArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.teaminfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(getContext(), userListArrayList);
        recyclerView.setAdapter(userAdapter);

        fetchTeamInfo();

        return view;
    }

    private void fetchTeamInfo() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String meetingUuidString = bundle.getString("meetingUuid");
            UUID meetingId = UUID.fromString(meetingUuidString);

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("Token", Context.MODE_PRIVATE);
            String authToken = sharedPreferences.getString("authToken", "");

            Retrofit retrofit = RetrofitClient.getClient(authToken);
            ApiService apiService = retrofit.create(ApiService.class);

            Call<MeetingUserResponse> call = apiService.getUsersByMeeting(meetingId);
            call.enqueue(new Callback<MeetingUserResponse>() {
                @Override
                public void onResponse(Call<MeetingUserResponse> call, Response<MeetingUserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        for (MeetingUserResponse.UserWithRole user : response.body().getUsers()) {
                            int profileImg = R.mipmap.ic_launcher;
                            userListArrayList.add(new UserList(profileImg, user.getUsername(),user.getMeetingRole(), user.getStudentNumber(), user.getMainMajor(), user.getSubMajor1(), user.getSubMajor2()));
                        }
                        userAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "데이터를 가져오지 못했습니다: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MeetingUserResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "미팅 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
