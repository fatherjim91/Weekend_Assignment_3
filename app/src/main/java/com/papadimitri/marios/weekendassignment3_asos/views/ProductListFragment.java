package com.papadimitri.marios.weekendassignment3_asos.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.papadimitri.marios.weekendassignment3_asos.ApplicationASOS;
import com.papadimitri.marios.weekendassignment3_asos.R;
import com.papadimitri.marios.weekendassignment3_asos.adapters.RecyclerProductAdapter;
import com.papadimitri.marios.weekendassignment3_asos.model.Products.ProductModel;
import com.papadimitri.marios.weekendassignment3_asos.services.ASOS_API;
import com.papadimitri.marios.weekendassignment3_asos.utilities.RxUtils;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fatherjim on 10/04/2016.
 */
public class ProductListFragment extends Fragment {

    @Inject
    ASOS_API api;

    @Inject
    SharedPreferences mSharedPreferences;

    @Inject
    OkHttpClient mOkHttpClient;

    @Bind(R.id.products_recycler_view) RecyclerView recycler_view;
    @Bind(R.id.tv_products_category_name)
    TextView category_name;
    private RecyclerProductAdapter product_adapter;

    private CompositeSubscription subscriptions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ApplicationASOS) getActivity().getApplication()).getComponentAPI().inject(this);

        subscriptions = new CompositeSubscription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_product_list,container,false);
        ButterKnife.bind(this, layout);

        getProductsByCategoryID(getArguments().getString("id"));
        category_name.setText(getArguments().getString("name"));

        return layout;
    }

    public void getProductsByCategoryID(String id) {
        final ProgressDialog progress_dialog = new ProgressDialog(getActivity());
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
        subscriptions.add(api.getProductsByCategoryId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<ProductModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "onError");
                    }

                    @Override
                    public void onNext(ProductModel prod_model) {
                        Log.i("Retrofit", "onNext");
                        if (prod_model.getItemCount() > 0 && recycler_view != null) {
                            product_adapter = new RecyclerProductAdapter(prod_model, R.layout.cardview_product_, getActivity());
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                            GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
                            grid.setOrientation(GridLayoutManager.VERTICAL);
                            recycler_view.setLayoutManager(grid);
                            recycler_view.setAdapter(product_adapter);
                            progress_dialog.dismiss();
                        }
                    }
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(subscriptions);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(subscriptions);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
