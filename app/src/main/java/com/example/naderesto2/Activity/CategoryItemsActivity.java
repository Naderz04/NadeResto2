package com.example.naderesto2.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naderesto2.Adapter.ItemAdapter;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Helper.ItemDataSource;
import com.example.naderesto2.R;

import java.util.ArrayList;

public class CategoryItemsActivity extends AppCompatActivity {

    private static final String TAG = "CategoryItemsActivity";
    private RecyclerView.Adapter itemsAdapter;
    private RecyclerView itemsrecyclerViewList;
    private ArrayList<Item> items;
    ImageView backbtnn;
    int categoryId;
    TextView categoryName;
    private ItemDataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        // Initialize ItemDataSource with context
        ds = new ItemDataSource(this);
        ds.open();  // Open the database connection
        getIntentExtra();
        initList();

    }

    private void initList() {
        itemsrecyclerViewList = findViewById(R.id.recycleViewItems);
        itemsrecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        ds.open();
        int categoryId = getIntent().getIntExtra("category_id", -1);

        // Check if categoryId is valid
        if (categoryId != -1) {
            items = ds.getItemsByCategory(categoryId);
        } else {
            Log.e(TAG, "Invalid category ID");
            items = new ArrayList<>(); // Provide an empty list if categoryId is invalid
        }

        itemsAdapter = new ItemAdapter(items);
        itemsrecyclerViewList.setAdapter(itemsAdapter);


    }

    @Override
    protected void onDestroy() {
        ds.close();  // Close the database connection
        super.onDestroy();
    }

    private void getIntentExtra() {
        categoryName=findViewById(R.id.CategorytitleTxt);
        categoryId =getIntent().getIntExtra("category_id",0);
        categoryName.setText(getIntent().getStringExtra("categoryname"));
        backbtnn=findViewById(R.id.backBtn);
        backbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
