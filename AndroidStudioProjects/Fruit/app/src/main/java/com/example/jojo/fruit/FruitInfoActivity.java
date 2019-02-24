package com.example.jojo.fruit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class FruitInfoActivity extends AppCompatActivity {

    public static final String EXTRA_FRUIT_NAME =
            "com.example.jojo.fruit.EXTRA_FRUIT_NAME";
    public static final String EXTRA_FRUIT_PRICE =
            "com.example.jojo.fruit.EXTRA_FRUIT_PRICE";
    public static final String EXTRA_FRUIT_WEIGHT =
            "com.example.jojo.fruit.EXTRA_FRUIT_WEIGHT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_info);

        Intent intent = getIntent();
        String fruitName = StringUtils.capitalize(intent.getStringExtra(EXTRA_FRUIT_NAME));
        int fruitPrice = intent.getIntExtra(EXTRA_FRUIT_PRICE, 0);
        int fruitWeight = intent.getIntExtra(EXTRA_FRUIT_WEIGHT, 0);

        double fruitDisplayPrice = fruitPrice / 100.0;
        double fruitDisplayWeight = fruitWeight / 1000.0;

        TextView tv = findViewById(R.id.fruit_text_info);
        tv.setText("Fruit name: " + fruitName +
                "\nFruit Price: £" + fruitDisplayPrice +
                "\nFruit Weight: " + fruitDisplayWeight + " kg");
    }
}
