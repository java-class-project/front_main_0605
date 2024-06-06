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
 * Use the {@link alertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class alertFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private MyWebSocketClient webSocketClient;
    private AlertListAdapter adapter;
    private ArrayList<Alertitem> alertItems;

    public alertFragment() {
        // Required empty public constructor
    }

    public static alertFragment newInstance(String param1, String param2) {
        alertFragment fragment = new alertFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

        // 웹소켓 클라이언트 초기화
        webSocketClient = new MyWebSocketClient();
        webSocketClient.setOnMessageReceivedListener(new MyWebSocketClient.OnMessageReceivedListener() {
            @Override
            public void onMessageReceived(String message) {
                handleNotificationMessage(message);
            }
        });

        return view;
    }

    private void handleNotificationMessage(String message) {
        // NotificationMessage를 Alertitem으로 변환하여 리스트뷰에 추가
        NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);
        int type = notificationMessage.getType().equals("MEETING_APPLICATION") ? Alertitem.TYPE_ONE : Alertitem.TYPE_TWO;
        Alertitem alertItem = new Alertitem(notificationMessage.getMessage(), type);
        alertItems.add(alertItem);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged(); // 리스트뷰 갱신
            }
        });
    }
}