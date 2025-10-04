package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Index;

public class ListIndexesResponse {

    private long id;

    private String name;

    private int dimensions;

    private String similarity;

    private String optimization;

    public ListIndexesResponse() {
    }

    public static ListIndexesResponse fromIndex(Index index) {
        ListIndexesResponse response = new ListIndexesResponse();
        response.setId(index.getId());
        response.setName(index.getName());
        response.setDimensions(index.getDimensions());
        response.setSimilarity(index.getSimilarity());
        response.setOptimization(index.getOptimization());
        return (response);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getOptimization() {
        return optimization;
    }

    public void setOptimization(String optimization) {
        this.optimization = optimization;
    }

}
