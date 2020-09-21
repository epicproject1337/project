package com.example.boket.model;

import org.json.JSONObject;

public interface ISearch {
    public void search(String query, SearchCallback onComplete);

    public interface SearchCallback {
        void onSearchComplete(JSONObject content);
    }
}
