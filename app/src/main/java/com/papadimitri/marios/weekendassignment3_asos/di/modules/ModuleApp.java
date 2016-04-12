package com.papadimitri.marios.weekendassignment3_asos.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fatherjim on 12/04/2016.
 */

@Module
public class ModuleApp {

    Application mApplication;

    public ModuleApp(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}