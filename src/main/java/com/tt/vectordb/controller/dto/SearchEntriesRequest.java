package com.tt.vectordb.controller.dto;

public class SearchEntriesRequest {

    private float[] embedding;

    private int top;

    public SearchEntriesRequest() {
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
