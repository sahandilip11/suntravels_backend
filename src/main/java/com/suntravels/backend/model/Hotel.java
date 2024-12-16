package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Entity class representing a hotel in the system.
 * A hotel can have multiple contracts associated with it.
 */
@Entity
@Table(name = "Hotel")
public class Hotel {

    /**
     * The unique identifier for the hotel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id", nullable = false, updatable = false)
    private Long hotelId;

    /**
     * The name of the hotel.
     * This field is mandatory and cannot be blank.
     */
    @Column(name = "hotel_name", nullable = false, length = 50)
    @NotBlank(message = "Hotel name cannot be blank")
    private String hotelName;

    /**
     * The list of contracts associated with the hotel.
     * This field is not serialized to JSON.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contractList;

    /**
     * Default constructor for creating an empty {@link Hotel} instance.
     */
    public Hotel() {
    }

    /**
     * Constructor for creating a {@link Hotel} instance with the specified parameters.
     *
     * @param hotelId     the unique identifier for the hotel
     * @param hotelName   the name of the hotel
     * @param contractList the list of contracts associated with the hotel
     */
    public Hotel(Long hotelId, String hotelName, List<Contract> contractList) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.contractList = contractList;
    }

    /**
     * Gets the unique identifier for the hotel.
     *
     * @return the hotel ID
     */
    public Long getHotelId() {
        return hotelId;
    }

    /**
     * Sets the unique identifier for the hotel.
     *
     * @param hotelId the hotel ID to set
     */
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    /**
     * Gets the name of the hotel.
     *
     * @return the hotel name
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Sets the name of the hotel.
     *
     * @param hotelName the hotel name to set
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Gets the list of contracts associated with the hotel.
     *
     * @return the list of contracts
     */
    public List<Contract> getContractList() {
        return contractList;
    }

    /**
     * Sets the list of contracts associated with the hotel.
     *
     * @param contractList the list of contracts to set
     */
    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
