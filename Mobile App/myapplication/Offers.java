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

public class Offers extends AppCompatActivity {

    private RecyclerView recyclerViewOffers;
    private OfferAdapter offerAdapter;
    private ArrayList<HashMap<String, String>> offerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        recyclerViewOffers = findViewById(R.id.recyclerViewOffers);
        recyclerViewOffers.setLayoutManager(new LinearLayoutManager(this));
        offerList = new ArrayList<>();

        offerAdapter = new OfferAdapter(offerList);
        recyclerViewOffers.setAdapter(offerAdapter);

        fetchOffersFromFirebase();
    }

    private void fetchOffersFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Offers");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerList.clear();
                for (DataSnapshot offerSnapshot : snapshot.getChildren()) {
                    HashMap<String, String> offerData = (HashMap<String, String>) offerSnapshot.getValue();
                    offerList.add(offerData);
                }
                offerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Offers.this, "Failed to load offers!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Adapter Class
    public static class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

        private final ArrayList<HashMap<String, String>> offerList;

        public OfferAdapter(ArrayList<HashMap<String, String>> offerList) {
            this.offerList = offerList;
        }

        @NonNull
        @Override
        public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
            return new OfferViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
            HashMap<String, String> offer = offerList.get(position);
            holder.tvOfferTitle.setText("Title: " + offer.get("title"));
            holder.tvOfferDescription.setText("Description: " + offer.get("description"));
            holder.tvOfferDiscount.setText("Discount: " + offer.get("discount") + "%");
            holder.tvOfferValidity.setText("Validity: " + offer.get("validity"));
        }

        @Override
        public int getItemCount() {
            return offerList.size();
        }

        public static class OfferViewHolder extends RecyclerView.ViewHolder {
            TextView tvOfferTitle, tvOfferDescription, tvOfferDiscount, tvOfferValidity;

            public OfferViewHolder(@NonNull View itemView) {
                super(itemView);
                tvOfferTitle = itemView.findViewById(R.id.tvOfferTitle);
                tvOfferDescription = itemView.findViewById(R.id.tvOfferDescription);
                tvOfferDiscount = itemView.findViewById(R.id.tvOfferDiscount);
                tvOfferValidity = itemView.findViewById(R.id.tvOfferValidity);
            }
        }
    }
}
