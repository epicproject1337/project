package com.example.boket.controller;

import org.json.JSONObject;

//TODO: Start using interface for search instead?
public interface ISearch {
    public void search(String query, SearchCallback onComplete);

    public interface SearchCallback {
        void onSearchComplete(JSONObject content);
    }
}
