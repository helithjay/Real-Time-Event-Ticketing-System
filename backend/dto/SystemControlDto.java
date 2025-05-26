package com.Mycompany.TicketManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class SystemControlDto {

    private Long configurationId;
    private String action; // START, STOP, RESET

    public SystemControlDto() {
    }

    public SystemControlDto(Long configurationId, String action) {
        this.configurationId = configurationId;
        this.action = action;
    }

    public Long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

