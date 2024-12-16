package com.suntravels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity class representing a contract in the system.
 * A contract is associated with a specific hotel and contains details about room types, markup rate, and validity dates.
 */
@Entity
@Table(name = "Contract")
public class Contract {

    /**
     * The unique identifier for the contract.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id", nullable = false, updatable = false)
    private Long contractId;

    /**
     * The starting date from which the contract is valid.
     */
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    /**
     * The ending date until which the contract is valid.
     */
    @Column(name = "valid_to")
    private LocalDate validTo;

    /**
     * The markup rate applied for the contract, represented as a decimal value.
     */
    @Column(name = "mark_up_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal markupRate;

    /**
     * The hotel associated with this contract.
     * This field is not serialized to JSON.
     */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    /**
     * The list of room types associated with this contract.
     * Each room type belongs to this contract and will be cascaded during persistence.
     */
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomType> roomTypeList;

    /**
     * Default constructor for creating an empty {@link Contract} instance.
     */
    public Contract() {
    }

    /**
     * Constructor for creating a {@link Contract} instance with the specified parameters.
     *
     * @param contractId   the unique identifier for the contract
     * @param validFrom    the starting date of the contract's validity
     * @param validTo      the ending date of the contract's validity
     * @param markupRate   the markup rate applied to the contract
     * @param hotel        the hotel associated with this contract
     * @param roomTypeList the list of room types included in this contract
     */
    public Contract(Long contractId, LocalDate validFrom, LocalDate validTo, BigDecimal markupRate, Hotel hotel, List<RoomType> roomTypeList) {
        this.contractId = contractId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.markupRate = markupRate;
        this.hotel = hotel;
        this.roomTypeList = roomTypeList;
    }

    /**
     * Gets the unique identifier for the contract.
     *
     * @return the contract ID
     */
    public Long getContractId() {
        return contractId;
    }

    /**
     * Sets the unique identifier for the contract.
     *
     * @param contractId the contract ID to set
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    /**
     * Gets the starting date of the contract's validity.
     *
     * @return the validity start date
     */
    public LocalDate getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the starting date of the contract's validity.
     *
     * @param validFrom the validity start date to set
     */
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Gets the ending date of the contract's validity.
     *
     * @return the validity end date
     */
    public LocalDate getValidTo() {
        return validTo;
    }

    /**
     * Sets the ending date of the contract's validity.
     *
     * @param validTo the validity end date to set
     */
    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    /**
     * Gets the markup rate applied for the contract.
     *
     * @return the markup rate
     */
    public BigDecimal getMarkupRate() {
        return markupRate;
    }

    /**
     * Sets the markup rate applied for the contract.
     *
     * @param markupRate the markup rate to set
     */
    public void setMarkupRate(BigDecimal markupRate) {
        this.markupRate = markupRate;
    }

    /**
     * Gets the hotel associated with this contract.
     *
     * @return the associated hotel
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Sets the hotel associated with this contract.
     *
     * @param hotel the associated hotel to set
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Gets the list of room types associated with this contract.
     *
     * @return the list of room types
     */
    public List<RoomType> getRoomTypeList() {
        return roomTypeList;
    }

    /**
     * Sets the list of room types associated with this contract.
     *
     * @param roomTypeList the list of room types to set
     */
    public void setRoomTypeList(List<RoomType> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }
}
