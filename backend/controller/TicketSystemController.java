package com.Mycompany.TicketManagement.controller;

import com.Mycompany.TicketManagement.dto.SystemControlDto;
import com.Mycompany.TicketManagement.dto.TicketPoolStatusDto;
import com.Mycompany.TicketManagement.service.TicketSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "*")
public class TicketSystemController {

    private final TicketSystemService ticketSystemService;

    @Autowired
    public TicketSystemController(TicketSystemService ticketSystemService) {
        this.ticketSystemService = ticketSystemService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSystem(@RequestBody SystemControlDto controlDto) {
        try {
            ticketSystemService.startSystem(controlDto.getConfigurationId());
            return ResponseEntity.ok("System started successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error starting system: " + e.getMessage());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        ticketSystemService.stopSystem();
        return ResponseEntity.ok("System stopped successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<TicketPoolStatusDto> getSystemStatus() {
        TicketPoolStatusDto status = ticketSystemService.getSystemStatus();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/running")
    public ResponseEntity<Boolean> isSystemRunning() {
        boolean running = ticketSystemService.isSystemRunning();
        return ResponseEntity.ok(running);
    }
}
