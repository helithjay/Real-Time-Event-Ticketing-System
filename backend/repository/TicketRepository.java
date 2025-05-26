

package com.Mycompany.TicketManagement.repository;

import com.Mycompany.TicketManagement.entity.Ticket;
import com.Mycompany.TicketManagement.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(TicketStatus status);

    int countByStatus(TicketStatus status);

    // Fixed query - using LIMIT to ensure only one result is returned
    @Query(value = "SELECT * FROM tickets WHERE status = 'AVAILABLE' ORDER BY created_at ASC, id ASC LIMIT 1", nativeQuery = true)
    Optional<Ticket> findFirstAvailableTicket();

    // Alternative Spring Data JPA approach (if you prefer)
    // Optional<Ticket> findFirstByStatusOrderByCreatedAtAscIdAsc(TicketStatus status);

    List<Ticket> findByCustomerId(String customerId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'AVAILABLE'")
    int countAvailableTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.status = 'PURCHASED'")
    int countPurchasedTickets();
}