package com.example.teamplay_p.front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teamplay_p.R;

import java.util.ArrayList;

public class AlertListAdapter extends ArrayAdapter<Alertitem> {
    private Context mContext;
    private ArrayList<Alertitem> alertitems;

    public AlertListAdapter(Context context, ArrayList<Alertitem> items) {
        super(context, 0, items);
        mContext = context;
        alertitems = items;
    }

    @Override
    public int getItemViewType(int position) {
        return alertitems.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2; // 두 가지 유형의 항목
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int viewType = getItemViewType(position);
        View listItemView = convertView;

        if (listItemView == null) {
            if (viewType == Alertitem.TYPE_ONE) {
                listItemView = LayoutInflater.from(mContext).inflate(R.layout.alert_list_accept_reject, parent, false);
            } else {
                listItemView = LayoutInflater.from(mContext).inflate(R.layout.alert_list_result, parent, false);
            }
        }

        TextView username = listItemView.findViewById(R.id.alert_username);
        TextView contentTextView = listItemView.findViewById(R.id.contentTextView);

        Alertitem currentItem = alertitems.get(position);

        if (viewType == Alertitem.TYPE_ONE) {
            username.setText(currentItem.getUsername());


            Button acceptButton = listItemView.findViewById(R.id.btn_accept);
            Button denyButton = listItemView.findViewById(R.id.btn_deny);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 수락 버튼 클릭 시 동작 정의
                    removeItem(position);
                }
            });

            denyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 거절 버튼 클릭 시 동작 정의
                    removeItem(position);
                }
            });
        } else {

            contentTextView.setText(currentItem.getMessage());

            Button actionButton = listItemView.findViewById(R.id.actionButton);

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 확인 버튼 클릭 시 동작 정의
                    removeItem(position);
                }
            });
        }

        return listItemView;
    }

    private void removeItem(int position) {
        alertitems.remove(position); // 데이터 소스에서 아이템 제거
        notifyDataSetChanged(); // 어댑터 갱신
    }

}