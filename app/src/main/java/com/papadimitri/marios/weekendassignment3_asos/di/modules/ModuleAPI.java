package com.papadimitri.marios.weekendassignment3_asos.di.modules;

import com.papadimitri.marios.weekendassignment3_asos.di.scopes.UserScope;
import com.papadimitri.marios.weekendassignment3_asos.services.ASOS_API;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by fatherjim on 11/04/2016.
 */
@Module
public class ModuleAPI {

    @Provides
    @UserScope
    public ASOS_API providesIContactsInterface(RestAdapter retrofit) {
        return retrofit.create(ASOS_API.class);
    }
}
