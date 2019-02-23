package com.example.jojo.fruit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FruitInfoActivity extends AppCompatActivity {

    public static final String EXTRA_FRUIT_NAME =
            "com.example.jojo.fruit.EXTRA_FRUIT_NAME";
    public static final String EXTRA_FRUIT_PRICE =
            "com.example.jojo.fruit.EXTRA_FRUIT_PRICE";
    public static final String EXTRA_FRUIT_WEIGHT =
            "com.example.jojo.fruit.EXTRA_FRUIT_WEIGHT";

    // Fruit attributes - price in pence and weight in grams
    private String mFruitName;
    private int mFruitPrice, mFruitWeight;

    // Fruit attributes - price in pounds and weight in kilograms
    private double mFruitDisplayPrice, mFruitDisplayWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_info);

        Intent intent = getIntent();
        mFruitName = intent.getStringExtra(EXTRA_FRUIT_NAME);
        mFruitPrice = intent.getIntExtra(EXTRA_FRUIT_PRICE, 0);
        mFruitWeight = intent.getIntExtra(EXTRA_FRUIT_WEIGHT, 0);

        mFruitDisplayPrice = mFruitPrice / 100.0;
        mFruitDisplayWeight = mFruitWeight / 1000.0;

        TextView tv = findViewById(R.id.fruit_text_info);
        tv.setText("Fruit name: " + mFruitName +
                "\nFruit Price: £" + mFruitDisplayPrice +
                "\nFruit Weight: " + mFruitDisplayWeight + " kg");
    }
}
