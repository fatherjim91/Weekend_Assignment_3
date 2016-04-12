package com.papadimitri.marios.weekendassignment3_asos.di.components;

import android.content.SharedPreferences;

import com.papadimitri.marios.weekendassignment3_asos.di.modules.ModuleApp;
import com.papadimitri.marios.weekendassignment3_asos.di.modules.ModuleNet;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.RestAdapter;

/**
 * Created by fatherjim on 12/04/2016.
 */
@Singleton
@Component(modules={ModuleApp.class, ModuleNet.class})
public interface ComponentNet {

    RestAdapter retrofit();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();

}
