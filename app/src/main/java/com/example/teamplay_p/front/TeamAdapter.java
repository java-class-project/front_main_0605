package com.example.teamplay_p.front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamplay_p.R;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private static Context context;
    private ArrayList<TeamList> teamListArrayList;

    public TeamAdapter(Context context, ArrayList<TeamList> teamListArrayList) {
        this.context = context;
        this.teamListArrayList = teamListArrayList;

    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        TeamList teamList = teamListArrayList.get(position);
        holder.team_img.setImageResource(teamList.team_img);
        holder.classname.setText(teamList.className);
        holder.classnum.setText(teamList.classNum);
        holder.classtype.setText(teamList.classType);




    }

    @Override
    public int getItemCount() {
        return teamListArrayList.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        ImageView team_img;

        TextView classname,classnum, classtype;


        int  teampeoplenum;
        Button btn_showinfo;



        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            team_img = itemView.findViewById(R.id.team_icon);
            classname = itemView.findViewById(R.id.tv_className);
            classnum = itemView.findViewById(R.id.tv_ClassNum);
            classtype = itemView.findViewById(R.id.tv_ClassType);


            btn_showinfo = itemView.findViewById(R.id.btn_showinfo);

            btn_showinfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Teaminfo teaminfoFragment = new Teaminfo();
                    FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, teaminfoFragment);
                    transaction.addToBackStack(null);  // 뒤로가기 버튼을 눌렀을 때 이전 프래그먼트로 돌아가기 위해 백스택에 추가
                    transaction.commit();



                }
            });





        }


    }
}