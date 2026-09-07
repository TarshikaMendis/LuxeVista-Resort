package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        // Button Click Listeners
        Button SpaButton = findViewById(R.id.spaButton);
        Button DiningButton = findViewById(R.id.diningButton);
        Button CabanaButton = findViewById(R.id.cabanaButton);
        Button activityButton = findViewById(R.id.activityButton);

        SpaButton.setOnClickListener(v -> {
            Toast.makeText(Service.this, "Spa Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Spa.class);
            startActivity(intent);
        });

        DiningButton.setOnClickListener(v -> {
            Toast.makeText(Service.this, "Dining Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Dining.class);
            startActivity(intent);
        });

        CabanaButton.setOnClickListener(v -> {
            Toast.makeText(Service.this, "Cabana Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Cabana.class);
            startActivity(intent);
        });

        activityButton.setOnClickListener(v -> {
            Toast.makeText(Service.this, "Activity Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Activities.class);
            startActivity(intent);
        });
    }
}


