package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class bookingdelete extends AppCompatActivity {

    private RecyclerView bookingsRecyclerView;
    private BookingAdapter bookingAdapter;
    private DatabaseReference databaseReference;
    private ArrayList<HashMap<String, String>> bookingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdelete);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Payment");

        // Initialize RecyclerView
        bookingsRecyclerView = findViewById(R.id.bookingsRecyclerView);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch bookings from Firebase
        fetchBookings();
    }

    private void fetchBookings() {
        bookingsList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingsList.clear();
                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    String bookingId = bookingSnapshot.getKey();
                    HashMap<String, String> bookingDetails = (HashMap<String, String>) bookingSnapshot.getValue();
                    bookingDetails.put("bookingId", bookingId);
                    bookingsList.add(bookingDetails);
                }
                bookingAdapter = new BookingAdapter(bookingsList);
                bookingsRecyclerView.setAdapter(bookingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(bookingdelete.this, "Failed to fetch bookings!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // RecyclerView Adapter for displaying bookings
    private class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

        private ArrayList<HashMap<String, String>> bookings;

        public BookingAdapter(ArrayList<HashMap<String, String>> bookings) {
            this.bookings = bookings;
        }

        @NonNull
        @Override
        public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
            return new BookingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
            HashMap<String, String> booking = bookings.get(position);
            String bookingId = booking.get("bookingId");
            String customerName = booking.get("name");
            String checkInDate = booking.get("checkInDate");
            String checkOutDate = booking.get("checkOutDate");
            String amount = booking.get("amount");

            // Set booking details to views
            holder.customerNameTextView.setText("Name: " + customerName);
            holder.checkInDateTextView.setText("Check-in: " + checkInDate);
            holder.checkOutDateTextView.setText("Check-out: " + checkOutDate);
            holder.amountTextView.setText("Amount: $" + amount);

            // Delete button click listener
            holder.deleteButton.setOnClickListener(v -> {
                databaseReference.child(bookingId).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(bookingdelete.this, "Booking deleted successfully!", Toast.LENGTH_SHORT).show();
                        bookings.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(bookingdelete.this, "Failed to delete booking!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

        @Override
        public int getItemCount() {
            return bookings.size();
        }

        // ViewHolder class for RecyclerView
        public class BookingViewHolder extends RecyclerView.ViewHolder {

            TextView customerNameTextView, checkInDateTextView, checkOutDateTextView, amountTextView;
            Button deleteButton;

            public BookingViewHolder(@NonNull View itemView) {
                super(itemView);
                customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
                checkInDateTextView = itemView.findViewById(R.id.checkInDateTextView);
                checkOutDateTextView = itemView.findViewById(R.id.checkOutDateTextView);
                amountTextView = itemView.findViewById(R.id.amountTextView);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}
