package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText etFullName, etEmail, etPass, etPhone, etCity;
    private Button btnSubmit;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        etFullName = findViewById(R.id.et_fullname);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.pass);
        etPhone = findViewById(R.id.et_phone);
        etCity = findViewById(R.id.city);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String city = etCity.getText().toString().trim();

        // Check if any field is empty
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(pass) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        } else {
            // Register the user with Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Registration successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                saveAdditionalUserData(user.getUid(), fullName, email, phone, city);
                            }
                            Toast.makeText(this, "Registration is successfull. " , Toast.LENGTH_LONG).show();
                        } else {
                            // Handle failure
                            String errorMessage = (task.getException() != null) ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(this, "Registration failed: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void saveAdditionalUserData(String userId, String fullName, String email, String phone, String city) {
        // Create a map of user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("fullName", fullName);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("city", city);

        // Save user data in Firestore
        db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "User registered and data saved successfully!", Toast.LENGTH_SHORT).show();
                    clearForm();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void clearForm() {
        etFullName.setText("");
        etEmail.setText("");
        etPass.setText("");
        etPhone.setText("");
        etCity.setText("");
    }
}
