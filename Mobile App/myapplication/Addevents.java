package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addevents extends AppCompatActivity {

    private EditText etEventName, etEventDate, etEventTime, etEventLocation, etEventDescription;
    private Button btnSubmitEvent;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevents);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");

        // Initialize Views
        etEventName = findViewById(R.id.etEventName);
        etEventDate = findViewById(R.id.etEventDate);
        etEventTime = findViewById(R.id.etEventTime);
        etEventLocation = findViewById(R.id.etEventLocation);
        etEventDescription = findViewById(R.id.etEventDescription);
        btnSubmitEvent = findViewById(R.id.btnSubmitEvent);

        // Set Click Listener for Submit Button
        btnSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEvent();
            }
        });
    }

    private void addNewEvent() {
        // Get values from input fields
        String name = etEventName.getText().toString().trim();
        String date = etEventDate.getText().toString().trim();
        String time = etEventTime.getText().toString().trim();
        String location = etEventLocation.getText().toString().trim();
        String description = etEventDescription.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(name)) {
            etEventName.setError("Event name is required!");
            return;
        }
        if (TextUtils.isEmpty(date)) {
            etEventDate.setError("Event date is required!");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            etEventTime.setError("Event time is required!");
            return;
        }
        if (TextUtils.isEmpty(location)) {
            etEventLocation.setError("Event location is required!");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            etEventDescription.setError("Event description is required!");
            return;
        }

        // Generate a unique ID for the event
        String eventId = databaseReference.push().getKey();

        // Create a HashMap to store event details
        HashMap<String, String> eventMap = new HashMap<>();
        eventMap.put("id", eventId);
        eventMap.put("name", name);
        eventMap.put("date", date);
        eventMap.put("time", time);
        eventMap.put("location", location);
        eventMap.put("description", description);

        // Save the event to Firebase Database
        databaseReference.child(eventId).setValue(eventMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Addevents.this, "Event added successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(Addevents.this, "Failed to add event. Try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        etEventName.setText("");
        etEventDate.setText("");
        etEventTime.setText("");
        etEventLocation.setText("");
        etEventDescription.setText("");
    }
}
