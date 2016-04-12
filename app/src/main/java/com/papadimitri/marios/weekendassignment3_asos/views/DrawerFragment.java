package com.papadimitri.marios.weekendassignment3_asos.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.papadimitri.marios.weekendassignment3_asos.ApplicationASOS;
import com.papadimitri.marios.weekendassignment3_asos.R;
import com.papadimitri.marios.weekendassignment3_asos.adapters.RecyclerCategoryAdapter;
import com.papadimitri.marios.weekendassignment3_asos.model.Categories.CategoryModel;
import com.papadimitri.marios.weekendassignment3_asos.services.ASOS_API;
import com.papadimitri.marios.weekendassignment3_asos.utilities.RxUtils;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fatherjim on 10/04/2016.
 */
public class DrawerFragment extends Fragment {

    @Inject
    ASOS_API api;

    @Inject
    SharedPreferences mSharedPreferences;

    @Inject
    OkHttpClient mOkHttpClient;

    @Bind(R.id.btnMen)
    Button btnMen;
    @Bind(R.id.btnWomen)
    Button btnWomen;
    @Bind(R.id.category_recycler_view)
    RecyclerView recycler_view;

    private ActionBarDrawerToggle drawer_toggle;
    private DrawerLayout drawer_layout;
    private View containerView;

    private RecyclerCategoryAdapter category_adapter;
    private CompositeSubscription subscriptions;

    private boolean isProductDetailsShown = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ApplicationASOS) getActivity().getApplication()).getComponentAPI().inject(this);

        subscriptions = new CompositeSubscription();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category_drawer,container,false);
        ButterKnife.bind(this, layout);

        getMenCategory();

        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout dl, final Toolbar toolbar) {
        View containerView = getActivity().findViewById(fragmentId);
        drawer_layout = dl;
        drawer_toggle = new ActionBarDrawerToggle(getActivity(), drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                Toast.makeText(getActivity(),"drawer close",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        drawer_layout.addDrawerListener(drawer_toggle);
        drawer_layout.post(new Runnable() {
            @Override
            public void run() {
                drawer_toggle.syncState();
            }
        });
    }

    public void closeDrawer() {
        drawer_layout.closeDrawers();
    }

    public void setProductDetailsShown() {
        isProductDetailsShown = true;
    }

    @OnClick(R.id.btnMen)
    public void getMenCategory() {
        btnMen.setEnabled(false);
        btnWomen.setEnabled(true);
        final ProgressDialog progress_dialog = new ProgressDialog(getActivity());
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
        subscriptions.add(api.getMenCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<CategoryModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "onError");
                    }

                    @Override
                    public void onNext(CategoryModel cat_model) {
                        Log.i("Retrofit", "onNext");
                        if (cat_model.getListing().size() > 0 && recycler_view != null) {
                            category_adapter = new RecyclerCategoryAdapter(cat_model, R.layout.cardview_category, getActivity());
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recycler_view.setAdapter(category_adapter);
                            progress_dialog.dismiss();
                        }
                    }
                }));
    }

    @OnClick(R.id.btnWomen)
    public void getWomenCategory() {
        btnWomen.setEnabled(false);
        btnMen.setEnabled(true);
        final ProgressDialog progress_dialog = new ProgressDialog(getActivity());
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
        subscriptions.add(api.getWomenCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<CategoryModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "onError");
                    }

                    @Override
                    public void onNext(CategoryModel cat_model) {

                        Log.i("Retrofit", "onNext");
                        if (cat_model.getListing().size() > 0 && recycler_view != null) {
                            category_adapter = new RecyclerCategoryAdapter(cat_model, R.layout.cardview_category, getActivity());
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recycler_view.setAdapter(category_adapter);
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
