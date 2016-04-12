package com.papadimitri.marios.weekendassignment3_asos.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.papadimitri.marios.weekendassignment3_asos.ApplicationASOS;
import com.papadimitri.marios.weekendassignment3_asos.R;
import com.papadimitri.marios.weekendassignment3_asos.adapters.ImageViewSliderAdapter;
import com.papadimitri.marios.weekendassignment3_asos.model.SingleProduct.SpecifiedProduct;
import com.papadimitri.marios.weekendassignment3_asos.services.ASOS_API;
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
public class ProductDetailsFragment extends Fragment {

    @Inject
    ASOS_API api;

    @Inject
    SharedPreferences mSharedPreferences;

    @Inject
    OkHttpClient mOkHttpClient;

    @Bind(R.id.tv_product_brand) TextView product_brand;
    @Bind(R.id.product_image_pager) ViewPager image_pager;
    @Bind(R.id.tv_product_details) TextView product_details;
    @Bind(R.id.btn_add_basket) Button btnAddToBag;
    private ImageViewSliderAdapter image_adapter;
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
        View layout = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, layout);

        getProductByID(Integer.parseInt(getArguments().getString("id")));

        return layout;
    }

    public void getProductByID(int id) {
        final ProgressDialog progress_dialog = new ProgressDialog(getActivity());
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
        subscriptions.add(api.getProductById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<SpecifiedProduct>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "onError");
                    }

                    @Override
                    public void onNext(SpecifiedProduct specified_product) {
                        Log.i("Retrofit", "onCompleted");
                        product_brand.setText(specified_product.getBrand());
                        image_adapter = new ImageViewSliderAdapter(specified_product.getProductImageUrls(),getActivity());
                        image_pager.setAdapter(image_adapter);
                        String details = "Product Title: " + specified_product.getTitle() + "\n\nAvailable in stock: " + specified_product.getInStock()
                                + "\n\nIs in set: " + specified_product.getIsInSet() + "\n\nAdditional info:\n" + specified_product.getAdditionalInfo();
                        product_details.setText(details);
                        String button_text = "Add to bag (" + specified_product.getCurrentPrice() + ")";
                        btnAddToBag.setText(button_text);
                        progress_dialog.dismiss();
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
