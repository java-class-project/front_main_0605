package com.example.teamplay_p.front;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamplay_p.R;
import com.google.android.material.navigation.NavigationBarView;

public class mainHomeActivity extends AppCompatActivity {
    homeFragment HomeFragment;
    myPageFragment MyPageFragment;

    alertFragment AlertFragment;

    @Override
    protected void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.activity_main_home);

        HomeFragment = new homeFragment();
        MyPageFragment = new myPageFragment();
        AlertFragment = new alertFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, HomeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottomnavigationview);
        navigationBarView.setSelectedItemId(R.id.action_home);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_alert) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, AlertFragment).commit();
                    return true;
                } else if (itemId == R.id.action_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, HomeFragment).commit();
                    return true;
                } else if (itemId == R.id.action_myPage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, MyPageFragment).commit();
                    return true;
                }


                return false;
            }
        });


    }


}