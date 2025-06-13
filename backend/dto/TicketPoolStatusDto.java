package com.Mycompany.TicketManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPoolStatusDto {

    private int availableTickets;
    private int totalTickets;
    private int purchasedTickets;
    private int maxCapacity;
    private boolean isSystemRunning;


}