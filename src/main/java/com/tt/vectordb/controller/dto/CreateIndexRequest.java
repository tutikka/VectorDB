package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Index;

public class CreateIndexRequest {

    private String name;

    private int dimensions;

    private String similarity;

    private String optimization;

    public CreateIndexRequest() {
    }

    public static Index toIndex(CreateIndexRequest request) {
        Index index = new Index();
        index.setName(request.getName());
        index.setDimensions(request.getDimensions());
        index.setSimilarity(request.getSimilarity());
        index.setOptimization(request.getOptimization());
        return (index);
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
