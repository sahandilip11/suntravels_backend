package com.suntravels.backend.utils;

import com.suntravels.backend.dto.HotelContractDto;
import com.suntravels.backend.dto.RoomTypeDto;
import com.suntravels.backend.model.Contract;
import com.suntravels.backend.model.Hotel;
import com.suntravels.backend.model.RoomType;

import java.util.List;
import java.util.stream.Collectors;

public class HotelContractMapper {

    public static HotelContractDto entityToDto( Contract contract, List<RoomType> roomTypes) {
        HotelContractDto dto = new HotelContractDto();
        dto.setContractId( contract.getContractId() );
        dto.setHotelName(contract.getHotel().getHotelName());
        dto.setValidFrom(contract.getValidFrom());
        dto.setValidTo(contract.getValidTo());
        dto.setMarkupRate(contract.getMarkupRate());

        // Convert RoomType entities to DTOs
        List<RoomTypeDto> roomTypeDtos = roomTypes.stream()
                                                  .map(RoomTypeMapper::entityToDto)
                                                  .collect(Collectors.toList());
        dto.setRoomTypeList(roomTypeDtos);
        return dto;
    }

    public static Contract dtoToEntity(HotelContractDto contractDto, Hotel hotel) {
        Contract contract = new Contract();
        contract.setContractId( contractDto.getContractId() );
        contract.setValidFrom(contractDto.getValidFrom());
        contract.setValidTo(contractDto.getValidTo());
        contract.setMarkupRate(contractDto.getMarkupRate());
        contract.setHotel(hotel);

        return contract;
    }
}
