package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Entry;
import com.tt.vectordb.model.Search;

public class SearchEntriesRequest {

    private float[] embedding;

    private int top;

    public SearchEntriesRequest() {
    }

    public static Search toSearch(SearchEntriesRequest request) {
        Search search = new Search();
        search.setEmbedding(request.getEmbedding());
        search.setTop(request.getTop());
        return (search);
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

}
