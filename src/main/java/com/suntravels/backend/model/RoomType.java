package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "RoomType")
public class RoomType {

    public RoomType( Long roomTypeId, String typeName, BigDecimal perPersonPrice, int noOfRooms, Contract contract, int maxNoAdults )
    {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.perPersonPrice = perPersonPrice;
        this.noOfRooms = noOfRooms;
        this.contract = contract;
        this.maxNoAdults = maxNoAdults;
    }

    public Long getRoomTypeId()
    {
        return roomTypeId;
    }

    public void setRoomTypeId( Long roomTypeId )
    {
        this.roomTypeId = roomTypeId;
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

    public void setPerPersonPrice( BigDecimal perPersonPrice )
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

    public int getMaxNoAdults()
    {
        return maxNoAdults;
    }

    public void setMaxNoAdults( int maxNoAdults )
    {
        this.maxNoAdults = maxNoAdults;
    }

    public Contract getContract()
    {
        return contract;
    }

    public void setContract( Contract contract )
    {
        this.contract = contract;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id", nullable = false, updatable = false)
    private Long roomTypeId;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(name = "per_person_price", nullable = false, precision = 10, scale = 2)
    @Positive(message = "Price must be positive")
    private BigDecimal perPersonPrice;

    @Column(name = "no_of_rooms", nullable = false)
    private int noOfRooms;

    @Column(name = "max_no_of_adults", nullable = false)
    private int maxNoAdults;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    public RoomType()
    {
    }
}
