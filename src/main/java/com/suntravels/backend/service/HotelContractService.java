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

/**
 * Service layer for managing hotel contracts, hotels, and room types.
 *
 * <p>This service encapsulates the business logic for creating, retrieving, and deleting hotel contracts
 * and their associated data.</p>
 */
@Service
public class HotelContractService {

    private final ContractRepo contractRepo;
    private final HotelRepo hotelRepo;
    private final RoomTypeRepo roomTypeRepo;

    /**
     * Constructor to inject required dependencies.
     *
     * @param contractRepo The repository for managing {@link Contract} entities.
     * @param hotelRepo    The repository for managing {@link Hotel} entities.
     * @param roomTypeRepo The repository for managing {@link RoomType} entities.
     */
    @Autowired
    public HotelContractService(ContractRepo contractRepo, HotelRepo hotelRepo, RoomTypeRepo roomTypeRepo) {
        this.contractRepo = contractRepo;
        this.hotelRepo = hotelRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    /**
     * Adds a new hotel contract, including the associated hotel and room types.
     *
     * @param contractDto The DTO containing the contract details.
     * @return The saved contract as a {@link HotelContractDto}.
     */
    public HotelContractDto addContract(HotelContractDto contractDto) {

        // Fetch or create a Hotel
        Hotel hotel = hotelRepo.findByHotelName(contractDto.getHotelName())
                               .orElseGet(() -> {
                                   Hotel newHotel = HotelMapper.dtoToEntity(new HotelDto(contractDto.getHotelName()));
                                   return hotelRepo.save(newHotel);
                               });

        // Convert DTO to Contract entity using HotelContractMapper
        Contract contract = HotelContractMapper.dtoToEntity(contractDto, hotel);

        // Save the Contract entity to the database
        Contract savedContract = contractRepo.save(contract);

        // Convert RoomTypeDtos to RoomType entities and associate them with the saved Contract
        List<RoomType> roomTypes = contractDto.getRoomTypeList().stream()
                                              .map(roomTypeDto -> RoomTypeMapper.dtoToEntity(roomTypeDto, savedContract))
                                              .collect(Collectors.toList());

        // Save all RoomType entities to the database
        List<RoomType> savedRoomTypes = roomTypeRepo.saveAll(roomTypes);

        // Convert the saved Contract and RoomTypes back to a DTO and return
        return HotelContractMapper.entityToDto(savedContract, savedRoomTypes);
    }

    /**
     * Retrieves all hotel contracts.
     *
     * @return A list of {@link HotelContractDto} representing all contracts.
     */
    public List<HotelContractDto> getContracts() {

        // Fetch all contracts
        List<Contract> contracts = contractRepo.findAll();

        // Map each Contract to HotelContractDto
        return contracts.stream()
                        .map(contract -> HotelContractMapper.entityToDto(contract, contract.getRoomTypeList()))
                        .collect(Collectors.toList());
    }

    /**
     * Deletes a hotel contract by its ID.
     *
     * @param id The ID of the contract to delete.
     */
    public void deleteContract(Long id) {
        contractRepo.deleteById(id);
    }

    /**
     * Finds a hotel by its name.
     *
     * @param hotelName The name of the hotel to search for.
     * @return The found hotel as a {@link HotelDto}.
     * @throws RuntimeException if no hotel is found with the given name.
     */
    public HotelDto findByHotelName(String hotelName) {
        Hotel hotel = hotelRepo.findByHotelName(hotelName)
                               .orElseThrow(() -> new RuntimeException("Hotel not found with name: " + hotelName));
        return new HotelDto(hotel.getHotelName());
    }
}
