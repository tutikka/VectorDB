package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Entry;

public class ListEntriesResponse {

    private long id;

    private float[] embedding;

    public ListEntriesResponse() {
    }

    public static ListEntriesResponse fromEntry(Entry entry) {
        ListEntriesResponse response = new ListEntriesResponse();
        response.setId(entry.getId());
        response.setEmbedding(entry.getEmbedding());
        return (response);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

}
