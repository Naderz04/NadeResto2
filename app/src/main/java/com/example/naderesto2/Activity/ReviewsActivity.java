package com.example.naderesto2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naderesto2.Adapter.CartListAdapter;
import com.example.naderesto2.Adapter.ReviewAdapter;
import com.example.naderesto2.Domain.Review;
import com.example.naderesto2.Helper.ReviewDataSource;
import com.example.naderesto2.Interface.ChangeNumberItemsListener;
import com.example.naderesto2.R;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    ArrayList<Review> costumersreview;

    private RecyclerView recyclerViewreviews;
    ImageView deleteButton, costumerphoto,add_review_button;
    TextView costumername, costumerdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        initView();
        initList();
        ButtomNavigation();
        add_review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(ReviewsActivity.this, ProfileActivity.class));

            }
        });
    }

    private void initView() {

        recyclerViewreviews = findViewById(R.id.recyclerView4);
        deleteButton=findViewById(R.id.delete_review);
        add_review_button=findViewById(R.id.addreview);

    }

    private void ButtomNavigation(){

        ImageView floatingActionButton=findViewById(R.id.CartNavBtn);
        ImageView homeBtn=findViewById(R.id.homeBtnNav);
        ImageView reviewBtn=findViewById(R.id.supportimageView7);
        ImageView navperson=findViewById(R.id.personnav);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewsActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewsActivity.this, MainActivity.class));
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewsActivity.this, ReviewsActivity.class));

            }
        });
        navperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewsActivity.this, ProfileActivity.class));

            }
        });

    }


    private void initList() {
        ReviewDataSource reviewDataSource = new ReviewDataSource(this);
        reviewDataSource.open();
        costumersreview = reviewDataSource.getAllContacts();
        reviewDataSource.close();
        if (costumersreview.size() > 0) {
            recyclerViewreviews.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ReviewAdapter(costumersreview, this); // Pass reviewDataSource
            recyclerViewreviews.setAdapter(adapter);
        }
    }
}