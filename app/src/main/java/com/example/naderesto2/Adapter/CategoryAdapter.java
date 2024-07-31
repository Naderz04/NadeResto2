package com.example.naderesto2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.naderesto2.Activity.CategoryItemsActivity;
import com.example.naderesto2.Activity.IntroActivity;
import com.example.naderesto2.Activity.ReviewsActivity;
import com.example.naderesto2.Activity.ShowDetailActivity;
import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<Category> categoryDomains;
    private ArrayList<Category> items;


    public CategoryAdapter(ArrayList<Category> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewhoder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.categoryName.setText(categoryDomains.get(position).getCategoryName());
        String picUrl = "";
        switch ((position)) {
            case 0: {
                picUrl = "naderestobeef";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(), CategoryItemsActivity.class);
                        intent.putExtra("category_id",categoryDomains.get(position).getCategoryId());
                        intent.putExtra("categoryname",categoryDomains.get(position).getCategoryName());

                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case 1: {
                picUrl = "big_chicken";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(), CategoryItemsActivity.class);
                        intent.putExtra("category_id",categoryDomains.get(position).getCategoryId());
                        intent.putExtra("categoryname",categoryDomains.get(position).getCategoryName());
                        intent.putExtra("categoryphoto",categoryDomains.get(position).getCategoryPhotoResId());

                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case 2: {
                picUrl = "pepsi";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(), CategoryItemsActivity.class);
                        intent.putExtra("category_id",categoryDomains.get(position).getCategoryId());
                        intent.putExtra("categoryname",categoryDomains.get(position).getCategoryName());

                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case 3: {
                picUrl = "fries2";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(), CategoryItemsActivity.class);
                        intent.putExtra("category_id",categoryDomains.get(position).getCategoryId());
                        intent.putExtra("categoryname",categoryDomains.get(position).getCategoryName());

                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case 4: {
                picUrl = "king_chicken_salad";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(holder.itemView.getContext(), CategoryItemsActivity.class);
                        intent.putExtra("category_id",categoryDomains.get(position).getCategoryId());
                        intent.putExtra("categoryname",categoryDomains.get(position).getCategoryName());

                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);



    }
    public void updateList(ArrayList<Category> newList) {
        items = newList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
    public interface OnCategoryClickListener {
        void onCategoryClick(int categoryid);
    }
}
