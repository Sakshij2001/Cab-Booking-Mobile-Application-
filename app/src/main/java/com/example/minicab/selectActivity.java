package com.example.minicab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectActivity extends AppCompatActivity {

    Button driver,rider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        driver=findViewById(R.id.btn_driver);
        rider=findViewById(R.id.btn_rider);

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(selectActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}