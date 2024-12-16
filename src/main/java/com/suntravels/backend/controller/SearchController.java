package com.suntravels.backend.controller;

import com.suntravels.backend.model.SearchResult;
import com.suntravels.backend.model.SearchRequest;
import com.suntravels.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping( "/api/v1/search" )
public class SearchController
{

    @Autowired
    private SearchService searchService;

    @PostMapping()
    public ResponseEntity<List<SearchResult>> searchRooms( @RequestBody SearchRequest searchRequest )
    {
        List<SearchResult> results = searchService.searchRooms( searchRequest );
        return ResponseEntity.ok( results );
    }
}


