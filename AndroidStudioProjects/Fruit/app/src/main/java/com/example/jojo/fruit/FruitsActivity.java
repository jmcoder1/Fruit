package com.example.jojo.fruit;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.agrawalsuneet.loaderspack.loaders.CurvesLoader;

import java.util.ArrayList;
import java.util.List;

public class FruitsActivity extends AppCompatActivity implements LoaderCallbacks<List<Fruit>> {

    private static final String LOG_TAG = FruitsActivity.class.getName();

    // URL Fruit data
    private static final String DUMMY_REQUEST =
            "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json";

    private FruitAdapter mFruitAdapterAdapter;

    private RelativeLayout noConnectionView;
    private CurvesLoader mProgressLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        initConnectButton();
        initFruitListView();

        mProgressLoader = findViewById(R.id.loading_spinner);
        noConnectionView = findViewById(R.id.no_connection_view);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        } else {
            // Otherwise, display error and hide the loading indicator
            mProgressLoader.setVisibility(View.GONE);
            noConnectionView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method initialises the 'Connect' button that connects to the wifi.
     */
    private void initConnectButton() {
        final WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        Button connectButton = findViewById(R.id.connect_to_wifi_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager.setWifiEnabled(true);
                recreate();
            }
        });
    }

    private void initFruitListView() {
        ListView fruitListView = (ListView) findViewById(R.id.fruit_list);

        mFruitAdapterAdapter = new FruitAdapter(this, new ArrayList<Fruit>());
        fruitListView.setAdapter(mFruitAdapterAdapter);
        fruitListView.setEmptyView(noConnectionView);

        fruitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Fruit currentFruit = mFruitAdapterAdapter.getItem(position);
                Intent intent = new Intent(FruitsActivity.this, FruitInfoActivity.class);
                intent.putExtra(FruitInfoActivity.EXTRA_FRUIT_NAME, currentFruit.getName());
                intent.putExtra(FruitInfoActivity.EXTRA_FRUIT_PRICE, currentFruit.getPrice());
                intent.putExtra(FruitInfoActivity.EXTRA_FRUIT_WEIGHT, currentFruit.getWeight());

                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<Fruit>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "called: onCreateLoader");
        return new FruitLoader(this, DUMMY_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<Fruit>> loader, List<Fruit> fruits) {
        Log.e(LOG_TAG, "called: onLoadFinished");
        // Hide loading indicator because the data has been loaded
        mProgressLoader.setVisibility(View.GONE);
        mFruitAdapterAdapter.clear();

        // If there is a valid list of {@link Fruit}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (fruits != null && !fruits.isEmpty()) {
            mFruitAdapterAdapter.addAll(fruits);
            noConnectionView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Fruit>> loader) {
        Log.e(LOG_TAG, "called: onLoaderReset");
        mFruitAdapterAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fruits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reconnect:
                recreate();
                Toast.makeText(this, "Reloading data", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_settings:
                Log.v(LOG_TAG, "onOptionsItemSelected:: opened Settings activity");
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
