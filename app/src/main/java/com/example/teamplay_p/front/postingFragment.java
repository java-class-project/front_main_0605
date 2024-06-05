package com.example.teamplay_p.front;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamplay_p.R;

import java.util.ArrayList;

public class postingFragment extends AppCompatActivity {

    private ArrayList<ProfileList> profileArrayList;

    private RecyclerView recyclerView;

    private Button popupButton;
    private TextView selectedMenuItemTextView;

    private Button saveButton, postButton;

    private RadioGroup radioGroup1, radioGroup2, radioGroup3;

    private EditText editText1, editText2, editText3;

    private String selectedItemText;  // 과목명

    private int profile_img;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_fragment);

        popupButton = findViewById(R.id.popup_subject);
        selectedMenuItemTextView = findViewById(R.id.select_subject);

        saveButton = findViewById(R.id.btn_save);
        postButton = findViewById(R.id.btn_intent);

        radioGroup1 = findViewById(R.id.radioteam);
        radioGroup2 = findViewById(R.id.radioclass);
        radioGroup3 = findViewById(R.id.radioyesno);

        editText1 = findViewById(R.id.name);
        editText2 = findViewById(R.id.text_mbti);
        editText3 = findViewById(R.id.selfpr);

        // 팝업 버튼 클릭 이벤트 핸들러 설정
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        //저장버튼을 누르면 입력받은 데이터를 처리
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 저장 버튼 클릭 이벤트 처리
                String value1 = editText1.getText().toString();
                String value2 = editText2.getText().toString();
                String value3 = editText3.getText().toString();
                int selectedRadioButtonId1 = radioGroup1.getCheckedRadioButtonId();
                RadioButton selectedRadioButton1 = findViewById(selectedRadioButtonId1);
                String selectedOption1 = selectedRadioButton1 != null ? selectedRadioButton1.getText().toString() : "";

                int selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                RadioButton selectedRadioButton2 = findViewById(selectedRadioButtonId2);
                String selectedOption2 = selectedRadioButton2 != null ? selectedRadioButton2.getText().toString() : "";

                int selectedRadioButtonId3 = radioGroup3.getCheckedRadioButtonId();
                RadioButton selectedRadioButton3 = findViewById(selectedRadioButtonId3);
                String selectedOption3 = selectedRadioButton3 != null ? selectedRadioButton3.getText().toString() : "";
                //데이터 저장됬는지 확인용으로 띄어놓음
                String message = "Value 1: " + value1 + "\n" +
                        "Value 2: " + value2 + "\n" +
                        "Value 3: " + value3;

                Toast.makeText(postingFragment.this, message, Toast.LENGTH_LONG).show();
            }
        });
        //포스트버튼을 누르면
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApp myApp = (MyApp) getApplicationContext();
                ArrayList<ArrayList<Integer>> userInfo = myApp.getUserInfo();

                // Add a new list of integers to the userinfo
                ArrayList<Integer> newUser = new ArrayList<>();
                newUser.add(0);
                newUser.add(0);
                userInfo.add(newUser);

                //번들에 담아서 홈프래그먼트로 전달하는 부분
                Bundle bundle = new Bundle();
                bundle.putString("key1", editText1.getText().toString());
                bundle.putString("key2", editText2.getText().toString());
                bundle.putString("key3", editText3.getText().toString());
                int selectedRadioButtonId1 = radioGroup1.getCheckedRadioButtonId();
                RadioButton selectedRadioButton1 = findViewById(selectedRadioButtonId1);
                String selectedOption1 = selectedRadioButton1 != null ? selectedRadioButton1.getText().toString() : "";
                bundle.putString("selectedOption1", selectedOption1);
                int selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                RadioButton selectedRadioButton2 = findViewById(selectedRadioButtonId2);
                String selectedOption2 = selectedRadioButton2 != null ? selectedRadioButton2.getText().toString() : "";
                bundle.putString("selectedOption2", selectedOption2);
                int selectedRadioButtonId3 = radioGroup3.getCheckedRadioButtonId();
                RadioButton selectedRadioButton3 = findViewById(selectedRadioButtonId3);
                String selectedOption3 = selectedRadioButton3 != null ? selectedRadioButton3.getText().toString() : "";
                bundle.putString("selectedOption3", selectedOption3);
                bundle.putString("selectedItemText", selectedItemText);

                homeFragment homeFragment = new homeFragment();
                homeFragment.setArguments(bundle);

                // 화면을 homeFragment로 전환
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void showPopupMenu(View v) {
        // 팝업 메뉴 객체 생성
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.subjectpopup); // 팝업 메뉴에 사용할 XML 리소스 인플레이트

        // 팝업 메뉴 아이템 클릭 이벤트 핸들러 설정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 선택된 항목의 텍스트 가져오기
                selectedItemText = item.getTitle().toString();
                // 선택된 항목의 텍스트를 TextView에 표시
                selectedMenuItemTextView.setText(selectedItemText);
                // 선택된 항목에 대한 동작 구현
                Toast.makeText(postingFragment.this, selectedItemText + " 선택됨", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // 팝업 메뉴 표시
        popupMenu.show();
    }

    public String getSelectedItemText() {
        return selectedItemText;
    }
}




