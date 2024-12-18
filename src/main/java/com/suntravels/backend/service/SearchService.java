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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for searching available hotel rooms and calculating the total cost.
 * <p>
 * This service handles the logic to:
 * 1. Group available room types based on their capacity.
 * 2. Generate all combinations of room types.
 * 3. Check each combination against the requested rooms.
 * 4. Calculate the total price for valid combinations.
 * 5. Return the optimal result (minimum cost) for each hotel contract.
 */
@Service
public class SearchService {

    @Autowired
    private ContractRepo contractRepo;

    /**
     * Searches for rooms based on the provided search request.
     *
     * @param searchRequest The search request containing check-in date, number of nights, and room requirements.
     * @return A list of {@link SearchResult} containing the optimal room combinations and prices for each hotel.
     */
    @Transactional(readOnly = true)
    public List<SearchResult> searchRooms(SearchRequest searchRequest) {
        LocalDate endDate = searchRequest.getCheckInDate().plusDays(searchRequest.getNumberOfNights());
        List<SearchResult> searchResults = new ArrayList<>();

        // Fetch all contracts
        List<Contract> allContracts = contractRepo.findAll();

        // Iterate through each hotel contract
        for (Contract contract : allContracts) {

            // Validate if the contract is valid for the requested check-in and check-out dates
            if (contract.getValidFrom().isAfter(searchRequest.getCheckInDate()) ||
                        contract.getValidTo().isBefore(endDate)) {
                searchResults.add(new SearchResult(null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName()));
                continue;
            }

            // Group RoomTypes by the number of adults they can accommodate
            Map<Integer, List<RoomType>> groupedRoomTypes = contract.getRoomTypeList()
                                                                    .stream()
                                                                    .collect(Collectors.groupingBy(RoomType::getMaxNoAdults));

            // Convert the grouped room types map into a list of lists for combination generation
            List<List<RoomType>> groupedLists = new ArrayList<>(groupedRoomTypes.values());

            // Generate all possible combinations of room types
            List<List<RoomType>> allCombinations = generateCombinations(groupedLists);

            // Variables to track the optimal combination and minimum price
            List<RoomType> optimalRoomCombination = null;
            BigDecimal minTotalPrice = null;

            // Evaluate each combination to find the valid one with the minimum total price
            for (List<RoomType> combination : allCombinations) {
                BigDecimal totalPrice = BigDecimal.ZERO;
                boolean isValidCombination = true;
                List<RoomType> usedRoomTypes = new ArrayList<>();

                // Check if the current combination satisfies all room requests
                for (RoomRequest roomRequest : searchRequest.getRoomRequests()) {
                    Optional<RoomType> matchingRoomType = combination.stream()
                                                                     .filter(roomType -> roomType.getMaxNoAdults() == roomRequest.getNumberOfAdults()
                                                                                                 && roomType.getNoOfRooms() >= roomRequest.getNumberOfRooms())
                                                                     .findFirst();

                    if (matchingRoomType.isPresent()) {
                        RoomType roomType = matchingRoomType.get();

                        // Avoid using the same room type more than once
                        if (!usedRoomTypes.contains(roomType)) {
                            usedRoomTypes.add(roomType);

                            // Calculate the price for this room type
                            BigDecimal roomPrice = roomType.getPerPersonPrice()
                                                           .multiply(BigDecimal.valueOf(roomRequest.getNumberOfAdults()))
                                                           .multiply(BigDecimal.valueOf(roomRequest.getNumberOfRooms()))
                                                           .multiply(BigDecimal.valueOf(searchRequest.getNumberOfNights()))
                                                           .multiply((contract.getMarkupRate().add(BigDecimal.valueOf(100)))
                                                                             .divide(BigDecimal.valueOf(100)));

                            totalPrice = totalPrice.add(roomPrice);
                        }
                    } else {
                        // If any room request cannot be satisfied, mark this combination as invalid
                        isValidCombination = false;
                        break;
                    }
                }

                // Update the minimum price and optimal combination if this combination is valid
                if (isValidCombination && (minTotalPrice == null || totalPrice.compareTo(minTotalPrice) < 0)) {
                    minTotalPrice = totalPrice;
                    optimalRoomCombination = new ArrayList<>(usedRoomTypes);
                }
            }

            // Add the result for this contract
            if (optimalRoomCombination != null) {
                searchResults.add(new SearchResult(optimalRoomCombination, minTotalPrice, "Available", contract.getHotel().getHotelName()));
            } else {
                searchResults.add(new SearchResult(null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName()));
            }
        }

        // Sort results by total price in ascending order
        searchResults.sort(Comparator.comparing(SearchResult::getPrice));

        return searchResults;
    }

    /**
     * Generates all combinations of room types based on the grouped lists.
     *
     * @param groupedLists A list of room type groups where each group contains room types for a specific adult capacity.
     * @return A list containing all possible combinations of room types.
     */
    private List<List<RoomType>> generateCombinations(List<List<RoomType>> groupedLists) {
        List<List<RoomType>> combinations = new ArrayList<>();
        generateCombinationsHelper(groupedLists, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    /**
     * Helper method for generating combinations of room types recursively.
     *
     * @param groupedLists       The grouped room types.
     * @param index              The current index of the group being processed.
     * @param currentCombination The current combination being generated.
     * @param combinations       The list of all generated combinations.
     */
    private void generateCombinationsHelper(List<List<RoomType>> groupedLists, int index,
                                            List<RoomType> currentCombination, List<List<RoomType>> combinations) {
        if (index == groupedLists.size()) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (RoomType roomType : groupedLists.get(index)) {
            currentCombination.add(roomType);
            generateCombinationsHelper(groupedLists, index + 1, currentCombination, combinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}
