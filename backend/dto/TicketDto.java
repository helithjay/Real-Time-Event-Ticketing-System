package com.Mycompany.TicketManagement.dto;

import com.Mycompany.TicketManagement.entity.TicketStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private Long id;
    private String ticketNumber;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime purchasedAt;
    private String customerId;


}