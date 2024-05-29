package com.example.naderesto2.Domain;

public class Item {
    private int itemId;
    private String itemName;
    private Double itemPrice;
    private String description;
    private String URL;
    private int categoryid;
    private int numberInCart;


    public Item(int itemId,String itemName,Double itemPrice,String description,String URL,int categoryidint ,int numberInCart) {
        this.itemId = itemId;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.description=description;
        this.URL=URL;
        this.categoryid=categoryidint;
        this.numberInCart=numberInCart;
    }

    public Item(String itemName,Double itemPrice,String description,String URL ) {
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

    public Item() {
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


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
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