package com.boxers.mitra.whatcandogseat.foodlist;

/**
 * Created by arnabmitra on 6/7/15.
 */
public class FoodItem implements Comparable<FoodItem>{

    private String id;

    private TypeOfFood typeOfFood;

    private String itemDescription;

    private String itemName;

    public FoodItem(String id, TypeOfFood typeOfFood, String itemName,String itemDescription ) {
        this.id = id;
        this.typeOfFood = typeOfFood;

        this.itemDescription = itemDescription;
        this.itemName = itemName;
    }


    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public TypeOfFood getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(TypeOfFood typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int compareTo(FoodItem other) {
        int last = this.itemName.toLowerCase().compareTo(other.itemName.toLowerCase());
        return last == 0 ? this.typeOfFood.compareTo(other.typeOfFood) : last;
    }
}
