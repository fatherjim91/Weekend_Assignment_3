package com.papadimitri.marios.weekendassignment3_asos.model.Categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CategoryModel {

    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Listing")
    @Expose
    private List<CategoryListing> listing = new ArrayList<CategoryListing>();
    @SerializedName("SortType")
    @Expose
    private String SortType;

    /**
     *
     * @return
     * The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     * The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     *
     * @return
     * The Listing
     */
    public List<CategoryListing> getListing() {
        return listing;
    }

    /**
     *
     * @param Listing
     * The Listing
     */
    public void setListing(List<CategoryListing> Listing) {
        this.listing = listing;
    }

    /**
     *
     * @return
     * The SortType
     */
    public String getSortType() {
        return SortType;
    }

    /**
     *
     * @param SortType
     * The SortType
     */
    public void setSortType(String SortType) {
        this.SortType = SortType;
    }

}