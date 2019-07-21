package com.example.tochkatest.model.git;

import java.util.ArrayList;
import java.util.List;

import com.example.tochkatest.model.vk.Profile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputData {

    @SerializedName("total_count")
    @Expose
    private int totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private ArrayList<GitUsers> items = null;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public ArrayList<GitUsers> getItems() {
        return items;
    }

    public void setItems(ArrayList<GitUsers> items) {
        this.items = items;
    }
}