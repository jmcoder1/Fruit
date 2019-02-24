package com.example.jojo.fruit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        String fruitNameFirstLetter = fruitName.substring(0, 1);

        int fruitPrice = intent.getIntExtra(EXTRA_FRUIT_PRICE, 0);
        int fruitWeight = intent.getIntExtra(EXTRA_FRUIT_WEIGHT, 0);

        double fruitDisplayPrice = fruitPrice / 100.0;
        double fruitDisplayWeight = fruitWeight / 1000.0;

        TextView fruit_image = findViewById(R.id.fruit_image);
        TextView fruit_name_tv = findViewById(R.id.fruit_name);
        TextView fruit_price_tv = findViewById(R.id.fruit_price);
        TextView fruit_weight_tv = findViewById(R.id.fruit_weight);

        fruit_image.setText(fruitNameFirstLetter);
        fruit_name_tv.setText(fruitName);
        fruit_price_tv.setText(String.format(getString(R.string.fruit_price), fruitDisplayPrice));
        fruit_weight_tv.setText(String.format(getString(R.string.fruit_weight), fruitDisplayWeight));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fruit_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_save:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
