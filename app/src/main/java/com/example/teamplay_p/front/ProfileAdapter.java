package com.example.teamplay_p.front;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileAdapterHolder> {

    static Context context;
    static ArrayList<ProfileList> profileListArrayList;
    private ArrayList<ProfileList> originalList;

    Button btn_register;

    private static int team_img;








    public ProfileAdapter(Context context, ArrayList<ProfileList> profileListArrayList) {

        this.context = context;
        this.profileListArrayList = profileListArrayList;
        this.originalList = new ArrayList<>(profileListArrayList); // 원본 목록 저장



    }

    @NonNull
    @Override
    public ProfileAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.profile_list,parent,false);

        return new ProfileAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapterHolder holder, @SuppressLint("RecyclerView") int position) {


        team_img = R.id.team_icon;

        ProfileList profileList = profileListArrayList.get(position);
        holder.profile_img.setImageResource(profileList.profile_img);
        holder.ClassName.setText(profileList.ClassName);
        holder.ClassNumber.setText(profileList.CLassnum);
        holder.TeamType.setText(profileList.Teamtype);
        holder.TeamLeader.setText(profileList.TeamLeader);
        holder.UserMajor.setText(profileList.UserMajor);
        holder.Userstunum.setText(profileList.Userstunum);
        holder.Desiredcount.setText(profileList.DesiredCount);
        holder.MeetingRecruitmentFinished.setText(profileList.MeetingRecruitmentFinished);
        holder.MeetingRecruitment.setText(profileList.MeetingRecruitment);
        holder.Title.setText(profileList.Title);
        holder.Description.setText(profileList.Description);

        // date를 TextView에 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(profileList.getDate());
        holder.Date.setText(dateString);

        holder.btn_teaminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프래그먼트를 이동하고 싶은 Activity를 확인합니다.
                FragmentActivity fragmentActivity = (FragmentActivity) context;
                // 새로운 Fragment를 생성합니다.
                Teaminfo newFragment = new Teaminfo();
                // 데이터 전달을 위해 번들 생성
                Bundle bundle = new Bundle();
                bundle.putString("meetingUuid", profileList.getMeetingUuid().toString());
                newFragment.setArguments(bundle);
                // FragmentTransaction을 시작합니다.
                FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                // 새로운 Fragment를 레이아웃에 표시합니다.
                fragmentTransaction.replace(R.id.frame_layout, newFragment);
                // 이전 Fragment를 Back Stack에 추가하여 뒤로 가기 동작이 가능하도록 합니다.
                fragmentTransaction.addToBackStack(null);
                // FragmentTransaction을 커밋하여 변경 사항을 적용합니다.
                fragmentTransaction.commit();
            }
        });




    }

    @Override
    public int getItemCount() {
        return profileListArrayList.size();
    }

    public void filterList(ArrayList<ProfileList> filteredList) {
        if (filteredList == null) {
            profileListArrayList = new ArrayList<>(originalList); // 전체 목록을 다시 할당
        } else {
            profileListArrayList = filteredList;
        }
        notifyDataSetChanged();
    }

    public class ProfileAdapterHolder extends RecyclerView.ViewHolder {

        Button btn_register,btn_teaminfo;
        ImageView profile_img;
        int team_img;

        TextView major,ClassName,Desiredcount, TeamType,Title, Description, TeamLeader,UserMajor, Userstunum,Date;

        TextView ClassNumber, MeetingRecruitment, MeetingRecruitmentFinished;


        public ProfileAdapterHolder(@NonNull View itemView) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.iv_profile);
            ClassName = itemView.findViewById(R.id.iv_ClassName);
            ClassNumber = itemView.findViewById(R.id.iv_subjectNum);
            TeamType = itemView.findViewById(R.id.iv_teamtype);
            TeamLeader = itemView.findViewById(R.id.iv_username);
            UserMajor = itemView.findViewById(R.id.iv_usermajor);
            Userstunum = itemView.findViewById(R.id.iv_userstnum);
            Desiredcount = itemView.findViewById(R.id.iv_desiredcount);
            MeetingRecruitment = itemView.findViewById(R.id.iv_meetingRecruitment);
            MeetingRecruitmentFinished = itemView.findViewById(R.id.iv_meetingRecruitmentFinished);
            Date = itemView.findViewById(R.id.iv_date);
            Title = itemView.findViewById(R.id.iv_title);
            Description = itemView.findViewById(R.id.iv_description);


            ImageView teamImgView = itemView.findViewById(R.id.team_icon);

            btn_register = itemView.findViewById(R.id.btn_register);
            //뒤에 가입버튼 누르면 동작되는거 코드적으면됨

            btn_teaminfo = itemView.findViewById(R.id.btn_showinfo);

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UUID meetingid = profileListArrayList.get(getAdapterPosition()).getMeetingUuid();

                    // SharedPreferences에서 userId 읽어오기
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
                    String applicantId = sharedPreferences.getString("userId", "");

                   // Display the meeting UUID and applicantId in a Toast
                    Toast.makeText(context, "Meeting UUID: " + meetingid + "\nApplicant ID: " + applicantId, Toast.LENGTH_SHORT).show();

                    // 토큰 읽어오기
                    String authToken = sharedPreferences.getString("authToken", "");

                    // Retrofit 클라이언트 생성
                    Retrofit retrofit = RetrofitClient.getClient(authToken);

                    // ApiService 인터페이스 구현체 생성
                    ApiService apiService = retrofit.create(ApiService.class);

                    // 서버에 POST 요청 보내기
                    Call<Void> call = apiService.applyForMeeting(meetingid);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // 서버 요청이 성공한 경우
                                // 예: 성공 메시지 표시
                                Toast.makeText(context, "신청이 성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show();


                                // 신청이 성공하면 respondToApplication 호출
                                Call<UUID> respondCall = apiService.respondToApplication(meetingid, applicantId, true);
                                respondCall.enqueue(new Callback<UUID>() {
                                    @Override
                                    public void onResponse(Call<UUID> call, Response<UUID> response) {
                                        if (response.isSuccessful()) {
                                            // respondToApplication이 성공한 경우
                                            Toast.makeText(context, "가입 신청이 성공적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // respondToApplication이 실패한 경우
                                            String errorMessage = "respond실패: " + response.code() + " " + response.message();
                                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UUID> call, Throwable t) {
                                        // respondToApplication이 실패한 경우
                                        Toast.makeText(context, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // 신청이 실패한 경우
                                String errorMessage = "실패: " + response.code() + " " + response.message();
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }




                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 서버 요청이 실패한 경우
                            // 네트워크 오류 등으로 인한 경우
                            // 예: 오류 메시지 표시
                            Toast.makeText(context, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });



        }







    }


}

/*
int clickedPosition = getAdapterPosition();


                    ProfileList clickedItem = profileListArrayList.get(clickedPosition);
                    String rc_className = clickedItem.getClassName();
                    String rc_profileName = clickedItem.getProfileName();
                    String rc_cmt = clickedItem.getCmt();
                    String rc_ClassNumber = clickedItem.getClassNumber();
                    String rc_TeamType = clickedItem.getTeamType();
                    String rc_Experience = clickedItem.getExperience();
                    String rc_Mbti = clickedItem.getMbti();


                    // 클릭된 항목을 삭제합니다.
                    profileListArrayList.remove(clickedPosition);
                    notifyItemRemoved(clickedPosition);


                    int teamImgResId = R.drawable.team_icon;
                    ArrayList<TeamList> teamListArrayList = new ArrayList<>(); // 새로운 데이터를 가져오는 부분은 여기에 구현해야 합니다.
                    TeamList teamList = new TeamList(teamImgResId, rc_className, rc_ClassNumber, rc_TeamType, 2);
                    teamListArrayList.add(teamList);

                    RecyclerView recyclerView = ((Activity) context).findViewById(R.id.tv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);

                    TeamAdapter teamAdapter = new TeamAdapter(context, teamListArrayList);
                    recyclerView.setAdapter(teamAdapter);
 */