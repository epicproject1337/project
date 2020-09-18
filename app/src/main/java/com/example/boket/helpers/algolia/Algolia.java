package com.example.boket.helpers.algolia;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;

import org.json.JSONObject;

public class Algolia {

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
        index.addObjectAsync(json, null);
    }

    public void search(String query, AlgoliaCallback onCallback) {
        CompletionHandler completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                //TODO: error handling
                onCallback.onSearchComplete(content);
            }
        };
        index.searchAsync(new Query(query), completionHandler);
    }

    public interface AlgoliaCallback {
        void onSearchComplete(JSONObject content);
    }
}
