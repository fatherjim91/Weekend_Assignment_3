package com.papadimitri.marios.weekendassignment3_asos.services;

import com.papadimitri.marios.weekendassignment3_asos.utilities.APIErrorHandler;
import com.papadimitri.marios.weekendassignment3_asos.utilities.Constants;

import retrofit.RestAdapter;

/**
 * Created by fatherjim on 10/04/2016.
 */
public class Services {

    public static ASOS_API _createAPI() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setErrorHandler(new APIErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(ASOS_API.class);
    }

}
