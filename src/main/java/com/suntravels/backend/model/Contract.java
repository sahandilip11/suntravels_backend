package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table( name = "Contract" )
public class Contract
{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "contract_id", nullable = false, updatable = false )
    private Long contractId;

    @Column( name = "valid_from", nullable = false )
    private LocalDate validFrom;

    @Column( name = "valid_to" )
    private LocalDate validTo;

    @Column( name = "mark_up_rate", nullable = false, precision = 5, scale = 2 )
    private BigDecimal markupRate;

    @JsonIgnore
    @ManyToOne( optional = false )
    @JoinColumn( name = "hotel_id", nullable = false )
    private Hotel hotel;

    @OneToMany( mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<RoomType> roomTypeList;

    public Long getContractId()
    {
        return contractId;
    }

    public void setContractId( Long contractId )
    {
        this.contractId = contractId;
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

    public Hotel getHotel()
    {
        return hotel;
    }

    public void setHotel( Hotel hotel )
    {
        this.hotel = hotel;
    }

    public List<RoomType> getRoomTypeList()
    {
        return roomTypeList;
    }

    public void setRoomTypeList( List<RoomType> roomTypeList )
    {
        this.roomTypeList = roomTypeList;
    }

    public Contract()
    {
    }

    public Contract( Long contractId, LocalDate validFrom, LocalDate validTo, BigDecimal markupRate, Hotel hotel, List<RoomType> roomTypeList )
    {
        this.contractId = contractId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.markupRate = markupRate;
        this.hotel = hotel;
        this.roomTypeList = roomTypeList;
    }
}
