package com.boxers.mitra.whatcandogseat.foodlist;

import android.content.res.AssetManager;

import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FoodContent {


    /**
     * An array of sample (dummy) items.
     */
    private static List<FoodItem> ITEMS ;
    private static FoodItem[] ITEMS_ARRAY ;

    /**
     * A map of sample (dummy) items, by ID.
     */
    private static Map<String, FoodItem> ITEM_MAP = new HashMap<String, FoodItem>();

//    static {
//        // Add 3 sample items.
//        addItem(new FoodItem("1", TypeOfFood.GOOD,"Broccoli","Brocolli is great for dogs,it's rich in vitamins," +
//                "There has been a bit of confusion where broccoli is concerned.  Broccoli is very good for dogs, however," +
//                " if the daily intake exceeds more than 10% of the animals diet – problems can occur." +
//                "The toxic substance is isothiocyanate and can cause gastrointestinal irritation."));
//        addItem(new FoodItem("2", TypeOfFood.GOOD,"Apples","They contain potassium, which stimulates the" +
//                " immune system, and pepsin, which helps the stomach"));
//        addItem(new FoodItem("3", TypeOfFood.GOOD,"Brewers yeast","Health aid and can help remedy some skin conditions"));
//
//        ITEMS_ARRAY = ITEMS.toArray(new FoodItem[0]);
//    }


    public FoodContent(List<FoodItem> items) {
        ITEMS=items;
        if(items!=null)
        {
            for(FoodItem item:items)
            {
                addItem(item);

            }
        }
    }

    private static void addItem(FoodItem item) {
       // ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public static List<FoodItem> getITEMS() {
        return ITEMS;
    }

    public static FoodItem[] getItemsArray() {
        return ITEMS_ARRAY;
    }

    public static Map<String, FoodItem> getItemMap() {
        return ITEM_MAP;
    }



}
