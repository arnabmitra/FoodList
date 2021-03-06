package com.boxers.mitra.whatcandogseat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.boxers.mitra.whatcandogseat.foodlist.FoodContent;
import com.boxers.mitra.whatcandogseat.foodlist.FoodItem;
import com.boxers.mitra.whatcandogseat.foodlist.TypeOfFood;
import com.opencsv.CSVReader;

import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list fragment representing a list of Foods. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link FoodDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class FoodListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private ListView mListView;

    private static final String TAG = "FoodListFragment";

    public static final String SEARCH_QUERY = "search";

    private  String mSearchQuery;


    public static final int SYNC_INTERVAL = 30000 * 180;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }


    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        // TODO: replace with a real list adapter.
//        setListAdapter(new ArrayAdapter<FoodItem>(
//                getActivity(),
//                R.layout.rowlayout,
//                android.R.id.text1,
//                FoodContent.ITEMS));

       // setListAdapter(new FoodListAdapter(getActivity(),SYNC_INTERVAL,FoodContent.ITEMS_ARRAY));
        Intent intent = getActivity().getIntent();
        String mSearchQuery= intent.getStringExtra(SEARCH_QUERY);

        if(FoodContent.getITEMS()==null) {
            new FoodContent(getObjectFromAssetsFile(getActivity()));
        }
        List<FoodItem> items=new ArrayList<>();
        if(mSearchQuery!=null && !mSearchQuery.isEmpty())
        {
            for(FoodItem item:FoodContent.getITEMS())
            {
                if(item.getItemName().toLowerCase().contains(mSearchQuery))
                {
                    items.add(item);
                }
            }
        }
        else
        {
            items=FoodContent.getITEMS();
        }
        setListAdapter(new FoodListAdapter(getActivity(),SYNC_INTERVAL,items.toArray(new FoodItem[0])));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        //FoodContent.ITEMS=getObjectFromAssetsFile(view.getContext());
        mCallbacks.onItemSelected(FoodContent.getITEMS().get(position).getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private static List<FoodItem> getObjectFromAssetsFile(Context context) {

        List<FoodItem> listOfFoodItems=new ArrayList<>();
        try {

            AssetManager assets = context.getAssets();
            InputStream csvStream = assets.open("Dogs - Sheet1.csv");
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);

            CSVReader csvReader = new CSVReader(csvStreamReader);

            String[] line;

            // throw away the header
            csvReader.readNext();

            int i=1;

            while ((line = csvReader.readNext()) != null) {

                i++;
                TypeOfFood typeOfFood=TypeOfFood.BAD;
                if(line[2].equalsIgnoreCase("GOOD"))
                {
                    typeOfFood=TypeOfFood.GOOD;
                }else if(line[2].equalsIgnoreCase("BAD"))
                {
                    typeOfFood=TypeOfFood.BAD;

                }
                else if(line[2].equalsIgnoreCase("NEUTRAL"))
                {
                    typeOfFood=TypeOfFood.NEUTRAL;

                }
                listOfFoodItems.add(new FoodItem(String.valueOf(i),typeOfFood,line[0],line[0]+":"+ StringUtils.capitalize(line[1])));
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG,"Expected file was not found",e);
        }catch (Exception e) {// Catch exception if any
            Log.e(TAG,"An exception occured while rendering fragment",e);

        }
        Collections.sort(listOfFoodItems);
        return listOfFoodItems;
    }
}
