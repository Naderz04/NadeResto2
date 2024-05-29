package com.example.naderesto2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.naderesto2.Adapter.CategoryAdapter;
import com.example.naderesto2.Adapter.PopularAdapter;
import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Helper.CategoryDataSource;
import com.example.naderesto2.Helper.dbhelper;
import com.example.naderesto2.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    private dbhelper dbHelper;
    CategoryDataSource categoryDataSource;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "taht oncreate");
        setContentView(R.layout.activity_main);
        Log.d(TAG, "taht setcontent");

        dbHelper = new dbhelper(this);  // Initialize dbhelper
        Log.d(TAG, "taht dbhelper");
        categoryDataSource=new CategoryDataSource(this);

        dbHelper.getWritableDatabase();
        Log.d(TAG, "Database initialized");
        recyclerViewCategory();
        recyclerViewPopularList();
        putDataInDatabase();
    }


    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<Category> category2 = new ArrayList<>();
        category2.add(new Category(1, "Meet On Meat", R.drawable.naderestobeef));  // Use resource ID
        category2.add(new Category(2, "Pick a Chick", R.drawable.big_chicken));    // Use resource ID
        category2.add(new Category(3, "Link a Drink", R.drawable.pepsi));          // Use resource ID
        category2.add(new Category(4, "Side Stride", R.drawable.fries2));          // Use resource ID
        category2.add(new Category(5, "Grins with Greens", R.drawable.king_chicken_salad)); // Use resource ID

        adapter = new CategoryAdapter(category2);
        recyclerViewCategoryList.setAdapter(adapter);
    }



    private void recyclerViewPopularList(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<Item> PopularList =new ArrayList<>();
        PopularList.add(new Item("NadeResto SPECIAL BEEF",12.5,"The signature burger at NadeResto is a meticulously crafted masterpiece featuring double flame-grilled beef patties, premium melted cheese, crisp lettuce, juicy tomatoes, onion, and a secret house sauce, all sandwiched between a soft, freshly baked artisanal bun. Known as the NadeResto Special Beef, this burger is more than just a meal, it's a symphony of flavors that elevates your burger experience to new heights.","drawable/naderestobeef.png"));
        PopularList.add(new Item("Steakhouse",10.5,"A warm and toasted sesame seed bun crowns a variety of foodstuff such as mayonnaise, crispy onions, lettuce, tomato, BBQ sauce, beef bacon, Cheddar Cheese, Whopper patty and a warm and toasted corn dusted bun at the bottom.","drawable/steakhouse.png"));
        PopularList.add(new Item("Chicken Steakhouse",11.5,"An eleven-layer Chicken Steakhouse crowned by a soft bun, mayonnaise, crispy onions, lettuce, tomato, BBQ Sauce, delicious 4 Half's beef bacon, American Cheese, savory flame-grilled Chicken Whopper patty and a corn dusted bun heel.", "drawable/inchicksteakhousev2.png"));
        PopularList.add(new Item("Wings",6.5, "Flap into flavor paradise with our succulent wings, meticulously crafted and sauced to perfection for an unparalleled taste adventure.","drawable/wings6pc.png"));
        PopularList.add(new Item("Chicken Tenderloin",5.5, "Made of tender chicken breast, lightly coated to make it crispy, our Chicken strips are the best treat you can pick!", "drawable/chikentnderlon.png"));

        adapter2=new PopularAdapter(PopularList);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    public void putDataInDatabase(){
        categoryDataSource.open();

        // Use resource IDs instead of strings
        Category beef = new Category(0, "Meet On Meat", R.drawable.naderestobeef);
        Category chicken = new Category(0, "Pick a Chick", R.drawable.big_chicken);
        Category sides = new Category(0, "Side Stride", R.drawable.fries2);
        Category drinks = new Category(0, "Link a Drink", R.drawable.pepsi);
        Category salad = new Category(0, "Grins with Greens", R.drawable.king_chicken_salad);

        categoryDataSource.insertCategory(beef);
        categoryDataSource.insertCategory(chicken);
        categoryDataSource.insertCategory(sides);
        categoryDataSource.insertCategory(drinks);
        categoryDataSource.insertCategory(salad);

        // Log all categories to verify insertion
        categoryDataSource.logAllCategories();

        categoryDataSource.close();
    }

}