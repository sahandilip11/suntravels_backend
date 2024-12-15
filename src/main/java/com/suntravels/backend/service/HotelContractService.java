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
public class HotelContractService {

    private final ContractRepo contractRepo;
    private final HotelRepo hotelRepo;
    private final RoomTypeRepo roomTypeRepo;

    @Autowired
    public HotelContractService(ContractRepo contractRepo, HotelRepo hotelRepo, RoomTypeRepo roomTypeRepo) {
        this.contractRepo = contractRepo;
        this.hotelRepo = hotelRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    public HotelContractDto addContract(HotelContractDto contractDto) {
        System.out.println("Received contractDto with hotelName: " + contractDto.getHotelName()
                                   + ", startDate: " + contractDto.getValidFrom()
                                   + ", endDate: " + contractDto.getValidTo()
                                   + ", markupRate: " + contractDto.getMarkupRate());

        System.out.println("RoomTypeList size: " + contractDto.getRoomTypeList().size());

        // Debugging each RoomType in the list
        contractDto.getRoomTypeList().forEach(roomTypeDto -> {
            System.out.println("RoomTypeDto details -");
            System.out.println("  Name: " + roomTypeDto.getTypeName());
            System.out.println("  Max Adults: " + roomTypeDto.getMaxNoOfAdults());
            System.out.println("  Price Per Person: " + roomTypeDto.getPerPersonPrice());
            System.out.println("  Max Children: " + roomTypeDto.getMaxNoOfAdults());
        });


        // Fetch or create a Hotel
        Hotel hotel = hotelRepo.findByHotelName(contractDto.getHotelName())
                               .orElseGet(() -> {
                                   System.out.println("Hotel not found. Creating new Hotel with name: " + contractDto.getHotelName());
                                   Hotel newHotel = HotelMapper.dtoToEntity(new HotelDto(contractDto.getHotelName()));
                                   Hotel savedHotel = hotelRepo.save(newHotel);
                                   System.out.println("Saved new Hotel with id: " + savedHotel.getHotelId() + ", name: " + savedHotel.getHotelName());
                                   return savedHotel;
                               });

        System.out.println("Using Hotel with id: " + hotel.getHotelId() + ", name: " + hotel.getHotelName());

        // Convert DTO to Contract entity using HotelContractMapper
        Contract contract = HotelContractMapper.dtoToEntity(contractDto, hotel);
        System.out.println("Mapped Contract with id: " + (contract.getContractId() != null ? contract.getContractId() : "null")
                                   + ", startDate: " + contract.getValidFrom()
                                   + ", endDate: " + contract.getValidTo());

        // Save the Contract entity to the database
        Contract savedContract = contractRepo.save(contract);
        System.out.println("Saved Contract with id: " + savedContract.getContractId()
                                   + ", startDate: " + savedContract.getValidFrom()
                                   + ", endDate: " + savedContract.getValidTo());

        // Convert RoomTypeDtos to RoomType entities and associate them with the saved Contract
        List<RoomType> roomTypes = contractDto.getRoomTypeList().stream()
                                              .map(roomTypeDto -> {
                                                  RoomType roomType = RoomTypeMapper.dtoToEntity(roomTypeDto, savedContract);
                                                  System.out.println("Mapped RoomType with name: " + roomType.getTypeName()
                                                                             + ", capacity: " + roomType.getMaxNoAdults() +"Max" + roomType.getPerPersonPrice());
                                                  return roomType;
                                              })
                                              .collect(Collectors.toList());

        // Save all RoomType entities to the database
        List<RoomType> savedRoomTypes = roomTypeRepo.saveAll(roomTypes);
        System.out.println("Saved RoomTypes count: " + savedRoomTypes.size());
        savedRoomTypes.forEach(rt ->
                                       System.out.println("RoomType id: " + rt.getRoomTypeId()
                                                                  + ", name: " + rt.getTypeName()
                                                                  + ", capacity: " + rt.getMaxNoAdults()));

        // Convert the saved Contract and RoomTypes back to a DTO and return
        HotelContractDto resultDto = HotelContractMapper.entityToDto(savedContract, savedRoomTypes);
        System.out.println("Returning HotelContractDto with hotelName: " + resultDto.getHotelName()
                                   + ", roomTypeList size: " + resultDto.getRoomTypeList().size());
        return resultDto;
    }

    public List<HotelContractDto> getContracts() {
        System.out.println("Fetching all contracts...");

        // Fetch all contracts
        List<Contract> contracts = contractRepo.findAll();
        System.out.println("Fetched Contracts count: " + contracts.size());

        // Map each Contract to HotelContractDto
        List<HotelContractDto> hotelContractDto = contracts.stream()
                                                           .map(contract -> {
                                                               System.out.println("Mapping Contract with id: " + contract.getContractId()
                                                                                          + ", startDate: " + contract.getValidFrom()
                                                                                          + ", endDate: " + contract.getValidTo());
                                                               HotelContractDto dto = HotelContractMapper.entityToDto(contract, contract.getRoomTypeList());
                                                               System.out.println("Mapped to HotelContractDto with hotelName: " + dto.getHotelName()
                                                                                          + ", roomTypeList size: " + dto.getRoomTypeList().size());
                                                               return dto;
                                                           })
                                                           .collect(Collectors.toList());

        System.out.println("Returning list of HotelContractDtos with size: " + hotelContractDto.size());
        return hotelContractDto; // Return the list of DTOs
    }

    public void deleteContract( Long id )
    {
        contractRepo.deleteById( id );
    }

    public HotelDto findByHotelName(String hotelName) {
        Hotel hotel = hotelRepo.findByHotelName(hotelName)
                               .orElseThrow(() -> new RuntimeException("Hotel not found with name: " + hotelName));
        return new HotelDto(hotel.getHotelName());
    }
}
