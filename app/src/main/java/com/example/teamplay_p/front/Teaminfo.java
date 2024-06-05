package com.example.teamplay_p.front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamplay_p.R;
import com.example.teamplay_p.dto.user.User;

import java.util.ArrayList;

public class Teaminfo extends Fragment {

    private ArrayList<UserList> userListArrayList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teaminfo, container, false);
        userListArrayList = new ArrayList<>();

        int profile_img = R.mipmap.ic_launcher;
        userListArrayList.add(new UserList(profile_img, "현유리","2010929", "it공학과","",""));
        userListArrayList.add(new UserList(profile_img, "ㄱㄱㄱ","2210929", "수학과","it공학과",""));





        recyclerView = view.findViewById(R.id.teaminfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(getContext(), userListArrayList);
        recyclerView.setAdapter(userAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

