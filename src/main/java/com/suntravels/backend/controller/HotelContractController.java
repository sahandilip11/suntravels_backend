package com.suntravels.backend.controller;

import com.suntravels.backend.dto.HotelContractDto;
import com.suntravels.backend.service.HotelContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing hotel contracts.
 * Provides endpoints for adding, retrieving, and deleting contracts.
 */
@RestController
@RequestMapping("/api/v1/contracts")
public class HotelContractController {

    private final HotelContractService hotelContractService;

    /**
     * Constructor for injecting the HotelContractService.
     *
     * @param hotelContractService the service handling hotel contract logic
     */
    @Autowired
    public HotelContractController(HotelContractService hotelContractService) {
        this.hotelContractService = hotelContractService;
    }

    /**
     * Adds a new hotel contract.
     *
     * @param contractDto the data transfer object representing the hotel contract
     * @return the saved {@link HotelContractDto} object
     */
    @PostMapping
    public HotelContractDto addContract(@RequestBody HotelContractDto contractDto) {
        return hotelContractService.addContract(contractDto);
    }

    /**
     * Retrieves all hotel contracts.
     *
     * @return a {@link ResponseEntity} containing a list of {@link HotelContractDto} objects
     */
    @GetMapping
    public ResponseEntity<List<HotelContractDto>> getContracts() {
        return new ResponseEntity<>(hotelContractService.getContracts(), HttpStatus.OK);
    }

    /**
     * Deletes a hotel contract by ID.
     *
     * @param id the ID of the contract to be deleted
     */
    @DeleteMapping("/{id}")
    public void DeleteContract(@PathVariable Long id) {
        hotelContractService.deleteContract(id);
    }
}
