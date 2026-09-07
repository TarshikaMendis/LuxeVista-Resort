package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Events extends AppCompatActivity {

    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;
    private ArrayList<HashMap<String, String>> eventList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        recyclerViewEvents.setAdapter(eventAdapter);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");

        // Fetch Events from Firebase
        fetchEventsFromFirebase();
    }

    private void fetchEventsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HashMap<String, String> eventMap = (HashMap<String, String>) dataSnapshot.getValue();
                    eventList.add(eventMap);
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Events.this, "Failed to load events: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // RecyclerView Adapter for displaying events
    private static class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
        private final ArrayList<HashMap<String, String>> eventList;

        public EventAdapter(ArrayList<HashMap<String, String>> eventList) {
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            HashMap<String, String> event = eventList.get(position);
            holder.tvTitle.setText(event.get("name"));
            holder.tvSubtitle.setText(String.format("Date: %s, Location: %s", event.get("date"), event.get("location")));
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        // ViewHolder class for RecyclerView
        public static class EventViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvSubtitle;

            public EventViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(android.R.id.text1);
                tvSubtitle = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
