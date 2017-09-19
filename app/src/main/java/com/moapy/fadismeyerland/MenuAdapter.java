package com.moapy.fadismeyerland;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;
import java.util.Random;

class MenuAdapter extends ArrayAdapter<FoodItem> {

    private ImageLoader mImageLoader;

    MenuAdapter(Context context) {
        super(context, R.layout.grid_item);
        mImageLoader = CustomVolleyRequest.getInstance(this.getContext()).getImageLoader();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
            convertView.setBackgroundResource(R.drawable.round_gridview);

            GradientDrawable drawable = (GradientDrawable) convertView.getBackground();
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            drawable.setColor(color);

            holder.imageView = (ImageView) convertView.findViewById(R.id.picture);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name);
            holder.price_tv = (TextView) convertView.findViewById(R.id.price);
            holder.priceTagRL = (LinearLayout) convertView.findViewById(R.id.itemtag);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        final FoodItem imageRecord = getItem(position);
        int imageResource = this.getContext().getResources().getIdentifier((imageRecord != null ? imageRecord.getUrl() : null), null, this.getContext().getPackageName());
        holder.imageView.setImageResource(imageResource);
        /*
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProductDetail(position, imageRecord != null ? imageRecord.getUrl() : null);
            }
        });*/

        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "design.graffiti.comicsansms.ttf");
        holder.name_tv.setTypeface(type);
        holder.name_tv.setText(imageRecord != null ? imageRecord.getName() : null);
        holder.name_tv.setTypeface(null, Typeface.BOLD);

        holder.price_tv.setTypeface(type);
        holder.price_tv.setText(imageRecord != null ? imageRecord.getPrice() : null);
        holder.price_tv.setTypeface(null, Typeface.BOLD);
        holder.priceTagRL.setRotation(0);

        return convertView;
    }

    private class ViewHolder {
        TextView name_tv;
        TextView price_tv;
        ImageView imageView;
        LinearLayout priceTagRL;
    }

    private void openProductDetail(int position, String url) {
/*
        if(!Connectivity.isConnected(getContext())) {
            CommonUtil.buildDialog(getContext(), getContext().getResources().getString(R.string.no_network_title),
                    getContext().getResources().getString(R.string.no_network_msg)).show();
            return;
        }*/

        try {
            FoodItem imr = getItem(position);
            String productID = imr != null ? imr.getId() : "0";
            Intent intent = new Intent(getContext(), CheckoutActivity.class);
            Bundle extras= new Bundle();
            extras.putString("pid", productID);
            extras.putString("url", url);
            intent.putExtras(extras);
            getContext().startActivity(intent);

        } catch (Exception e) {
            Log.e("ProductMainAdapter", "ProductDetailActivity exception!", e);
        }
    }

    void swapImageRecords(List<FoodItem> objects) {
        clear();
        for(FoodItem object : objects) {
            add(object);
        }
        notifyDataSetChanged();
    }
}

