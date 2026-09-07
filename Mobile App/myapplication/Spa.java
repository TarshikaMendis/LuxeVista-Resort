package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Spa extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa);

        Button bookNowButton4 = findViewById(R.id.bookNowButton4);

        bookNowButton4.setOnClickListener(v -> {
            Toast.makeText(Spa.this, "Spa Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Payment.class);
            startActivity(intent);
        });
    }
}
