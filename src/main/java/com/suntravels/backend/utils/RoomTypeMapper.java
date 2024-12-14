package com.suntravels.backend.utils;


import com.suntravels.backend.dto.RoomTypeDto;
import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.RoomType;

public class RoomTypeMapper {

    // Converts RoomTypeDto to RoomType entity
    public static RoomType dtoToEntity(RoomTypeDto roomTypeDto, Contract contract) {
        RoomType roomType = new RoomType();

        roomType.setTypeName(roomTypeDto.getTypeName());
        roomType.setPerPersonPrice(roomTypeDto.getPerPersonPrice());
        roomType.setNoOfRooms(roomTypeDto.getNoOfRooms());
        roomType.setMaxNoAdults(roomTypeDto.getMaxNoOfAdults());
        roomType.setContract(contract); // Associate the RoomType with the Contract
        return roomType;
    }

    // Converts RoomType entity to RoomTypeDto
    public static RoomTypeDto entityToDto(RoomType roomType) {
        RoomTypeDto roomTypeDto = new RoomTypeDto();

        roomTypeDto.setContractId(roomType.getContract().getContractId());
        roomTypeDto.setTypeName(roomType.getTypeName());
        roomTypeDto.setPerPersonPrice(roomType.getPerPersonPrice());
        roomTypeDto.setNoOfRooms(roomType.getNoOfRooms());
        roomTypeDto.setMaxNoOfAdults(roomType.getMaxNoAdults());
        return roomTypeDto;
    }
}


