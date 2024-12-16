package com.suntravels.backend.service;

import com.suntravels.backend.dto.HotelContractDto;
import com.suntravels.backend.dto.HotelDto;
import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.model.RoomType;
import com.suntravels.backend.repository.ContractRepo;
import com.suntravels.backend.repository.HotelRepo;
import com.suntravels.backend.repository.RoomTypeRepo;
import com.suntravels.backend.utils.HotelContractMapper;
import com.suntravels.backend.utils.HotelMapper;
import com.suntravels.backend.utils.RoomTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelContractService
{

    private final ContractRepo contractRepo;
    private final HotelRepo hotelRepo;
    private final RoomTypeRepo roomTypeRepo;

    @Autowired
    public HotelContractService( ContractRepo contractRepo, HotelRepo hotelRepo, RoomTypeRepo roomTypeRepo )
    {
        this.contractRepo = contractRepo;
        this.hotelRepo = hotelRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    public HotelContractDto addContract( HotelContractDto contractDto )
    {


        // Fetch or create a Hotel
        Hotel hotel = hotelRepo.findByHotelName( contractDto.getHotelName() )
                               .orElseGet( () ->
                               {

                                   Hotel newHotel = HotelMapper.dtoToEntity( new HotelDto( contractDto.getHotelName() ) );
                                   Hotel savedHotel = hotelRepo.save( newHotel );

                                   return savedHotel;
                               } );


        // Convert DTO to Contract entity using HotelContractMapper
        Contract contract = HotelContractMapper.dtoToEntity( contractDto, hotel );


        // Save the Contract entity to the database
        Contract savedContract = contractRepo.save( contract );

        // Convert RoomTypeDtos to RoomType entities and associate them with the saved Contract
        List<RoomType> roomTypes = contractDto.getRoomTypeList().stream()
                                              .map( roomTypeDto ->
                                              {
                                                  RoomType roomType = RoomTypeMapper.dtoToEntity( roomTypeDto, savedContract );

                                                  return roomType;
                                              } )
                                              .collect( Collectors.toList() );

        // Save all RoomType entities to the database
        List<RoomType> savedRoomTypes = roomTypeRepo.saveAll( roomTypes );


        // Convert the saved Contract and RoomTypes back to a DTO and return
        HotelContractDto resultDto = HotelContractMapper.entityToDto( savedContract, savedRoomTypes );

        return resultDto;
    }

    public List<HotelContractDto> getContracts()
    {


        // Fetch all contracts
        List<Contract> contracts = contractRepo.findAll();


        // Map each Contract to HotelContractDto
        List<HotelContractDto> hotelContractDto = contracts.stream()
                                                           .map( contract ->
                                                           {

                                                               HotelContractDto dto = HotelContractMapper.entityToDto( contract, contract.getRoomTypeList() );

                                                               return dto;
                                                           } )
                                                           .collect( Collectors.toList() );


        return hotelContractDto; // Return the list of DTOs
    }

    public void deleteContract( Long id )
    {
        contractRepo.deleteById( id );
    }

    public HotelDto findByHotelName( String hotelName )
    {
        Hotel hotel = hotelRepo.findByHotelName( hotelName )
                               .orElseThrow( () -> new RuntimeException( "Hotel not found with name: " + hotelName ) );
        return new HotelDto( hotel.getHotelName() );
    }
}
