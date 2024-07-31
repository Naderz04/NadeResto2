package com.example.naderesto2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.naderesto2.Domain.Review;
import com.example.naderesto2.Activity.ReviewsActivity;

import com.example.naderesto2.Helper.ReviewDataSource;
import com.example.naderesto2.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    ArrayList<Review> reviews;
    Context context;
    ReviewDataSource reviewDataSource;

    public ReviewAdapter(ArrayList<Review> reviews,Context context) {
        this.context=context;
        this.reviews = reviews;
        this.reviewDataSource=reviewDataSource;
    }
    

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_reviews, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        holder.Name.setText(reviews.get(position).getCostumerName());
        holder.reviewtext.setText(String.valueOf( reviews.get(position).getCostumerfeedback()));


        holder.deleteiconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.deleteiconn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteReview(holder.getAdapterPosition());                    }
                });

            };
        });



    }


    private void deleteReview(int position) {
        ReviewDataSource ds = new ReviewDataSource(context);
        try {
            ds.open();
            int ReviewId = reviews.get(position).getReview_Id();
            boolean didDelete = ds.deleteReview(ReviewId);
            ds.close();
            if (didDelete) {
                reviews.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Delete Failed", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name,reviewtext;
        ImageView deleteiconn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.userName);
            reviewtext = itemView.findViewById(R.id.userReview);
            deleteiconn = itemView.findViewById(R.id.delete_review);

        }

    }


}
