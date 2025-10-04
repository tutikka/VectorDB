package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Entry;

public class CreateEntryRequest {

    private long id;

    private float[] embedding;

    public CreateEntryRequest() {
    }

    public static Entry toEntry(CreateEntryRequest request) {
        Entry entry = new Entry();
        entry.setId(request.getId());
        entry.setEmbedding(request.getEmbedding());
        return (entry);
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
