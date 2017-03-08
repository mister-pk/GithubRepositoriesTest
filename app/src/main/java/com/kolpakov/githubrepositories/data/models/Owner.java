package com.kolpakov.githubrepositories.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pkolpakov on 06.03.2017.
 */

public class Owner {

    @SerializedName("avatar_url")
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
