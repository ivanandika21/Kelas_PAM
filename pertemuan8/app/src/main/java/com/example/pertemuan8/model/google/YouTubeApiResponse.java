package com.example.pertemuan8.model.google;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YouTubeApiResponse {

    @SerializedName("items")
    private ArrayList<item> items;

    public YouTubeApiResponse(ArrayList<item> items) {
        this.items = items;
    }

    public ArrayList<item> getItems() {
        return items;
    }

    public void setItems(ArrayList<item> items) {
        this.items = items;
    }
}
