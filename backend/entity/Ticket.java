package com.Mycompany.TicketManagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.AVAILABLE;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;

    @Column(name = "customer_id")
    private String customerId;


}
