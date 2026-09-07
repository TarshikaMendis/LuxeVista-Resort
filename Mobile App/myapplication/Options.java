package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // contact Button
        Button contact = findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  start a new activity
                Intent intent= new Intent(getApplicationContext(), Contact.class);
                startActivity(intent);
            }
        });

        // Book Room Button
        Button BookRoom = findViewById(R.id.BookRoom);
        BookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Room1.class);
                startActivity(intent);

            }
        });

        // service Button
        Button service = findViewById(R.id.service);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Service.class);
                startActivity(intent);

            }
        });

        // nearby places Button
        Button nearby = findViewById(R.id.nearby );
        nearby .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Nearby.class);
                startActivity(intent);

            }
        });

        // offers Button
        Button offers = findViewById(R.id.offers);
        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Offers.class);
                startActivity(intent);


            }
        });

        // Events Button
        Button events = findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Events.class);
                startActivity(intent);


            }
        });


        Button profiles = findViewById(R.id.profile);
        profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
            }
        });

    }
}
