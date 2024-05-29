package com.example.naderesto2.Domain;

public class Category {

    private int CategoryId;
    private String CategoryName;
    private String CategoryPhoto;


    public Category(int categoryId,String categoryName, String categoryPhoto) {
        this.CategoryId=categoryId;
        this.CategoryName = categoryName;
        this.CategoryPhoto = categoryPhoto;
    }


    public String getCategoryPhoto() {

        return CategoryPhoto;
    }

    public void setCategoryPhoto(String categoryPhoto) {
        CategoryPhoto = categoryPhoto;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {

        CategoryName = categoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {

        CategoryId = categoryId;
    }

}
