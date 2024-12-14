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

@Service
public class SearchService {

    @Autowired
    private ContractRepo contractRepo;

    @Transactional(readOnly = true)
    public List<SearchResult> searchRooms(SearchRequest searchRequest) {
//        System.out.println("Received SearchRequest:");
//        System.out.println("  Check-in Date: " + searchRequest.getCheckInDate());
//        System.out.println("  Number of Nights: " + searchRequest.getNumberOfNights());
//        System.out.println("  Room Requests: " + searchRequest.getRoomRequests().size());
//        searchRequest.getRoomRequests().forEach(req -> {
//            System.out.println("    RoomRequest - Adults: " + req.getNumberOfAdults()
//                                       + ", Rooms: " + req.getNumberOfRooms());
//        });

        LocalDate endDate = searchRequest.getCheckInDate().plusDays(searchRequest.getNumberOfNights());
        List<SearchResult> searchResults = new ArrayList<>();

        // Fetch all contracts
        List<Contract> allContracts = contractRepo.findAll();
        //System.out.println("Fetched " + allContracts.size() + " contracts from the database.");

        for (Contract contract : allContracts) {
//            System.out.println("Processing Contract:");
//            System.out.println("  Hotel Name: " + contract.getHotel().getHotelName());
//            System.out.println("  Contract Valid From: " + contract.getValidFrom());
//            System.out.println("  Contract Valid To: " + contract.getValidTo());

            if (contract.getValidFrom().isAfter(searchRequest.getCheckInDate()) ||
                        contract.getValidTo().isBefore(endDate)) {
                //System.out.println("  Contract dates do not match the search request. Marking as unavailable.");
                searchResults.add(new SearchResult(null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName()));
                continue;
            }

            boolean allRequestsSatisfied = true;
            BigDecimal totalPrice = BigDecimal.ZERO;
            List<RoomType> availableRoomTypes = new ArrayList<>();

            for (RoomRequest roomRequest : searchRequest.getRoomRequests()) {
                //System.out.println("  Checking RoomRequest - Adults: " + roomRequest.getNumberOfAdults()
                //                           + ", Rooms: " + roomRequest.getNumberOfRooms());

                Optional<RoomType> matchingRoomType = contract.getRoomTypeList().stream()
                                                              .filter(roomType -> roomType.getMaxNoAdults() == roomRequest.getNumberOfAdults() &&
                                                                                          roomType.getNoOfRooms() >= roomRequest.getNumberOfRooms())
                                                              .findFirst();

                if (matchingRoomType.isPresent()) {
                    RoomType roomType = matchingRoomType.get();
//                    System.out.println("    Matching RoomType Found: " + roomType.getTypeName());
//                    System.out.println("      Max Adults: " + roomType.getMaxNoAdults());
//                    System.out.println("      No of Rooms Available: " + roomType.getNoOfRooms());
//                    System.out.println("      Per Person Price: " + roomType.getPerPersonPrice());

                    BigDecimal priceForRequest = roomType.getPerPersonPrice()
                                                         .multiply(BigDecimal.valueOf(roomRequest.getNumberOfRooms()))
                                                         .multiply(BigDecimal.valueOf(searchRequest.getNumberOfNights()))
                                                         .multiply(contract.getMarkupRate());

                    //System.out.println("    Calculated Price for RoomRequest: " + priceForRequest);

                    totalPrice = totalPrice.add(priceForRequest);
                    availableRoomTypes.add(roomType);
                } else {
                    //System.out.println("    No matching RoomType found. Marking request as unsatisfied.");
                    allRequestsSatisfied = false;
                    break;
                }
            }

            if (allRequestsSatisfied) {
                //System.out.println("  All room requests satisfied. Adding hotel as available.");
                //System.out.println("    Total Price: " + totalPrice);
                SearchResult searchResult = new SearchResult(availableRoomTypes, totalPrice, "Available", contract.getHotel().getHotelName());
                System.out.println(searchResult.toString());
                searchResults.add(searchResult);
            } else {
                //System.out.println("  Not all room requests satisfied. Adding hotel as unavailable.");
                searchResults.add(new SearchResult(null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName()));
            }
        }

        System.out.println("Search completed. Found " + searchResults.getFirst().getRoomType() + " results.");
        return searchResults;
    }
}
