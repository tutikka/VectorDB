package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Index;

public class DeleteIndexResponse {

    private long id;

    public DeleteIndexResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
