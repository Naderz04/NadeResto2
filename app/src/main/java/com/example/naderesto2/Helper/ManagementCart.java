package com.example.naderesto2.Helper;

import android.content.Context;

import com.example.naderesto2.Domain.Item;

import java.util.ArrayList;

public class ManagementCart {

    private Context context;
    private TinyDB tinyDB;


    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(Item item) {

        ArrayList<Item> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getItemName().equals(item.getItemName())) {
                existAlready = true;
            }
            n = i;
            break;
        }
        if ((existAlready)) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else
            listFood.add(item);
    }

    private ArrayList<Item> getListCart() {
        return tinyDB.getListObject("CartList");
    }


}





