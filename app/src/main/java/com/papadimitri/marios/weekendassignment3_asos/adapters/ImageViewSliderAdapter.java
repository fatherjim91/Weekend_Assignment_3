package com.papadimitri.marios.weekendassignment3_asos.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.papadimitri.marios.weekendassignment3_asos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fatherjim on 11/04/2016.
 */
public class ImageViewSliderAdapter extends PagerAdapter {

    private ImageView image;
    private Context context;
    private LayoutInflater layout_inflater;
    private List<String> product_images;

    public ImageViewSliderAdapter(List<String> images, Context c) {
        this.context = c;
        this.product_images = images;
    }

    @Override
    public int getCount() {
        if (product_images == null)
            return 0;
        else
            return product_images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layout_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = layout_inflater.inflate(R.layout.product_details_pager, container,false);
        image = (ImageView) item_view.findViewById(R.id.product_images);
        Picasso.with(context)
                .load(product_images.get(position))
                .resize(300, 0)
                .into(image);

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
