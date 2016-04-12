package com.papadimitri.marios.weekendassignment3_asos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.papadimitri.marios.weekendassignment3_asos.MainActivity;
import com.papadimitri.marios.weekendassignment3_asos.R;
import com.papadimitri.marios.weekendassignment3_asos.model.Categories.CategoryModel;
import com.papadimitri.marios.weekendassignment3_asos.utilities.ItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fatherjim on 10/04/2016.
 */
public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder> {

    private CategoryModel categories;
    private int cardview_layout;
    private Context context;

    public RecyclerCategoryAdapter(CategoryModel cats, int rl, Context c) {
        this.categories = cats;
        this.cardview_layout = rl;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(cardview_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.category_name.setText(categories.getListing().get(position).getName());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                // The array position starts from 0, as usual, but it makes more sense to users if the position starts at #1
                if (isLongClick || !isLongClick) {
                    MainActivity ma = (MainActivity) context;
                    ((MainActivity) context).switchFragment(MainActivity.CATEGORY_ID, categories.getListing().get(position).getCategoryId(), categories.getListing().get(position).getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categories == null)
            return 0;
        else
            return categories.getListing().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @Bind(R.id.btn_category_name)
        Button category_name;

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
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
