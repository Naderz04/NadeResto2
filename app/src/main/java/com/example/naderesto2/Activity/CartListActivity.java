package com.example.naderesto2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naderesto2.Adapter.CartListAdapter;
import com.example.naderesto2.Helper.ManagementCart;
import com.example.naderesto2.Helper.PhotoDataSource;
import com.example.naderesto2.Helper.ReviewDataSource;
import com.example.naderesto2.Interface.ChangeNumberItemsListener;
import com.example.naderesto2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    ImageView BackBtn;
    private ManagementCart managementCart;
    PhotoDataSource photoDataSource =new PhotoDataSource(this);
    ReviewDataSource reviewDataSource=new ReviewDataSource(this);
    TextView totalfeeTxt, taxTxt, deliveryTxt, TotalTxt, emptyTxt;
    private double tax;
TextView checkout;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        managementCart = new ManagementCart(this);

        initView();
        initList();

        CalculateCart();
        ButtomNavigation();
checkout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(CartListActivity.this, MainActivity.class));
        Toast.makeText(CartListActivity.this, "Your order has been placed successfully!\"\n" +
                "\" Thank you for your purchase ", Toast.LENGTH_SHORT).show();
            photoDataSource.open();
            photoDataSource.deleteContact(photoDataSource.getLastphoto());
        photoDataSource.close();
        reviewDataSource.open();
        reviewDataSource.deleteReview(reviewDataSource.getLastreview2());
        reviewDataSource.close();;
    }
});
    }


    private void initView() {


        totalfeeTxt = findViewById(R.id.totalfeetext);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        TotalTxt = findViewById(R.id.TotalTxt);
        taxTxt = findViewById(R.id.taxTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.recyclerView3);
        BackBtn = findViewById(R.id.backBtn);
checkout=findViewById(R.id.checkoutText);
    }


    private void ButtomNavigation(){

        ImageView floatingActionButton=findViewById(R.id.CartNavBtn);
        ImageView homeBtn=findViewById(R.id.homeBtnNav);
        ImageView reviewBtn=findViewById(R.id.supportimageView7);
        ImageView navperson=findViewById(R.id.personnav);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, ReviewsActivity.class));

            }
        });
        navperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, ProfileActivity.class));

            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);

        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

        }

    }

    private void CalculateCart() {

        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100 / 100);
        double total = Math.round((managementCart.getTotalFee() + tax + delivery)) * 100 / 100;
        double itemTotal = Math.round((managementCart.getTotalFee()) * 100 / 100);


        totalfeeTxt.setText("$" + itemTotal);
        taxTxt.setText("$" + tax);
        deliveryTxt.setText("$" + delivery);
        TotalTxt.setText("$" + total);
    }

}