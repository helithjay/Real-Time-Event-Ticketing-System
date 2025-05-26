package com.Mycompany.TicketManagement.service;

import com.Mycompany.TicketManagement.dto.TicketDto;
import com.Mycompany.TicketManagement.dto.TicketPoolStatusDto;
import com.Mycompany.TicketManagement.entity.Ticket;
import com.Mycompany.TicketManagement.entity.TicketStatus;
import com.Mycompany.TicketManagement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public List<TicketDto> addTickets(int count) {
        List<Ticket> tickets = new java.util.ArrayList<>();

        for (int i = 0; i < count; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketNumber("TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setCreatedAt(LocalDateTime.now());
            tickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);
        System.out.println("Added " + count + " tickets to the pool");

        return savedTickets.stream().map(this::convertToDto).collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public Optional<TicketDto> purchaseTicket(String customerId) {
        Optional<Ticket> availableTicket = ticketRepository.findFirstAvailableTicket();

        if (availableTicket.isPresent()) {
            Ticket ticket = availableTicket.get();
            ticket.setStatus(TicketStatus.PURCHASED);
            ticket.setPurchasedAt(LocalDateTime.now());
            ticket.setCustomerId(customerId);

            Ticket savedTicket = ticketRepository.save(ticket);
            System.out.println("Ticket " + ticket.getTicketNumber() + " purchased by customer " + customerId);

            return Optional.of(convertToDto(savedTicket));
        }

        System.out.println("No available tickets for customer " + customerId);
        return Optional.empty();
    }

    public TicketPoolStatusDto getTicketPoolStatus() {
        int availableTickets = ticketRepository.countAvailableTickets();
        int purchasedTickets = ticketRepository.countPurchasedTickets();
        int totalTickets = availableTickets + purchasedTickets;

        return new TicketPoolStatusDto(
                availableTickets,
                totalTickets,
                purchasedTickets,
                0, // This would come from active configuration
                false // This would come from system status
        );
    }

    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<TicketDto> getAvailableTickets() {
        return ticketRepository.findByStatus(TicketStatus.AVAILABLE)
                .stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<TicketDto> getTicketsByCustomer(String customerId) {
        return ticketRepository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public void clearAllTickets() {
        ticketRepository.deleteAll();
        System.out.println("All tickets cleared from the system");
    }

    private TicketDto convertToDto(Ticket entity) {
        TicketDto dto = new TicketDto();
        dto.setId(entity.getId());
        dto.setTicketNumber(entity.getTicketNumber());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPurchasedAt(entity.getPurchasedAt());
        dto.setCustomerId(entity.getCustomerId());
        return dto;
    }
}
