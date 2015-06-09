package com.boxers.mitra.whatcandogseat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boxers.mitra.whatcandogseat.foodlist.FoodItem;
import com.boxers.mitra.whatcandogseat.foodlist.TypeOfFood;

/**
 * Created by arnabmitra on 6/7/15.
 */
public class FoodListAdapter extends ArrayAdapter<FoodItem> {

    private final Activity context;
    private final FoodItem[] names;
    public FoodListAdapter(Activity context, int resource, FoodItem[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.names=objects;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.food_text);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.icon_food);
            rowView.setTag(viewHolder);
        }


        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        FoodItem s = names[position];
        holder.text.setText(s.getItemName());
        if (s.getTypeOfFood().equals(TypeOfFood.BAD)) {
            holder.image.setImageResource(R.drawable.ic_error_black_24dp);
        } else if(s.getTypeOfFood().equals(TypeOfFood.GOOD)) {
            holder.image.setImageResource(R.drawable.ic_thumb_up_black_24dp);
        }
        else if(s.getTypeOfFood().equals(TypeOfFood.NEUTRAL))
        {
            holder.image.setImageResource(R.drawable.ic_warning_black_24dp);
        }

        return rowView;
    }
}
