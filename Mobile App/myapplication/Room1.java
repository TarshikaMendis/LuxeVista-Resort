package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Room1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);

        Button btnnext = findViewById(R.id.btnnext);
        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Room1.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Room1.this, "see other room", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), Room2.class);
                startActivity(intent);
            }
        });
    }
}
