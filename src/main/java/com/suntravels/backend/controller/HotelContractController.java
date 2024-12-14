package com.suntravels.backend.controller;
import com.suntravels.backend.dto.HotelContractDto;
import com.suntravels.backend.service.HotelContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts")
public class HotelContractController {

    private final HotelContractService hotelContractService;

    @Autowired
    public HotelContractController(HotelContractService hotelContractService)
    {
        this.hotelContractService = hotelContractService;
    }

    @PostMapping
    public HotelContractDto addContract( @RequestBody HotelContractDto contractDto) {
        return hotelContractService.addContract(contractDto);
    }

    @GetMapping
    public ResponseEntity<List<HotelContractDto>> getContracts()
    {
        return new ResponseEntity<>( hotelContractService.getContracts(), HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public void DeleteContract(@PathVariable Long id){
        hotelContractService.deleteContract(id);
    }
}
