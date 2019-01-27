package com.example.h260210.room_viewmodel_rx_main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.h260210.room_viewmodel_rx_main.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addHomeFragment(savedInstanceState);
    }

    private void addHomeFragment(Bundle savedInstanceState){
        if (null != findViewById(R.id.layout_container)){
            if (null != savedInstanceState){
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_container, new HomeFragment())
                    .commit();
        }
    }
}
