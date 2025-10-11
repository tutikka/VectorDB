package com.tt.vectordb.controller;

import com.tt.vectordb.config.Configuration;
import com.tt.vectordb.controller.dto.*;
import com.tt.vectordb.model.Entry;
import com.tt.vectordb.model.Index;
import com.tt.vectordb.model.SearchResult;
import com.tt.vectordb.service.DBService;
import jakarta.annotation.PreDestroy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    public APIController() {
    }

    @PostMapping("/indexes")
    @ResponseBody
    public CreateIndexResponse createIndex(@RequestBody CreateIndexRequest request) {
        try {
            Index index = CreateIndexRequest.toIndex(request);
            index.setId(System.currentTimeMillis());
            DBService.getService().createIndex(index);
            return (CreateIndexResponse.fromIndex(index));
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/indexes")
    @ResponseBody
    public List<ListIndexesResponse> listIndexes() {
        List<Index> indexes = DBService.getService().listIndexes();
        List<ListIndexesResponse> responses = new ArrayList<>();
        for (Index index : indexes) {
            responses.add(ListIndexesResponse.fromIndex(index));
        }
        return (responses);
    }

    @GetMapping("/indexes/{id}")
    @ResponseBody
    public GetIndexResponse getIndex(@PathVariable("id") long id) {
        Index index = DBService.getService().getIndex(id);
        if (index != null) {
            GetIndexResponse response = GetIndexResponse.fromIndex(index);
            response.setExtras(DBService.getService().getIndexExtras(id));
            return (response);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("index not found with id '%s'", id));
        }
    }

    @DeleteMapping("/indexes/{id}")
    @ResponseBody
    public DeleteIndexResponse deleteIndex(@PathVariable("id") long id) {
        try {
            DBService.getService().deleteIndex(id);
            DeleteIndexResponse response = new DeleteIndexResponse();
            response.setId(id);
            return (response);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/indexes/{id}/entries")
    @ResponseBody
    public CreateEntryResponse createEntry(@PathVariable("id") long id, @RequestBody CreateEntryRequest request) {
        try {
            Entry entry = CreateEntryRequest.toEntry(request);
            DBService.getService().createEntry(id, entry);
            return (CreateEntryResponse.fromEntry(entry));
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/indexes/{id}/entries")
    @ResponseBody
    public List<ListEntriesResponse> listEntries(@PathVariable("id") long id, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Entry> entries = DBService.getService().listEntries(id, offset, limit);
            List<ListEntriesResponse> responses = new ArrayList<>();
            for (Entry entry : entries) {
                responses.add(ListEntriesResponse.fromEntry(entry));
            }
            return (responses);
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/indexes/{id}/search")
    @ResponseBody
    public SearchEntriesResponse searchEntries(@PathVariable("id") long id, @RequestBody SearchEntriesRequest request) {
        try {
            SearchResult result = DBService.getService().searchEntries(id, SearchEntriesRequest.toSearch(request));
            return (SearchEntriesResponse.fromSearchResult(result));
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreDestroy
    public void cleanUp() {
        DBService.getService().close();
    }

}
