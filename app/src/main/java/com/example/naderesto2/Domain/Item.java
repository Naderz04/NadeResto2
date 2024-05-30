package com.example.naderesto2.Domain;

import java.io.Serializable;

public class Item implements Serializable {
    private int itemId;
    private String itemName;
    private Double itemPrice;
    private String description;
    private int URL;
    private int categoryid;
    private int numberInCart;


    public Item(int itemId,String itemName,Double itemPrice,String description,int URL,int categoryidint ,int numberInCart) {
        this.itemId = itemId;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.description=description;
        this.URL=URL;
        this.categoryid=categoryidint;
        this.numberInCart=numberInCart;
    }

    public Item(String itemName,Double itemPrice,String description,int URL ) {
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.description=description;
        this.URL=URL;
        this.numberInCart=numberInCart;
   }
//    public Item(int itemId,String itemName,Double itemPrice,String description,String URL,int categoryidint ) {
//        this.itemId = itemId;
//        this.itemName=itemName;
//        this.itemPrice=itemPrice;
//        this.description=description;
//        this.URL=URL;
//        this.categoryid=categoryidint;
//    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }



    public int getCategoryId() {
        return categoryid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }


    public int getURL() {
        return URL;
    }

    public void setURL(int URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


}
