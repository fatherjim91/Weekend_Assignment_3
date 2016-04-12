package com.papadimitri.marios.weekendassignment3_asos.model.Categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CategoryListing {

    @SerializedName("CategoryId")
    @Expose
    private String CategoryId;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("ProductCount")
    @Expose
    private Integer ProductCount;

    /**
     *
     * @return
     * The CategoryId
     */
    public String getCategoryId() {
        return CategoryId;
    }

    /**
     *
     * @param CategoryId
     * The CategoryId
     */
    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The ProductCount
     */
    public Integer getProductCount() {
        return ProductCount;
    }

    /**
     *
     * @param ProductCount
     * The ProductCount
     */
    public void setProductCount(Integer ProductCount) {
        this.ProductCount = ProductCount;
    }

}