package com.suntravels.backend.integratonTests;

import com.suntravels.backend.model.RoomRequest;
import com.suntravels.backend.model.SearchRequest;
import com.suntravels.backend.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class SearchRequestIntegrationTests
{

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port; // Dynamically inject the running server port

    @Test
    void testSearchRoomsE2E()
    {
        // Backend API URL
        String url = "http://localhost:" + port + "/api/v1/search";

        // Define the search criteria
        SearchRequest searchRequest = new SearchRequest(
                LocalDate.of( 2024, 12, 30 ), // Check-in date
                5, // Duration in days
                List.of( new RoomRequest( 2, 2 ) ) // Room requirements (2 adults, 2 children)
        );

        // Send the request and get all available results
        SearchResult[] results = testRestTemplate.postForObject( url, searchRequest, SearchResult[].class );

        // Verify that results are not empty
        assertEquals( true, results.length > 0, "Expected at least one hotel in the results" );

        // Iterate through the list to find TestHotel
        boolean testHotelFound = false;
        for( SearchResult result : results )
        {
            if( "TestHotel".equals( result.getHotelName() ) )
            {
                testHotelFound = true;
                break;
            }
        }

        // Assert that TestHotel is in the results
        assertEquals( true, testHotelFound, "Expected 'TestHotel' to be in the results" );
    }
}