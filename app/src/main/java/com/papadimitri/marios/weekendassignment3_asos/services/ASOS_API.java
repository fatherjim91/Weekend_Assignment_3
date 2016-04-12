package com.papadimitri.marios.weekendassignment3_asos.services;

import com.papadimitri.marios.weekendassignment3_asos.model.Categories.CategoryModel;
import com.papadimitri.marios.weekendassignment3_asos.model.Products.ProductModel;
import com.papadimitri.marios.weekendassignment3_asos.model.SingleProduct.SpecifiedProduct;
import com.papadimitri.marios.weekendassignment3_asos.utilities.Constants;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by fatherjim on 08/04/2016.
 */
public interface ASOS_API {

    @GET(Constants.CATEGORIES_MEN_URL)
    Observable<CategoryModel> getMenCategory();

    @GET(Constants.CATEGORIES_WOMEN_URL)
    Observable<CategoryModel> getWomenCategory();

    @GET(Constants.PRODUCTS_BY_CATEGORY_URL)
    Observable<ProductModel> getProductsByCategoryId(@Query("id") String id);

    @GET(Constants.PRODUCT_BY_ID_URL)
    Observable<SpecifiedProduct> getProductById(@Query("prod_id") int prod_id);



}
