package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ImageButton btnCall = findViewById(R.id.btnCall);
        ImageButton btnMessage = findViewById(R.id.btnMessage);
        ImageButton btnEmail = findViewById(R.id.btnEmail);

        btnCall.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+919876543210"));
            startActivity(callIntent);
        });

        btnMessage.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:+919876543210"));
            startActivity(smsIntent);
        });

        btnEmail.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:info@bytexvnc.co.in"));
            startActivity(emailIntent);
        });
    }
}
