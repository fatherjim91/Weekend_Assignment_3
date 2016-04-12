package com.papadimitri.marios.weekendassignment3_asos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.papadimitri.marios.weekendassignment3_asos.MainActivity;
import com.papadimitri.marios.weekendassignment3_asos.R;
import com.papadimitri.marios.weekendassignment3_asos.model.Products.ProductModel;
import com.papadimitri.marios.weekendassignment3_asos.utilities.ItemClickListener;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fatherjim on 11/04/2016.
 */
public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {

    private ProductModel products;
    private int row_layout;
    private Context context;

    public RecyclerProductAdapter(ProductModel prods, int rl, Context c) {
        this.products = prods;
        this.row_layout = rl;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (products.getListings().get(position).getProductImageUrl().get(0) != null) {
            Picasso.with(context)
                    .load(products.getListings().get(position).getProductImageUrl().get(0))
                    .resize(300, 0)
                    .into(holder.image);
        }
        if (products.getListings().get(position).getTitle() != null) {
            holder.name.setText(products.getListings().get(position).getTitle());
        }
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                // The array position starts from 0, as usual, but it makes more sense to users if the position starts at #1
                if (isLongClick) {
                    Toast.makeText(context, "#" + (position + 1) + " - " + "FAVOURITES" + " (Long Click)", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity ma = (MainActivity) context;
                    ((MainActivity) context).switchFragment(MainActivity.PRODUCT_ID, products.getListings().get(position).getProductId().toString(), products.getListings().get(position).getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        else
            return products.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @Bind(R.id.iv_product_list_image) ImageView image;
        @Bind(R.id.tv_product_list_name) TextView name;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }
    }
}