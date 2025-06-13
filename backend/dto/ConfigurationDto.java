package com.Mycompany.TicketManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDto {

    private Long id;

    private Integer totalTickets;

    private Integer ticketReleaseRateMs;

    private Integer customerRetrievalRateMs;

    private Integer maxTicketCapacity;

    private String configName;

    private boolean isActive;


}