package com.tt.vectordb.controller.dto;

import com.tt.vectordb.model.Index;

import java.util.Map;

public class GetIndexResponse {

    private long id;

    private String name;

    private int dimensions;

    private String similarity;

    private String optimization;

    private Map<String, Object> extras;

    public GetIndexResponse() {
    }

    public static GetIndexResponse fromIndex(Index index) {
        GetIndexResponse response = new GetIndexResponse();
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

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

}
