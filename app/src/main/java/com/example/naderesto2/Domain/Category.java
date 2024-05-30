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

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryPhotoResId() {
        return categoryPhotoResId;
    }
}
