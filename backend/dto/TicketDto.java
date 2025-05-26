package com.Mycompany.TicketManagement.dto;

import com.Mycompany.TicketManagement.entity.TicketStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class TicketDto {

    private Long id;
    private String ticketNumber;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime purchasedAt;
    private String customerId;

    public TicketDto() {
    }

    public TicketDto(Long id, String ticketNumber, TicketStatus status, LocalDateTime createdAt, LocalDateTime purchasedAt, String customerId) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.purchasedAt = purchasedAt;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
