package com.suntravels.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for representing hotel information.
 * This class is used to transfer hotel data between layers of the application.
 */
public class HotelDto {

    /**
     * The unique identifier of the hotel.
     */
    private Long hotelId;

    /**
     * The name of the hotel. Must not be blank.
     */
    @NotBlank(message = "Hotel name must not be blank")
    private String hotelName;

    /**
     * Default constructor for creating an empty {@link HotelDto} instance.
     */
    public HotelDto() {
    }

    /**
     * Constructor for creating a {@link HotelDto} instance with the specified hotel name.
     *
     * @param hotelName the name of the hotel
     */
    public HotelDto(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Constructor for creating a {@link HotelDto} instance with the specified hotel ID and name.
     *
     * @param hotelName the name of the hotel
     * @param hotelId   the unique identifier of the hotel
     */
    public HotelDto(String hotelName, Long hotelId) {
        this.hotelName = hotelName;
        this.hotelId = hotelId;
    }

    /**
     * Gets the unique identifier of the hotel.
     *
     * @return the hotel ID
     */
    public Long getHotelId() {
        return hotelId;
    }

    /**
     * Sets the unique identifier of the hotel.
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
}
