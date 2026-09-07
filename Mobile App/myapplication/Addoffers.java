package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addoffers extends AppCompatActivity {

    private EditText etOfferTitle, etOfferDescription, etOfferDiscount, etOfferValidity;
    private Button btnSubmitOffer;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addoffers);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Offers");

        // Initialize Views
        etOfferTitle = findViewById(R.id.etOfferTitle);
        etOfferDescription = findViewById(R.id.etOfferDescription);
        etOfferDiscount = findViewById(R.id.etOfferDiscount);
        etOfferValidity = findViewById(R.id.etOfferValidity);
        btnSubmitOffer = findViewById(R.id.btnSubmitOffer);

        // Submit Offer Button Click Listener
        btnSubmitOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOffer();
            }
        });
    }

    private void addNewOffer() {
        // Get values from input fields
        String title = etOfferTitle.getText().toString().trim();
        String description = etOfferDescription.getText().toString().trim();
        String discount = etOfferDiscount.getText().toString().trim();
        String validity = etOfferValidity.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(title)) {
            etOfferTitle.setError("Title is required!");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            etOfferDescription.setError("Description is required!");
            return;
        }
        if (TextUtils.isEmpty(discount)) {
            etOfferDiscount.setError("Discount is required!");
            return;
        }
        if (TextUtils.isEmpty(validity)) {
            etOfferValidity.setError("Validity date is required!");
            return;
        }

        // Create a unique ID for the offer
        String offerId = databaseReference.push().getKey();

        // Create a HashMap to store offer details
        HashMap<String, String> offerMap = new HashMap<>();
        offerMap.put("id", offerId);
        offerMap.put("title", title);
        offerMap.put("description", description);
        offerMap.put("discount", discount);
        offerMap.put("validity", validity);

        // Save the offer to Firebase Database
        databaseReference.child(offerId).setValue(offerMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Addoffers.this, "Offer added successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(Addoffers.this, "Failed to add offer. Try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        etOfferTitle.setText("");
        etOfferDescription.setText("");
        etOfferDiscount.setText("");
        etOfferValidity.setText("");
    }
}
