package com.example.naderesto2.Helper;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Interface.ChangeNumberItemsListener;

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
        tinyDB.putListObject("CardList", listFood);
        Toast.makeText(context, "Added to your cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Item> getListCart() {

        return tinyDB.getListObject("CardList");
    }


    public void plusNumberOfFood(ArrayList<Item> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listFood);
        changeNumberItemsListener.changed();
    }


    public void minusNumberOfFood(ArrayList<Item> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listFood.get(position).getNumberInCart() == 1)
            listFood.remove(position);
        else
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        tinyDB.putListObject("CardList", listFood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {

        ArrayList<Item> listfood = getListCart();

        double fee = 0;
        for (int i = 0; i < listfood.size(); i++) {
            fee = fee + (listfood.get(i).getItemPrice() * listfood.get(i).getNumberInCart());
        }
        return fee;
    }


}