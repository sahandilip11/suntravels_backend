package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


import java.util.List;

@Entity
@Table(name = "Hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id", nullable = false, updatable = false)
    private Long hotelId;

    @Column(name = "hotel_name", nullable = false, length = 50)
    @NotBlank(message = "Hotel name cannot be blank")
    private String hotelName;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contractList;

    public Hotel()
    {
    }

    public Hotel( Long hotelId, String hotelName, List<Contract> contractList )
    {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.contractList = contractList;
    }

    public Long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId( Long hotelId )
    {
        this.hotelId = hotelId;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName( String hotelName )
    {
        this.hotelName = hotelName;
    }

    public List<Contract> getContractList()
    {
        return contractList;
    }

    public void setContractList( List<Contract> contractList )
    {
        this.contractList = contractList;
    }
}
