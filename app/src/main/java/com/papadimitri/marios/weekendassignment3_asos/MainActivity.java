package com.papadimitri.marios.weekendassignment3_asos;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.papadimitri.marios.weekendassignment3_asos.utilities.Log;
import com.papadimitri.marios.weekendassignment3_asos.utilities.MemoryCache;
import com.papadimitri.marios.weekendassignment3_asos.views.DrawerFragment;
import com.papadimitri.marios.weekendassignment3_asos.views.ProductDetailsFragment;
import com.papadimitri.marios.weekendassignment3_asos.views.ProductListFragment;
import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /**
     * Debug Tag for use logging debug output to LogCat
     */
    // get inside of Activity or Service
    Application app = getApplication();

    // get from Contex
    // Application app = (Application)view.getContext().getApplicationContext();
    private static final String mLogTag = MainActivity.class.getName();

    public static final int CATEGORY_ID = 0;
    public static final int PRODUCT_ID = 1;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer_layout;

    private DrawerFragment fragment_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            Log.i(mLogTag, "onCreate:" + this);

            ///// TOOLBAR CONFIGURATION \\\\\
            toolbar.setNavigationIcon(R.drawable.menu); // Shows the hamburger icon, not the back icon
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false); // Removes the app_name string from the toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            ///// NAVIGATION DRAWER FRAGMENT \\\\\
            fragment_drawer = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
            fragment_drawer.setUp(R.id.fragment_drawer, drawer_layout, toolbar);

            this.registerComponentCallbacks(this);

            app.registerComponentCallbacks(new ComponentCallbacks2() {
                @Override
                public void onTrimMemory(int level) {
                    if(level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE){
                        // saveGlobalData();
                    }
                    else if(level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
                        // app in background
                    }
                }

                @Override
                public void onConfigurationChanged(Configuration configuration) {

                }

                @Override
                public void onLowMemory() {
                    // This is called when the overall system is running low on memory
                    // and actively running processes should trim their memory usage
                    // Note that onLowMemory is not called from API 14. You should only use it as a fallback for older versions,
                    // which can be treated the same as onTrimMemory with the ComponentCallbacks2.TRIM_MEMORY_COMPLETE level.
                    // call it here to support old operating systems
                    // saveGlobalData();
                }
            });

        }
        /**
         * This method simply throws an Exception if the incoming parameter a is not a positive number, just for fun.
         *
         * @param a Whether or not to throw an exception
         * @throws Exception
         */
        catch(Exception e){

        }
    }

    public void switchFragment(int type, String id, String name) {
        ProductListFragment plf = null;
        ProductDetailsFragment pdf = null;
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (type == MainActivity.CATEGORY_ID) {
            plf = new ProductListFragment();
            plf.setArguments(bundle);
            fragment_drawer.closeDrawer();
        } else {
            pdf = new ProductDetailsFragment();
            pdf.setArguments(bundle);
            fragment_drawer.closeDrawer();
        }
        if (plf != null)
            transaction.replace(R.id.container_body,plf);
        else if (pdf != null)
            transaction.replace(R.id.container_body,pdf);
        transaction.commit();
    }


    // Creates the items(options) that are positioned on the toolbar_options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return true;
    }

    // Handles the events that occur on the Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourites:
                Toast.makeText(this,"You clicked favourites",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_checkout:
                Toast.makeText(this,"You clicked basket",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onTrimMemory(int level) {
        Log.v("MainActivity Memory", "onTrimMemory() with level=" + level);
        MemoryCache cache= new MemoryCache();
        // Memory we can release here will help overall system performance, and
        // make us a smaller target as the system looks for memory

        if (level >= TRIM_MEMORY_MODERATE) { // 60
            // Nearing middle of list of cached background apps; evict our
            // entire thumbnail cache
            Log.v("MainActivity Memory", "evicting entire thumbnail cache");
            cache.evictAll();
        } else if (level >= TRIM_MEMORY_BACKGROUND) { // 40
            // Entering list of cached background apps; evict oldest half of our
            // thumbnail cache
            Log.v("MainActivity Memory", "evicting oldest half of thumbnail cache");
            //     mCache.trimToSize(mCache.size() / 2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        RefWatcher refWatcher = ApplicationASOS.getRefWatcher(getApplication());
        refWatcher.watch(this);
    }
}
// TODO GitHub modification
// TODO HOME BUTTON SHOULD BECOME A BACK BUTTON
// TODO IMPLEMENT SHAREDPREFS "ADD TO LONG CLICK FAVORITES AND BASKET"
// TODO VIEW THE CONTENTS OF FAVOURITES AND BASKET AND CHOOSE TO REMOVE ITEMS
// TODO FIX CACHE/LEAK CANARY ERRORS (probably memory issue when loading fragment ???)
// TODO SHOULD I PREFER WORKING WITH FRAGMENTS OVER DRAWER LAYOUTS ??