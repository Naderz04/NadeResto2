package com.example.naderesto2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Helper.ManagementCart;
import com.example.naderesto2.Interface.ChangeNumberItemsListener;
import com.example.naderesto2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<Item> itemsDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<Item> itemsDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.itemsDomains = itemsDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;


    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(itemsDomains.get(position).getItemName());
//        holder.feeEachItem.setText(String.valueOf(itemsDomains.get(position).getItemPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round(itemsDomains.get(position).getNumberInCart() * itemsDomains.get(position).getItemPrice() * 100) / 100));
        holder.num.setText(String.valueOf(itemsDomains.get(position).getNumberInCart()));
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(String.valueOf(itemsDomains.get(position).getURL()), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberOfFood(itemsDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });

            }


        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberOfFood(itemsDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });


    }
    @Override
    public int getItemCount() {
        return itemsDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView){

            super(itemView);
            title=itemView.findViewById(R.id.TitleTxt);
//            feeEachItem=itemView.findViewById(R.id.itemfee);
            pic=itemView.findViewById(R.id.picc);
            totalEachItem=itemView.findViewById(R.id.ttlfee);
            num=itemView.findViewById(R.id.itemnumber);
            plusItem=itemView.findViewById(R.id.pluscart);
            minusItem=itemView.findViewById(R.id.minus_cart);




        }
    }
}