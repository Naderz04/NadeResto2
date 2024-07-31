package com.example.naderesto2.Domain;
    public class Review {
        private int review_Id;
        private String costumerName;
        private String costumerfeedback;

        public Review() {
        }

        public Review(int review_Id, String costumerName, String costumerfeedback) {
            this.review_Id = review_Id;
            this.costumerName = costumerName;
            this.costumerfeedback = costumerfeedback;
        }

        public Review(String costumerName, String costumerfeedback) {
            this.review_Id = 0; // Initialize with 0 instead of -1
            this.costumerName = costumerName;
            this.costumerfeedback = costumerfeedback;
        }

        // Getters and setters...


    public String getCostumerfeedback() {
        return costumerfeedback;
    }

    public void setCostumerfeedback(String costumerfeedback) {
        this.costumerfeedback = costumerfeedback;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public int getReview_Id() {
        return review_Id;
    }

    public void setReview_Id(int review_Id) {
        this.review_Id = review_Id;
    }
}
