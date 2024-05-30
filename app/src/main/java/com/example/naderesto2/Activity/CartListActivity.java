package com.example.naderesto2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.naderesto2.Helper.ManagementCart;
import com.example.naderesto2.R;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagementCart managementCart;
    TextView totalfeeTxt,taxTxt,deliveryTxt,TotalTxt,emptyTxt;
    private double tax;

    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        managementCart=new ManagementCart(this);

        /*initView();*/
    }

    /*private void initView(){


        recyclerView=findViewById(R.id.re)
    }*/
}