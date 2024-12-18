package com.suntravels.backend.controller;

import com.suntravels.backend.model.SearchResult;
import com.suntravels.backend.model.SearchRequest;
import com.suntravels.backend.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling search requests for available rooms.
 * Provides an endpoint to search for room availability based on user input.
 */
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * Searches for available rooms based on the given search request.
     * The search criteria include details such as check-in date, number of nights, and room requirements.
     *
     * @param searchRequest the {@link SearchRequest} object containing search criteria.
     *                      This request must be valid as per the constraints defined in {@link SearchRequest}.
     * @return a {@link ResponseEntity} containing a list of {@link SearchResult} objects,
     *         each representing the details of available rooms matching the criteria.
     */
    @PostMapping
    public ResponseEntity<List<SearchResult>> searchRooms(@Valid @RequestBody SearchRequest searchRequest) {
        List<SearchResult> results = searchService.searchRooms(searchRequest);
        return ResponseEntity.ok(results);
    }
}
