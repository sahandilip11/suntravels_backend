package com.suntravels.backend.service;

import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.RoomRequest;
import com.suntravels.backend.model.RoomType;
import com.suntravels.backend.model.SearchRequest;
import com.suntravels.backend.model.SearchResult;
import com.suntravels.backend.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for searching hotel rooms based on search requests.
 * <p>
 * This service handles the business logic of matching search requests with available hotel contracts and room types,
 * calculating the total price, and determining availability.
 */
@Service
public class SearchService {

    @Autowired
    private ContractRepo contractRepo;

    /**
     * Searches for rooms based on the provided search request.
     * <p>
     * This method fetches all contracts, filters them by validity period and room availability,
     * and calculates the total price for the requested rooms.
     *
     * @param searchRequest The search request containing check-in date, number of nights, and room requirements.
     * @return A list of {@link SearchResult} containing available or unavailable room options.
     */
    @Transactional(readOnly = true)
    public List<SearchResult> searchRooms(SearchRequest searchRequest) {

        LocalDate endDate = searchRequest.getCheckInDate().plusDays(searchRequest.getNumberOfNights());
        List<SearchResult> searchResults = new ArrayList<>();

        // Fetch all contracts from the repository
        List<Contract> allContracts = contractRepo.findAll();

        // Iterate through each contract
        for (Contract contract : allContracts) {

            // Check if the contract is valid for the requested date range
            if (contract.getValidFrom().isAfter(searchRequest.getCheckInDate()) ||
                        contract.getValidTo().isBefore(endDate)) {

                // Add an "Unavailable" result if the contract is not valid
                searchResults.add(new SearchResult(null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName()));
                continue;
            }

            boolean allRequestsSatisfied = true; // Flag to track if all room requests can be satisfied
            BigDecimal totalPrice = BigDecimal.ZERO; // Accumulate total price
            List<RoomType> availableRoomTypes = new ArrayList<>();

            // Check each room request against the contract's room types
            for (RoomRequest roomRequest : searchRequest.getRoomRequests()) {

                // Find a matching room type
                Optional<RoomType> matchingRoomType = contract.getRoomTypeList().stream()
                                                              .filter(roomType -> roomType.getMaxNoAdults() == roomRequest.getNumberOfAdults() &&
                                                                                          roomType.getNoOfRooms() >= roomRequest.getNumberOfRooms())
                                                              .findFirst();

                if (matchingRoomType.isPresent()) {
                    RoomType roomType = matchingRoomType.get();

                    // Calculate the price for the matching room type
                    BigDecimal priceForRequest = roomType.getPerPersonPrice()
                                                         .multiply(BigDecimal.valueOf(roomRequest.getNumberOfRooms()))
                                                         .multiply(BigDecimal.valueOf(searchRequest.getNumberOfNights()))
                                                         .multiply(contract.getMarkupRate());

                    totalPrice = totalPrice.add(priceForRequest); // Add to total price
                    availableRoomTypes.add(roomType); // Add to available room types
                } else {
                    // If any room request cannot be satisfied, mark as unavailable
                    allRequestsSatisfied = false;
                    break;
                }
            }

            // Add the search result based on availability
            if (allRequestsSatisfied) {
                SearchResult searchResult = new SearchResult(availableRoomTypes, totalPrice, "Available", contract.getHotel().getHotelName());
                System.out.println(searchResult.toString()); // Log the search result
                searchResults.add(searchResult);
            } else {
                searchResults.add(new SearchResult(null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName()));
            }
        }

        return searchResults; // Return the list of search results
    }
}
