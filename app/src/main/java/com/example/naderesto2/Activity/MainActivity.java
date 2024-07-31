package com.example.naderesto2.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.naderesto2.Adapter.ItemAdapter;
import com.example.naderesto2.Domain.Photo;
import com.example.naderesto2.Domain.Review;
import com.example.naderesto2.Helper.PhotoDataSource;
import com.example.naderesto2.Helper.ReviewDataSource;
import com.example.naderesto2.databinding.ActivityMainBinding;

import com.example.naderesto2.Adapter.CategoryAdapter;
import com.example.naderesto2.Adapter.PopularAdapter;
import com.example.naderesto2.Domain.Category;
import com.example.naderesto2.Domain.Item;
import com.example.naderesto2.Helper.CategoryDataSource;
import com.example.naderesto2.Helper.ItemDataSource;
import com.example.naderesto2.Helper.dbhelper;
import com.example.naderesto2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener{

    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList, recyclerViewItemList;
    private dbhelper dbHelper;
    private CategoryAdapter categoryAdapter;
    ItemDataSource cds;
    TextView costName;

    ConstraintLayout PopLayout;

    ImageView userPhoto;
    private CategoryDataSource categoryDataSource;
    private ItemDataSource itemDataSource;
    private ReviewDataSource reviewDataSource;
    private ArrayList<Category> categoryList = new ArrayList<>();
    Photo currentphoto;
    Review currentreview;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        dbHelper = new dbhelper(this);
        categoryDataSource = new CategoryDataSource(this);
        itemDataSource = new ItemDataSource(this);
        reviewDataSource = new ReviewDataSource(this);
        userPhoto = findViewById(R.id.userphoto);
        dbHelper.getWritableDatabase();
        Log.d(TAG, "Database initialized");

        PhotoDataSource ph = new PhotoDataSource(this);
        putDataInDatabase();
        putItemsInDatabase();
        putReviewsInDatabase();
        recyclerViewCategory();
        recyclerViewPopularList();
        ButtomNavigation();
        costName = findViewById(R.id.NametextView3);

        ph.open();
        currentphoto = new Photo();
        currentphoto = ph.getSpecificContact(ph.getLastphoto());
        ph.close();

        if (currentphoto.getContactPhoto() != null) {
            Glide.with(this)
                    .load(currentphoto.getContactPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(userPhoto);
        }

        reviewDataSource.open();
        currentreview = new Review();
        currentreview = reviewDataSource.getSpecificReview(reviewDataSource.getLastreview2());
        reviewDataSource.close();
        if (currentreview.getCostumerName() != null) {
            costName.setText(currentreview.getCostumerName());
        }

        EditText searchbar = findViewById(R.id.editTextTextt);
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCategories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    // Other methods in MainActivity...



    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        categoryList.add(new Category(1, "Meet On Meat", R.drawable.naderestobeef));
        categoryList.add(new Category(2, "Pick a Chick", R.drawable.big_chicken));
        categoryList.add(new Category(3, "Link a Drink", R.drawable.pepsi));
        categoryList.add(new Category(4, "Side Stride", R.drawable.fries2));
        categoryList.add(new Category(5, "Grins with Greens", R.drawable.king_chicken_salad));

        categoryAdapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(categoryAdapter);

    }

    private void ButtomNavigation() {
        ImageView floatingActionButton = findViewById(R.id.CartNavBtn);
        ImageView homeBtn = findViewById(R.id.homeBtnNav);
        ImageView reviewBtn = findViewById(R.id.supportimageView7);
        ImageView navperson = findViewById(R.id.personnav);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReviewsActivity.class));
            }
        });
        navperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
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
        categoryDataSource.insertCategory(new Category(1, "Meet On Meat", R.drawable.naderestobeef));
        categoryDataSource.insertCategory(new Category(2, "Pick a Chick", R.drawable.big_chicken));
        categoryDataSource.insertCategory(new Category(3, "Link a Drink", R.drawable.pepsi));
        categoryDataSource.insertCategory(new Category(4, "Side Stride", R.drawable.fries2));
        categoryDataSource.insertCategory(new Category(5, "Grins with Greens", R.drawable.king_chicken_salad));
    }


    public void putItemsInDatabase() {
        itemDataSource.open();

        Item Classic = new Item(
                1,
                "Classic",
                7.0,
                "Try our Classic Hamburger, a signature flame-grilled beef patty topped with a simple layer of crinkle cut pickles, yellow mustard and ketchup on a toasted sesame seed bun.",
                R.drawable.classic,
                1,
                0
        );

        Item WHOPPER = new Item(
                2,
                "WHOPPER",
                8.0,
                "Our WHOPPER Sandwich is a savory flame-grilled beef patty topped with juicy tomatoes, fresh lettuce, creamy mayonnaise, ketchup, crunchy pickles and sliced white onions on a soft sesame seed bun.",
                R.drawable.whopper,
                1,
                0
        );

        Item STEAKHOUSE = new Item(
                3,
                "STEAKHOUSE",
                9.0,
                "A warm and toasted sesame seed bun crowns a variety of foodstuff such as mayonnaise, crispy onions, lettuce, tomato, BBQ sauce, beef bacon, Cheddar Cheese, Whopper patty and a warm and toasted corn dusted bun at the bottom.",
                R.drawable.steakhouse,
                1,
                0
        );

        Item CHEESE_BURGER = new Item(
                4,
                "CHEESE BURGER",
                7.0,
                "You can’t go wrong with our Cheeseburger, a signature flame-grilled beef patty topped with a simple layer of melted American cheese, crinkle cut pickles, yellow mustard and ketchup on a toasted sesame seed bun.",
                R.drawable.cheeseburger,
                1,
                0
        );

        Item THE_BIG = new Item(
                5,
                "THE BIG",
                10.0,
                "A Big Dual Burger with a dozen layers starting from the top with a warm and toasted tower sesame seed crown bun, its special sauce, lettuce, onions, pickles, savory Flame-Grilled Burger patty, warm and toasted tower middle bun, a second savory Flame-Grilled Burger patty, American cheese, onions, lettuce, our special sauce, and a warm and toasted tower bun heel at the bottom.",
                R.drawable.bigg,
                1,
                0
        );

        Item NadeResto_SPECIAL_BEEF = new Item(
                6,
                "NadeResto SPECIAL BEEF",
                14.0,
                "The signature burger at NadeResto is a meticulously crafted masterpiece featuring double flame-grilled beef patties, premium melted cheese, crisp lettuce, juicy tomatoes, onion, and a secret house sauce, all sandwiched between a soft, freshly baked artisanal bun. Known as the NadeResto Special Beef, this burger is more than just a meal; it's a symphony of flavors that elevates your burger experience to new heights.",
                R.drawable.naderestobeef,
                1,
                0
        );

        Item Classic_Chicken = new Item(
                7,
                "Classic Chicken",
                7.0,
                "Our Chicken burger is a mighty tasty chicken patty on a toasted sesame bun, topped with fresh lettuce and creamy mayonnaise!",
                R.drawable.classic,
                2,
                0
        );

        Item CHICKEN_ROYALE = new Item(
                8,
                "CHICKEN ROYALE",
                8.0,
                "Our Chicken Royale is made with white meat chicken, lightly breaded and topped with a simple combination of shredded lettuce and creamy mayonnaise on a sesame seed bun.",
                R.drawable.chikenroyale,
                2,
                0
        );

        Item CHICKEN_STEAKHOUSE = new Item(
                9,
                "CHICKEN STEAKHOUSE",
                12.0,
                "An eleven-layer Chicken Steakhouse crowned by a soft bun, mayonnaise, crispy onions, lettuce, tomato, BBQ Sauce, delicious 4 Half's beef bacon, American Cheese, savory flame-grilled Chicken Whopper patty and a corn dusted bun heel.",
                R.drawable.inchicksteakhousev2,
                2,
                0
        );

        Item CHICKEN_ROYALE_STEAKHOUSE = new Item(
                10,
                "CHICKEN ROYALE STEAKHOUSE",
                10.0,
                "Our Chicken Royale Steakhouse is made with white meat chicken, lightly breaded and topped with a combination of creamy mayonnaise, crispy onions, crispy lettuce, tomato, BBQ sauce, beef bacon and cheese topped with our sesame seed bun!",
                R.drawable.inchickenroyalsteakhouse,
                2,
                0
        );

        Item BIG_CHICKEN = new Item(
                11,
                "BIG CHICKEN",
                10.0,
                "A tower-like burger comprised of 9 levels, topped by a warm and toasted tower sesame seed bun crown, mayonnaise, lettuce, American yellow cheese, savory breaded chicken burger patty, tower middle bun, a second savory crispy chicken burger patty, another layer of mayonnaise and on the Ground Floor a soft tower bun heel.",
                R.drawable.big_chicken,
                2,
                0
        );

        Item SPICY_CHICKEN_ROYALE = new Item(
                12,
                "SPICY CHICKEN ROYALE",
                9.0,
                "Our Chicken Royale is made with white meat chicken, lightly breaded and topped with a simple combination of shredded lettuce and creamy mayonnaise on a sesame seed bun.",
                R.drawable.inspicychikenroyale,
                2,
                0
        );

        Item Fries = new Item(
                13,
                "Fries",
                4.0,
                "Crusty on the outside, squishy on the inside. Strong enough to hold its form when held up straight.",
                R.drawable.fries2,
                4,
                0
        );

        Item CHICKEN_TENDERLOIN = new Item(
                14,
                "CHICKEN TENDERLOIN",
                6.0,
                "Made of tender chicken breast, lightly coated to make it crispy, our Chicken strips are the best treat you can pick!",
                R.drawable.chikentnderlon,
                4,
                0
        );

        Item WINGS_6_PC = new Item(
                15,
                "WINGS 6 PC.",
                7.0,
                "Flap into flavor paradise with our succulent wings, meticulously crafted and sauced to perfection for an unparalleled taste adventure.",
                R.drawable.wings6pc,
                4,
                0
        );

        Item GARDEN_SALAD = new Item(
                16,
                "GARDEN SALAD",
                6.0,
                "Our Garden Salad is a blend of lettuces garnished with juicy tomatoes, cucumber, sweet corn. Served with Italien Dressing or Ceaser Dressing.",
                R.drawable.king_garden_salad,
                5,
                0
        );

        Item CHICKEN_SALAD2 = new Item(
                17,
                "CHICKEN SALAD2",
                8.0,
                "Our Chicken Salad is a wonderful medley of lettuce, juicy tomatoes, cucumber, sweet corn. Served with Italien Dressing or Ceaser Dressing and topped with Chicken WHOPPER grilled.",
                R.drawable.king_chicken_salad,
                5,
                0
        );

        Item Pepsi = new Item(
                18,
                "Pepsi",
                2.0,
                "Perfect with any meal, enjoy the genuine taste of Pepsi",
                R.drawable.pepsi2,
                3,
                0
        );

        Item sevenUP = new Item(
                19,
                "7UP",
                2.0,
                "The refreshing clear drink. The perfect drink to get you feeling Up!\n",
                R.drawable.sevenup,
                3,
                0
        );

        Item Water = new Item(
                20,
                "Water",
                1.0,
                "AQUAFINA, the exclusive bottled water of NadeResto in LEBANON, fresh and pure, AQUAFINA is the perfect combination for happy bodies.",
                R.drawable.water,
                3,
                0
        );


        itemDataSource.insertItem(Classic);
        itemDataSource.insertItem(WHOPPER);
        itemDataSource.insertItem(STEAKHOUSE);
        itemDataSource.insertItem(CHEESE_BURGER);
        itemDataSource.insertItem(THE_BIG);
        itemDataSource.insertItem(NadeResto_SPECIAL_BEEF);
        itemDataSource.insertItem(Classic_Chicken);
        itemDataSource.insertItem(CHICKEN_ROYALE);
        itemDataSource.insertItem(CHICKEN_STEAKHOUSE);
        itemDataSource.insertItem(CHICKEN_ROYALE_STEAKHOUSE);
        itemDataSource.insertItem(BIG_CHICKEN);
        itemDataSource.insertItem(SPICY_CHICKEN_ROYALE);
        itemDataSource.insertItem(Fries);
        itemDataSource.insertItem(CHICKEN_TENDERLOIN);
        itemDataSource.insertItem(WINGS_6_PC);
        itemDataSource.insertItem(GARDEN_SALAD);
        itemDataSource.insertItem(CHICKEN_SALAD2);
        itemDataSource.insertItem(Pepsi);
        itemDataSource.insertItem(sevenUP);
        itemDataSource.insertItem(Water);
        itemDataSource.logAllItems();
        itemDataSource.close();
    }


    public void putReviewsInDatabase() {
        reviewDataSource.open();

        Review dr_imad = new Review(1, "Dr imad moukaddem","Although I have had many successes and achievements in my life, I consider eating here as my top achievement!");
        Review dr_fatima = new Review(2, "Dr fatima abdullah","If grading were an art, your project would be the Mona Lisa – leaving everyone in wonder and earning a grade equivalent to a masterpiece!,Trying to assign a grade to your project is like trying to fit a square peg into a round hole – it just doesn't conform to the ordinary grading system. Expect an A++ for originality!\"");
        Review Messi = new Review(3, "Leo Messi","I've scored many goals, but the real victory is savoring the incredible dishes at NadeResto. A taste champion on and off the field!\"");
        Review Kingrolodex = new Review(4, "King Rolodex","BI SHARAFAKKKKKKKKKK!!!,MSH ANA L KING,ENTAAAA L KINGGGGGGGGGGGGGGG\"");

        reviewDataSource.insertReview(dr_imad);
        reviewDataSource.insertReview(dr_fatima);
        reviewDataSource.insertReview(Messi);
        reviewDataSource.insertReview(Kingrolodex);


        reviewDataSource.close();
    }

    private void filterCategories(String text) {
        ArrayList<Category> filteredList = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getCategoryName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(category);
            }
        }
        categoryAdapter.updateList(filteredList);
        categoryAdapter = new CategoryAdapter(filteredList);
        recyclerViewCategoryList.setAdapter(categoryAdapter);

    }

    @Override
    public void onCategoryClick(int position) {
        Category category = categoryList.get(position);
        filterItemsByCategory(category.getCategoryId());
    }

    private void filterItemsByCategory(int categoryId) {
        ArrayList<Item> filteredItemList = itemDataSource.getItemsByCategory(categoryId);
        ItemAdapter itemAdapter = new ItemAdapter(filteredItemList);
        recyclerViewItemList = findViewById(R.id.recyclerView);
        recyclerViewItemList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewItemList.setAdapter(itemAdapter);
    }
}
