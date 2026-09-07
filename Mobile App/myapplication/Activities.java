package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        Button bookNowButton = findViewById(R.id.bookNowButton);
        Button bookNowButton2 = findViewById(R.id.bookNowButton2);
        Button bookNowButton3 = findViewById(R.id.bookNowButton3);
        Button bookNowButton4 = findViewById(R.id.bookNowButton4);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activities.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        bookNowButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activities.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        bookNowButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activities.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        bookNowButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activities.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });
    }
}