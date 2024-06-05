package com.example.teamplay_p.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.teamplay_p.ApiService;
import com.example.teamplay_p.R;
import com.example.teamplay_p.RetrofitClient;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.user.UpdateRequest;
import com.example.teamplay_p.dto.user.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoEditFragment extends Fragment {

    private ApiService apiService;
    private Retrofit retrofit;
    private EditText Edt_username, Edt_userId, Edt_password, Edt_passwordCheck, Edt_studentNumber, Edt_mainMajor;

    public static InfoEditFragment newInstance(String param1, String param2) {
        InfoEditFragment fragment = new InfoEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_info_edit, container, false);

        // spinner
        Spinner major_spinner = view.findViewById(R.id.major_spinner);
        Spinner double_spinner = view.findViewById(R.id.double_spinner);
        Spinner minor_spinner = view.findViewById(R.id.minor_spinner);
        Spinner related_spinner = view.findViewById(R.id.related_spinner);

        loadMajorList(major_spinner);
        loadMajorList(double_spinner);
        loadMajorList(minor_spinner);
        loadMajorList(related_spinner);

        // btn_infoEdit 버튼을 찾습니다.
        Button btnInfoEdit = view.findViewById(R.id.InfoEditBT);

        // btn_infoEdit 버튼에 클릭 리스너를 설정합니다.
        btnInfoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = major_spinner.getSelectedItemPosition();
                if (selectedPosition == 0){      // 본전공 미선택 시
                    Toast.makeText(getActivity(), "본전공을 선택하세요", Toast.LENGTH_SHORT).show();
                }
                else {                          // 본전공 선택한 경우
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
                    String authToken = sharedPreferences.getString("authToken", "");
                    apiService = RetrofitClient.getClient(authToken).create(ApiService.class);

                    Major selectedMainmajor = (Major) major_spinner.getSelectedItem(); // 스피너 선택 아이템 major 타입으로 변환해서 major 객체 selectedMainmajor에 받아옴
                    String mainMajor = selectedMainmajor.getMajorUuid().toString(); // getter로 UUID를 가져와서 mainMajor에 저장(String타입)
                    Major selectedSubmajor1 = (Major) double_spinner.getSelectedItem();
                    String subMajor1 = selectedSubmajor1.getMajorUuid().toString();
                    Major selectedSubmajor2 = (Major) minor_spinner.getSelectedItem();
                    String subMajor2 = selectedSubmajor2.getMajorUuid().toString();
                    //updateRequest 객체 생성
                    UpdateRequest updateRequest = new UpdateRequest(/*"1245", "2222", mainMajor, subMajor1, subMajor2*/);
                    updateRequest.mainMajor = mainMajor;
                    updateRequest.subMajor1 = subMajor1;
                    updateRequest.subMajor2 = subMajor2;

                    Call<UserResponse> call = apiService.updateUserInfo(RetrofitClient.Uuid, updateRequest);
                    // 여기 이렇게 보내는거 맞는지 확인해봐야함
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                UserResponse userResponse = response.body();
                                //userResponse.
                                Toast.makeText(getActivity(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // response.message(): 서버 응답이 실패했을 때, 서버에서 제공하는 메시지를 표시
                                Toast.makeText(getActivity(), "Edit Info failed."+ response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            // t.getMessage(): 네트워크 호출이 실패했을 때 발생한 예외의 메시지를 표시
                            Toast.makeText(getActivity(), "Edit error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } // 본전공 선택 유효성 검사









                // myPageFragment로 이동합니다.
                myPageFragment mypageFragment = new myPageFragment();

                // FragmentManager를 사용하여 Fragment를 추가합니다.
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) // 애니메이션 효과 추가 (선택사항)
                        .replace(R.id.frame_layout, mypageFragment)
                        .commit();
            }
        });

        return view;
    }

    private void loadMajorList(Spinner spinner){
        // Retrofit 클라이언트 생성
        apiService = RetrofitClient.getClient(null).create(ApiService.class);
        Call<List<Major>> call = apiService.getAllMajors();
        call.enqueue(new Callback<List<Major>>() {
            @Override
            public void onResponse(Call<List<Major>> call, Response<List<Major>> response) {
                if(response.isSuccessful()){
                    List<Major> majorList = response.body();
                    if(majorList!=null){
                        // 힌트 텍스트 추가
                        Major hint = new Major("전공 선택", null);
                        majorList.add(0,hint);

                        // 전공 목록을 받아와서 스피너에 연결하는 어댑터 생성
                        // ArrayAdapter는 Major 객체 리스트를 받아서 각 객체의 toString() 메소드를 호출하여 문자열로 변환하고 스피너에 표시
                        // 스피너는 문자열 형태로 화면에 표시!
                        ArrayAdapter<Major> adapter = new ArrayAdapter<Major>( getActivity(), android.R.layout.simple_spinner_dropdown_item, majorList){
                            /*
                            @Override
                            public boolean isEnabled(int position){
                                // 첫 번째 항목은 선택할 수 없도록 비활성화
                                return position != 0;
                            }
                            */
                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // 힌트 항목은 회색으로 표시
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };

                        // 스피너에 어댑터 설정
                        spinner.setAdapter(adapter);

                    }
                }
                else {
                    // 오류 처리
                }
            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                // 네트워크 오류 등의 처리
            }
        });
    }

}
