package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity {

    private CalendarView calendarView;
    private Button createReservationButton;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        createReservationButton = findViewById(R.id.createReservationButton);

        // CalendarView Listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        // Button click listener
        createReservationButton.setOnClickListener(v -> {

            if (selectedDate == null) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            } else {
                String message = "Reservation Details:\n" +
                        "Date: " + selectedDate ;
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
