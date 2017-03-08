package com.kolpakov.githubrepositories.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pkolpakov on 05.03.2017.
 */

public class Repository {
    @SerializedName("id")
    private long id;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("description")
    private String description;
    @SerializedName("owner")
    private Owner owner;

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public Owner getOwner() {
        return owner;
    }

    public long getId() {
        return id;
    }
}
