package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Room2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room2);

        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Room2.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

    }
}