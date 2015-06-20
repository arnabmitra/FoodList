package com.boxers.mitra.whatcandogseat;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.boxers.mitra.whatcandogseat.foodlist.FoodContent;
import com.boxers.mitra.whatcandogseat.foodlist.FoodItem;

/**
 * Created by arnabmitra on 6/14/15.
 */
public class SearchResultsActivity extends FragmentActivity {

    private ListView mListView;

    public static final int SYNC_INTERVAL = 30000 * 180;


    @Override
    public void onCreate(Bundle savedInstanceState) {

   //     setContentView(R.layout.activity_food_list);
        handleIntent(getIntent());
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            showResults(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }



    private void showResults(String query) {
        // Query your data set and show results
        // ...
        // Get a reference to the ListView, and attach this adapter to it.

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        View rootView = getLayoutInflater().inflate(R.layout.fragment_main, view, false);
        mListView = (ListView) rootView.findViewById(R.id.listview_forecast);
       // setListAdapter(new FoodListAdapter(this, SYNC_INTERVAL, FoodContent.getITEMS().toArray(new FoodItem[0])));
      //  setContentView(R.layout.activity_food_list_search);
//        Bundle bundle=new Bundle();
//        bundle.putString("SEARCH_QUERY", query);
        Intent detailIntent = new Intent(this, FoodListActivity.class);
        detailIntent.putExtra(FoodListFragment.SEARCH_QUERY, query);
        startActivity(detailIntent);

    }


}
