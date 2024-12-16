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
public class SearchService
{

    @Autowired
    private ContractRepo contractRepo;

    @Transactional( readOnly = true )
    public List<SearchResult> searchRooms( SearchRequest searchRequest )
    {

        LocalDate endDate = searchRequest.getCheckInDate().plusDays( searchRequest.getNumberOfNights() );
        List<SearchResult> searchResults = new ArrayList<>();

        // Fetch all contracts
        List<Contract> allContracts = contractRepo.findAll();


        for( Contract contract : allContracts )
        {


            if( contract.getValidFrom().isAfter( searchRequest.getCheckInDate() ) ||
                        contract.getValidTo().isBefore( endDate ) )
            {

                searchResults.add( new SearchResult( null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName() ) );
                continue;
            }

            boolean allRequestsSatisfied = true;
            BigDecimal totalPrice = BigDecimal.ZERO;
            List<RoomType> availableRoomTypes = new ArrayList<>();

            for( RoomRequest roomRequest : searchRequest.getRoomRequests() )
            {


                Optional<RoomType> matchingRoomType = contract.getRoomTypeList().stream()
                                                              .filter( roomType -> roomType.getMaxNoAdults() == roomRequest.getNumberOfAdults() &&
                                                                                           roomType.getNoOfRooms() >= roomRequest.getNumberOfRooms() )
                                                              .findFirst();

                if( matchingRoomType.isPresent() )
                {
                    RoomType roomType = matchingRoomType.get();


                    BigDecimal priceForRequest = roomType.getPerPersonPrice()
                                                         .multiply( BigDecimal.valueOf( roomRequest.getNumberOfRooms() ) )
                                                         .multiply( BigDecimal.valueOf( searchRequest.getNumberOfNights() ) )
                                                         .multiply( contract.getMarkupRate() );


                    totalPrice = totalPrice.add( priceForRequest );
                    availableRoomTypes.add( roomType );
                }
                else
                {

                    allRequestsSatisfied = false;
                    break;
                }
            }

            if( allRequestsSatisfied )
            {

                SearchResult searchResult = new SearchResult( availableRoomTypes, totalPrice, "Available", contract.getHotel().getHotelName() );
                System.out.println( searchResult.toString() );
                searchResults.add( searchResult );
            }
            else
            {

                searchResults.add( new SearchResult( null, BigDecimal.ZERO, "Unavailable", contract.getHotel().getHotelName() ) );
            }
        }
        return searchResults;
    }
}
