package com.tt.vectordb.service;

import com.tt.vectordb.controller.dto.SearchEntriesRequest;
import com.tt.vectordb.storage.VectorStoreItem;

public interface Similarity {

    double compare(SearchEntriesRequest search, VectorStoreItem item);

}
