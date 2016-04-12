package com.papadimitri.marios.weekendassignment3_asos.di.components;

import com.papadimitri.marios.weekendassignment3_asos.MainActivity;
import com.papadimitri.marios.weekendassignment3_asos.di.modules.ModuleAPI;
import com.papadimitri.marios.weekendassignment3_asos.di.scopes.UserScope;
import com.papadimitri.marios.weekendassignment3_asos.views.DrawerFragment;
import com.papadimitri.marios.weekendassignment3_asos.views.ProductDetailsFragment;
import com.papadimitri.marios.weekendassignment3_asos.views.ProductListFragment;

import dagger.Component;

/**
 * Created by fatherjim on 12/04/2016.
 */
@UserScope
@Component(dependencies = ComponentNet.class, modules = ModuleAPI.class)
public interface ComponentAPI {

    void inject(MainActivity activity);

    void inject(DrawerFragment fragment);

    void inject(ProductListFragment fragment);

    void inject(ProductDetailsFragment fragment);

}