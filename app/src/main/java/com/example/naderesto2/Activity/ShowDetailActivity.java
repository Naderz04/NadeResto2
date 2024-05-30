package com.example.naderesto2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Helper.ManagementCart;
import com.example.naderesto2.R;

public class ShowDetailActivity extends AppCompatActivity {

    private TextView addToCart;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private Item object;
    ManagementCart managementCart;
int numberOrder=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart=new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        object = (Item) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(String.valueOf(object.getURL()), "drawable", this.getPackageName());
        Glide.with(this).load(drawableResourceId).into(picFood);
        titleTxt.setText(object.getItemName());
        feeTxt.setText("$" + object.getItemPrice());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

    }

    private void initView() {
        addToCart = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        numberOrderTxt = findViewById(R.id.numberOrderText);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn=findViewById(R.id.minusBtn);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        picFood=findViewById(R.id.picfood);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    numberOrder = numberOrder + 1;

                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
                Toast.makeText(ShowDetailActivity.this, "Item added to your cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}