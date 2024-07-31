package com.example.naderesto2.Domain;

public class Category {
    private int categoryId;
    private String categoryName;
    private int categoryPhotoResId; // Use resource ID

    public Category(int categoryId, String categoryName, int categoryPhotoResId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPhotoResId = categoryPhotoResId;
    }

    public Category() {
    }

    public int getCategoryId() {

        return categoryId;
    }

    public void setCategoryPhotoResId(int categoryPhotoResId) {
        this.categoryPhotoResId = categoryPhotoResId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryPhotoResId() {
        return categoryPhotoResId;
    }
}
