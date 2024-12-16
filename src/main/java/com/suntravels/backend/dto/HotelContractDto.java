package com.suntravels.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HotelContractDto
{
    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName( String hotelName )
    {
        this.hotelName = hotelName;
    }

    public LocalDate getValidFrom()
    {
        return validFrom;
    }

    public void setValidFrom( LocalDate validFrom )
    {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo()
    {
        return validTo;
    }

    public void setValidTo( LocalDate validTo )
    {
        this.validTo = validTo;
    }

    public BigDecimal getMarkupRate()
    {
        return markupRate;
    }

    public void setMarkupRate( BigDecimal markupRate )
    {
        this.markupRate = markupRate;
    }

    public List<RoomTypeDto> getRoomTypeList()
    {
        return roomTypeList;
    }

    public void setRoomTypeList( List<RoomTypeDto> roomTypeList )
    {
        this.roomTypeList = roomTypeList;
    }

    public HotelContractDto()
    {
    }

    public HotelContractDto( Long contractId, String hotelName, LocalDate validFrom, LocalDate validTo, BigDecimal markupRate, List<RoomTypeDto> roomTypeList )
    {
        this.contractId = contractId;
        this.hotelName = hotelName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.markupRate = markupRate;
        this.roomTypeList = roomTypeList;
    }

    public Long getContractId()
    {
        return contractId;
    }

    public void setContractId( Long contractId )
    {
        this.contractId = contractId;
    }

    private Long contractId;
    private String hotelName;
    private LocalDate validFrom;
    private LocalDate validTo;
    private BigDecimal markupRate;
    private List<RoomTypeDto> roomTypeList;

}
