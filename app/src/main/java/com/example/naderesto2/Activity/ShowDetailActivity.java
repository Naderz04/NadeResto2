package com.example.naderesto2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private ImageView plusBtn, minusBtn, picFood,arrowback,cartnader;
    private Item object;

    private ManagementCart managementCart;
    private int numberOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);

        initView();
        getBundle();
    }

    private void getBundle() {
        object = (Item) getIntent().getSerializableExtra("object");

        if (object != null) {
            int drawableResourceId = this.getResources().getIdentifier(String.valueOf(object.getURL()), "drawable", this.getPackageName());
            Glide.with(this).load(drawableResourceId).into(picFood);
            titleTxt.setText(object.getItemName());
            feeTxt.setText("$" + object.getItemPrice());
            descriptionTxt.setText(object.getDescription());
            numberOrderTxt.setText(String.valueOf(numberOrder));
        }
    }

    private void initView() {
        addToCart = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderText);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.picfood);
arrowback=findViewById(R.id.arrowback);
cartnader=findViewById(R.id.cartnader);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder++;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder--;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object != null) {
                    object.setNumberInCart(numberOrder);
                    managementCart.insertFood(object);
                    Toast.makeText(ShowDetailActivity.this, "Item added to your cart", Toast.LENGTH_SHORT).show();
                    Log.d("AddToCart", "Item added: " + object.getItemName() + ", Quantity: " + numberOrder);
                } else {
                    Log.e("AddToCart", "Object is null");
                }
            }
        });
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cartnader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowDetailActivity.this,CartListActivity.class));

            }
        });
    }
}
