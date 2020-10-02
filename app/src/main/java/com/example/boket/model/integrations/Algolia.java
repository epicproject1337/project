package com.example.boket.model.integrations;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.boket.controller.ISearch;

import org.json.JSONObject;

public class Algolia implements ISearch {

    //TODO: Shouldnt be hardcoded into app for security reasons.
    private static final String appId = "SPT4NUD890";
    private static final String apiKey = "5d9282943a14888fe4abb63d6384052c";

    private static final Client client = new Client(appId, apiKey);

    private final String indexName;
    private final Index index;

    public Algolia(String indexName) {
        this.indexName = indexName;
        this.index = client.getIndex(indexName);
    }

    public void addToIndex(JSONObject json) {
        index.addObjectAsync(json,null);
    }

    public void search(String query, SearchCallback onComplete) {
        CompletionHandler completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                //TODO: error handling
                onComplete.onSearchComplete(content);
            }
        };
        index.searchAsync(new Query(query), completionHandler);
    }
}
