package com.Mycompany.TicketManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemControlDto {

    private Long configurationId;
    private String action; // START, STOP, RESET


}
