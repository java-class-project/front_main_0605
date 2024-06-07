package com.example.teamplay_p.front;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
                Fragment newFragment = new Teaminfo();
                // FragmentTransaction을 시작합니다.
                FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                // 새로운 Fragment를 레이아웃에 표시합니다.
                fragmentTransaction.replace(R.id.frame_layout, new Teaminfo());
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

        TextView ClassNumber;


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
                    String meetingId = profileListArrayList.get(getAdapterPosition()).getMeetingUuid().toString();
                    // 서버의 메서드 호출

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