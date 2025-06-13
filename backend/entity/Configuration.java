package com.Mycompany.TicketManagement.entity;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "ticket_release_rate_ms")
    private int ticketReleaseRateMs;

    @Column(name = "customer_retrieval_rate_ms")
    private int customerRetrievalRateMs;

    @Column(name = "max_ticket_capacity")
    private int maxTicketCapacity;

    @Column(name = "config_name")
    private String configName;

    @Column(name = "is_active")
    private boolean isActive = false;


}