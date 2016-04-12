package com.papadimitri.marios.weekendassignment3_asos;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.papadimitri.marios.weekendassignment3_asos.di.components.ComponentAPI;
import com.papadimitri.marios.weekendassignment3_asos.di.components.ComponentNet;
import com.papadimitri.marios.weekendassignment3_asos.di.components.DaggerComponentAPI;
import com.papadimitri.marios.weekendassignment3_asos.di.components.DaggerComponentNet;
import com.papadimitri.marios.weekendassignment3_asos.di.modules.ModuleAPI;
import com.papadimitri.marios.weekendassignment3_asos.di.modules.ModuleApp;
import com.papadimitri.marios.weekendassignment3_asos.di.modules.ModuleNet;
import com.papadimitri.marios.weekendassignment3_asos.utilities.Constants;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by fatherjim on 08/04/2016.
 */
public class ApplicationASOS extends Application {
    /**
     * Debug Tag for use logging debug output to LogCat
     */
    public static final Boolean ENABLE_LOGGING = true;

    private RefWatcher refWatcher;

    private ComponentAPI myComponentAPI;
    private ComponentNet myComponentNet;

    @Override public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        refWatcher = LeakCanary.install(this);

        myComponentNet = DaggerComponentNet.builder()
                .moduleNet(new ModuleNet(Constants.BASE_URL))
                .moduleApp(new ModuleApp(this))
                .build();

        myComponentAPI = DaggerComponentAPI.builder()
                .componentNet(myComponentNet)
                .moduleAPI(new ModuleAPI())
                .build();

    }

    public ComponentAPI getComponentAPI() {
        return myComponentAPI;
    }

    public ComponentNet getComponentNet() {
        return myComponentNet;
    }

    public static RefWatcher getRefWatcher(Context context) {
        ApplicationASOS application = (ApplicationASOS) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE){
            // saveGlobalData();
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            // app in background
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Called by the system when the device configuration changes while your
        // component is running. Unlike activities Application doesn't restart when
        // a configuration changes
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        // This method is for use in emulated process environments only.
        // You can simply forget about it because it will never be called on real device
    }

    private void saveGlobalData() {
        // save your stats to database
    }
}
