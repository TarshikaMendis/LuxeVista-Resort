package com.example.myapplication;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Payment extends AppCompatActivity {

    private EditText cardNameEditText, cardNumberEditText, expiryDateEditText, securityCodeEditText;
    private EditText postalCodeEditText, amountEditText, nameEditText, checkInDateEditText, checkOutDateEditText;
    private Button payButton;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    // Notification channel constants
    private final String CHANNEL_ID = "payment_channel";
    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Payment");

        // Create a notification channel


        // Bind XML views to Java variables
        cardNameEditText = findViewById(R.id.cardName2);
        cardNumberEditText = findViewById(R.id.cardNumber);
        expiryDateEditText = findViewById(R.id.expiryDate);
        securityCodeEditText = findViewById(R.id.securityCode);
        postalCodeEditText = findViewById(R.id.postalCode);
        amountEditText = findViewById(R.id.cardName);
        nameEditText = findViewById(R.id.name);
        checkInDateEditText = findViewById(R.id.checkindate);
        checkOutDateEditText = findViewById(R.id.checkoutdate);
        payButton = findViewById(R.id.payButton);

        // Set click listener for Pay button
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "payment_channel", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifications for new payment added");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void processPayment() {
        // Get input data
        String cardName = cardNameEditText.getText().toString().trim();
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expiryDate = expiryDateEditText.getText().toString().trim();
        String securityCode = securityCodeEditText.getText().toString().trim();
        String postalCode = postalCodeEditText.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String checkInDate = checkInDateEditText.getText().toString().trim();
        String checkOutDate = checkOutDateEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(cardName) || TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(expiryDate)
                || TextUtils.isEmpty(securityCode) || TextUtils.isEmpty(postalCode) || TextUtils.isEmpty(amount)
                || TextUtils.isEmpty(name) || TextUtils.isEmpty(checkInDate) || TextUtils.isEmpty(checkOutDate)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cardNumber.length() != 16) {
            Toast.makeText(this, "Card number must be 16 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (securityCode.length() != 3) {
            Toast.makeText(this, "Security code must be 3 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique ID for this payment
        String paymentId = databaseReference.push().getKey();

        // Create a payment data map
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("paymentId", paymentId);
        paymentData.put("cardName", cardName);
        paymentData.put("cardNumber", cardNumber);
        paymentData.put("expiryDate", expiryDate);
        paymentData.put("securityCode", securityCode);
        paymentData.put("postalCode", postalCode);
        paymentData.put("amount", amount);
        paymentData.put("serviceOrRooms", name);
        paymentData.put("checkInDate", checkInDate);
        paymentData.put("checkOutDate", checkOutDate);

        // Save to Firebase Database
        databaseReference.child(paymentId).setValue(paymentData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Payment.this, "Payment successfully recorded!", Toast.LENGTH_SHORT).show();
                clearFields();
                sendNotification(name,amount);
            } else {
                Toast.makeText(Payment.this, "Failed to record payment. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        cardNameEditText.setText("");
        cardNumberEditText.setText("");
        expiryDateEditText.setText("");
        securityCodeEditText.setText("");
        postalCodeEditText.setText("");
        amountEditText.setText("");
        nameEditText.setText("");
        checkInDateEditText.setText("");
        checkOutDateEditText.setText("");
    }

    private void sendNotification(String serviceName, String  servicePrice) {
        // Check if permission is granted before sending the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, skip sending the notification and log it
                Toast.makeText(this, "Notification permission denied. No notification sent.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.bell) // Replace with your app icon
                    .setContentTitle("New Payment Added: " + serviceName)
                    .setContentText("Price: $" + servicePrice)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Show the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());

        } catch (SecurityException e) {
            // Handle the case where the permission is denied
            Toast.makeText(this, "Error sending notification: Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


}
