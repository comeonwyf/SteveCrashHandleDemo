package com.steve.stevecrashhandledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = null;
                test.length();
            }
        });
    }
}
