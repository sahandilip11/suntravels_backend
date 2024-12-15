package com.suntravels.backend.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.suntravels.backend.controller.SearchController;
import com.suntravels.backend.model.RoomRequest;
import com.suntravels.backend.model.SearchRequest;
import com.suntravels.backend.model.SearchResult;
import com.suntravels.backend.repository.ContractRepo;
import com.suntravels.backend.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
@Import(SearchControllerTests.TestConfig.class) // Import the test-specific configuration
public class SearchControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SearchService searchService; // Mocked SearchService

    @Autowired
    private ObjectMapper objectMapper; // Use a shared instance

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        // Register JavaTimeModule for handling LocalDate
        objectMapper.registerModule(new JavaTimeModule());

        // Disable serialization of dates as timestamps
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void testSearchRooms() throws Exception {
        SearchRequest searchRequest = new SearchRequest(
                LocalDate.of(2024, 12, 15),
                5,
                List.of(new RoomRequest(2, 2))
        );

        SearchResult mockResult = new SearchResult(
                null,
                BigDecimal.valueOf(1500),
                "Available",
                "TestHotel"
        );

        Mockito.when(searchService.searchRooms(Mockito.any(SearchRequest.class))).thenReturn(List.of(mockResult));

        mockMvc.perform(post("/api/v1/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(searchRequest))) // Use the configured ObjectMapper
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].hotelName").value("TestHotel"))
               .andExpect(jsonPath("$[0].availabilityStatus").value("Available"))
               .andExpect(jsonPath("$[0].price").value(1500.0));
    }


    @TestConfiguration // Test-specific configuration
    static class TestConfig {

        @Bean
        public SearchService searchService() {
            return Mockito.mock(SearchService.class);
        }

        @Bean
        public ContractRepo contractRepo() {
            return Mockito.mock(ContractRepo.class);
        }
    }
}
