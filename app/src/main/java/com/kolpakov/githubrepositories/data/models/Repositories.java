package com.kolpakov.githubrepositories.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pkolpakov on 06.03.2017.
 */

public class Repositories {
    @SerializedName("items")
    private List<Repository> items;
    private PageLinks pageLinks;

    public void setPageLinks(PageLinks pageLinks) {
        this.pageLinks = pageLinks;
    }

    public PageLinks getPageLinks() {
        return pageLinks;
    }

    public List<Repository> getItems() {
        return items;
    }
}
