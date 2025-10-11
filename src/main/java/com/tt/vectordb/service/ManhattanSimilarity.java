package com.tt.vectordb.service;

import com.tt.vectordb.controller.dto.SearchEntriesRequest;
import com.tt.vectordb.storage.VectorStoreItem;

public class ManhattanSimilarity implements Similarity {

    @Override
    public double compare(SearchEntriesRequest search, VectorStoreItem item) {
        return (manhattanDistance(search.getEmbedding(), item.getEmbedding()));
    }

    private static double manhattanDistance(float[] a, float[] b) {
        double d = 0;
        for (int i = 0; i < a.length; i++) {
            d += Math.abs(a[i] - b[i]);
        }
        return (d);
    }

}
