package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginButton,joinNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        joinNowButton = findViewById(R.id.joinNowButton);
        loginButton = findViewById(R.id.loginButton);
        // Set OnClickListeners for buttons and icons

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Login clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Join Now clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }
}


