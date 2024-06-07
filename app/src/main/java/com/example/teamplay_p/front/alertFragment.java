package com.example.teamplay_p.front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.teamplay_p.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class alertFragment extends Fragment {


    private AlertListAdapter adapter;
    private ArrayList<Alertitem> alertItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert, container, false);

        // ListView와 어댑터 설정
        ListView listView = view.findViewById(R.id.notificationListView);

        // 예시 데이터를 사용하여 어댑터 설정
        alertItems = new ArrayList<>();
        alertItems.add(new Alertitem("ooo", Alertitem.TYPE_ONE));
        alertItems.add(new Alertitem("모임신청이 승인되었습니다.", Alertitem.TYPE_TWO));
        alertItems.add(new Alertitem("현유리", Alertitem.TYPE_ONE));

        adapter = new AlertListAdapter(getContext(), alertItems);
        listView.setAdapter(adapter);



        return view;
    }


}