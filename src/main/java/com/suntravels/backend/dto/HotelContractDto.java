package com.suntravels.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing hotel contracts.
 * This DTO is used to transfer contract data between layers of the application.
 */
public class HotelContractDto {

    private Long contractId;
    private String hotelName;
    private LocalDate validFrom;
    private LocalDate validTo;
    private BigDecimal markupRate;
    private List<RoomTypeDto> roomTypeList;

    /**
     * Default constructor for creating an empty {@link HotelContractDto} instance.
     */
    public HotelContractDto() {
    }

    /**
     * Parameterized constructor for creating a {@link HotelContractDto} instance with specified values.
     *
     * @param contractId   the unique identifier of the contract
     * @param hotelName    the name of the hotel
     * @param validFrom    the start date of the contract's validity
     * @param validTo      the end date of the contract's validity
     * @param markupRate   the markup rate for the contract
     * @param roomTypeList the list of room types associated with the contract
     */
    public HotelContractDto(Long contractId, String hotelName, LocalDate validFrom, LocalDate validTo, BigDecimal markupRate, List<RoomTypeDto> roomTypeList) {
        this.contractId = contractId;
        this.hotelName = hotelName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.markupRate = markupRate;
        this.roomTypeList = roomTypeList;
    }

    /**
     * Gets the unique identifier of the contract.
     *
     * @return the contract ID
     */
    public Long getContractId() {
        return contractId;
    }

    /**
     * Sets the unique identifier of the contract.
     *
     * @param contractId the contract ID to set
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    /**
     * Gets the name of the hotel associated with the contract.
     *
     * @return the hotel name
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Sets the name of the hotel associated with the contract.
     *
     * @param hotelName the hotel name to set
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Gets the start date of the contract's validity.
     *
     * @return the validity start date
     */
    public LocalDate getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the start date of the contract's validity.
     *
     * @param validFrom the validity start date to set
     */
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Gets the end date of the contract's validity.
     *
     * @return the validity end date
     */
    public LocalDate getValidTo() {
        return validTo;
    }

    /**
     * Sets the end date of the contract's validity.
     *
     * @param validTo the validity end date to set
     */
    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    /**
     * Gets the markup rate for the contract.
     *
     * @return the markup rate
     */
    public BigDecimal getMarkupRate() {
        return markupRate;
    }

    /**
     * Sets the markup rate for the contract.
     *
     * @param markupRate the markup rate to set
     */
    public void setMarkupRate(BigDecimal markupRate) {
        this.markupRate = markupRate;
    }

    /**
     * Gets the list of room types associated with the contract.
     *
     * @return the list of room types
     */
    public List<RoomTypeDto> getRoomTypeList() {
        return roomTypeList;
    }

    /**
     * Sets the list of room types associated with the contract.
     *
     * @param roomTypeList the list of room types to set
     */
    public void setRoomTypeList(List<RoomTypeDto> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }
}
