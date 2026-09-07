package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {


    private Button btnAddOffer, btnEventCategory, btnManage, btnoption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        btnEventCategory = findViewById(R.id.btnEventCategory);
        btnAddOffer = findViewById(R.id.btnAddOffer);
        btnManage = findViewById(R.id.btnManage);
        btnoption = findViewById(R.id. btnoption);


        btnEventCategory.setOnClickListener(v -> {

            Toast.makeText(Admin.this, "Button clicked  " , Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(getApplicationContext(), Addevents.class);
            startActivity(intent);
        });

        btnAddOffer.setOnClickListener(v -> {

            Toast.makeText(Admin.this, "Button clicked" , Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(getApplicationContext(), Addoffers.class);
            startActivity(intent);
        });

        btnManage.setOnClickListener(v -> {
            Toast.makeText(Admin.this, "Manage now ", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(getApplicationContext(), bookingdelete.class);
            startActivity(intent);
        });

        btnoption.setOnClickListener(v -> {
            Toast.makeText(Admin.this, "button cliked ", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(getApplicationContext(), Options.class);
            startActivity(intent);
        });
    }
}
