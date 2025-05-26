package com.Mycompany.TicketManagement.controller;

import com.Mycompany.TicketManagement.dto.TicketDto;
import com.Mycompany.TicketManagement.dto.TicketPoolStatusDto;
import com.Mycompany.TicketManagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/add/{count}")
    public ResponseEntity<List<TicketDto>> addTickets(@PathVariable int count) {
        List<TicketDto> tickets = ticketService.addTickets(count);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/purchase")
    public ResponseEntity<TicketDto> purchaseTicket(@RequestParam String customerId) {
        Optional<TicketDto> ticket = ticketService.purchaseTicket(customerId);
        return ticket.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TicketDto>> getAvailableTickets() {
        List<TicketDto> tickets = ticketService.getAvailableTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TicketDto>> getTicketsByCustomer(@PathVariable String customerId) {
        List<TicketDto> tickets = ticketService.getTicketsByCustomer(customerId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status")
    public ResponseEntity<TicketPoolStatusDto> getTicketPoolStatus() {
        TicketPoolStatusDto status = ticketService.getTicketPoolStatus();
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllTickets() {
        ticketService.clearAllTickets();
        return ResponseEntity.noContent().build();
    }
}