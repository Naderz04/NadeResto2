package com.example.naderesto2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.naderesto2.Adapter.CategoryAdapter;
import com.example.naderesto2.Adapter.PopularAdapter;
import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Helper.CategoryDataSource;
import com.example.naderesto2.Helper.ItemDataSource;
import com.example.naderesto2.Helper.dbhelper;
import com.example.naderesto2.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private dbhelper dbHelper;
    private CategoryDataSource categoryDataSource;
    private ItemDataSource itemDataSource;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        dbHelper = new dbhelper(this);
        categoryDataSource = new CategoryDataSource(this);
        itemDataSource = new ItemDataSource(this);

        dbHelper.getWritableDatabase();
        Log.d(TAG, "Database initialized");

        putDataInDatabase();
        putItemsInDatabase();

        recyclerViewCategory();
        recyclerViewPopularList();
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Meet On Meat", R.drawable.naderestobeef));
        categoryList.add(new Category(2, "Pick a Chick", R.drawable.big_chicken));
        categoryList.add(new Category(3, "Link a Drink", R.drawable.pepsi));
        categoryList.add(new Category(4, "Side Stride", R.drawable.fries2));
        categoryList.add(new Category(5, "Grins with Greens", R.drawable.king_chicken_salad));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewPopularList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<Item> popularList = new ArrayList<>();
        popularList.add(new Item("NadeResto Special", 12.5, "The signature burger at NadeResto is a meticulously crafted masterpiece featuring double flame-grilled beef patties, premium melted cheese, crisp lettuce, juicy tomatoes, onion, and a secret house sauce, all sandwiched between a soft, freshly baked artisanal bun. Known as the NadeResto Special Beef, this burger is more than just a meal, it's a symphony of flavors that elevates your burger experience to new heights.", R.drawable.naderestobeef));
        popularList.add(new Item("Steakhouse", 10.5, "A warm and toasted sesame seed bun crowns a variety of foodstuff such as mayonnaise, crispy onions, lettuce, tomato, BBQ sauce, beef bacon, Cheddar Cheese, Whopper patty and a warm and toasted corn dusted bun at the bottom.", R.drawable.steakhouse));
        popularList.add(new Item("Chicken Steakhouse", 11.5, "An eleven-layer Chicken Steakhouse crowned by a soft bun, mayonnaise, crispy onions, lettuce, tomato, BBQ Sauce, delicious 4 Half's beef bacon, American Cheese, savory flame-grilled Chicken Whopper patty and a corn dusted bun heel.", R.drawable.inchickenroyalsteakhouse));
        popularList.add(new Item("Wings", 6.5, "Flap into flavor paradise with our succulent wings, meticulously crafted and sauced to perfection for an unparalleled taste adventure.", R.drawable.wings6pc));
        popularList.add(new Item("Chicken Tenderloin", 5.5, "Made of tender chicken breast, lightly coated to make it crispy, our Chicken strips are the best treat you can pick!", R.drawable.chikentnderlon));

        adapter2 = new PopularAdapter(popularList);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    public void putDataInDatabase() {
        categoryDataSource.open();

        Category beef = new Category(1, "Meet On Meat", R.drawable.naderestobeef);
        Category chicken = new Category(2, "Pick a Chick", R.drawable.big_chicken);
        Category sides = new Category(3, "Side Stride", R.drawable.fries2);
        Category drinks = new Category(4, "Link a Drink", R.drawable.pepsi);
        Category salad = new Category(5, "Grins with Greens", R.drawable.king_chicken_salad);

        categoryDataSource.insertCategory(beef);
        categoryDataSource.insertCategory(chicken);
        categoryDataSource.insertCategory(sides);
        categoryDataSource.insertCategory(drinks);
        categoryDataSource.insertCategory(salad);

        categoryDataSource.logAllCategories();
        categoryDataSource.close();
    }

    public void putItemsInDatabase() {
        itemDataSource.open();

        Item classic = new Item(
                1,
                "Classic",
                7.0,
                "Try our Classic Hamburger, a signature flame-grilled beef patty topped with a simple layer of crinkle cut pickles, yellow mustard and ketchup on a toasted sesame seed bun.",
                R.drawable.classic,
                1,
                0
        );
        Item nadeRestoSpecial = new Item(
                2,
                "NadeResto Special",
                12.5,
                "The signature burger at NadeResto is a meticulously crafted masterpiece featuring double flame-grilled beef patties, premium melted cheese, crisp lettuce, juicy tomatoes, onion, and a secret house sauce, all sandwiched between a soft, freshly baked artisanal bun. Known as the NadeResto Special Beef, this burger is more than just a meal, it's a symphony of flavors that elevates your burger experience to new heights.",
                R.drawable.naderestobeef,
                1,
                0
        );
        Item steakhouse = new Item(
                3,
                "Steakhouse",
                10.5,
                "A warm and toasted sesame seed bun crowns a variety of foodstuff such as mayonnaise, crispy onions, lettuce, tomato, BBQ sauce, beef bacon, Cheddar Cheese, Whopper patty and a warm and toasted corn dusted bun at the bottom.",
                R.drawable.steakhouse,
                1,
                0
        );
        Item chickenSteakhouse = new Item(
                4,
                "Chicken Steakhouse",
                11.5,
                "An eleven-layer Chicken Steakhouse crowned by a soft bun, mayonnaise, crispy onions, lettuce, tomato, BBQ Sauce, delicious 4 Half's beef bacon, American Cheese, savory flame-grilled Chicken Whopper patty and a corn dusted bun heel.",
                R.drawable.inchickenroyalsteakhouse,
                2,
                0
        );
        Item wings = new Item(
                5,
                "Wings",
                6.5,
                "Flap into flavor paradise with our succulent wings, meticulously crafted and sauced to perfection for an unparalleled taste adventure.",
                R.drawable.wings6pc,
                2,
                0
        );
        Item chickenTenderloin = new Item(
                6,
                "Chicken Tenderloin",
                5.5,
                "Made of tender chicken breast, lightly coated to make it crispy, our Chicken strips are the best treat you can pick!",
                R.drawable.chikentnderlon,
                2,
                0
        );

        itemDataSource.insertItem(classic);
        itemDataSource.insertItem(nadeRestoSpecial);
        itemDataSource.insertItem(steakhouse);
        itemDataSource.insertItem(chickenSteakhouse);
        itemDataSource.insertItem(wings);
        itemDataSource.insertItem(chickenTenderloin);

        itemDataSource.logAllItems();
        itemDataSource.close();
    }
}
