package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button loginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        etEmail = findViewById(R.id.Email);
        etPass = findViewById(R.id.pwd);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        // Check if email or password is empty
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user is Admin
        if (email.equals("admin@gmail.com") && password.equals("678")) {
            // Admin login successful
            Toast.makeText(Login.this, "Admin login successful", Toast.LENGTH_SHORT).show();
            navigateToAdminPage();
        } else {
            // Authenticate other users with Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // General user login successful
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            navigateToUserPage();
                        } else {
                            // Login failed
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Login failed";
                            Toast.makeText(Login.this, "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void navigateToAdminPage() {
        // Navigate to Admin-specific activity
        Intent intent = new Intent(Login.this, Admin.class); // Replace with your Admin page
        startActivity(intent);
        finish(); // Close login activity
    }

    private void navigateToUserPage() {
        // Navigate to general user options or home page
        Intent intent = new Intent(Login.this, Options.class); // Replace with your User home page
        startActivity(intent);
        finish(); // Close login activity
    }
}
