package com.suntravels.backend.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RoomTypeDto
{

    public RoomTypeDto()
    {
    }

    public RoomTypeDto( Long contractId, String typeName, BigDecimal perPersonPrice, int noOfRooms, int maxNoOfAdults )
    {
        this.contractId = contractId;
        this.typeName = typeName;
        this.perPersonPrice = perPersonPrice;
        this.noOfRooms = noOfRooms;
        this.maxNoOfAdults = maxNoOfAdults;
    }


    public Long getContractId()
    {
        return contractId;
    }

    public void setContractId( Long contractId )
    {
        this.contractId = contractId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
    }

    public BigDecimal getPerPersonPrice()
    {
        return perPersonPrice;
    }

    public void setPerPersonPrice(BigDecimal perPersonPrice )
    {
        this.perPersonPrice = perPersonPrice;
    }

    public int getNoOfRooms()
    {
        return noOfRooms;
    }

    public void setNoOfRooms( int noOfRooms )
    {
        this.noOfRooms = noOfRooms;
    }

    public int getMaxNoOfAdults()
    {
        return maxNoOfAdults;
    }

    public void setMaxNoOfAdults( int maxNoOfAdults )
    {
        this.maxNoOfAdults = maxNoOfAdults;
    }

    private Long contractId;
    private String typeName;
    private BigDecimal perPersonPrice;
    private int noOfRooms;
    private int maxNoOfAdults;
}
