package com.Mycompany.TicketManagement.service;

import com.Mycompany.TicketManagement.dto.ConfigurationDto;
import com.Mycompany.TicketManagement.dto.TicketPoolStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TicketSystemService {

    private final ConfigurationService configurationService;
    private final TicketService ticketService;

    @Autowired
    public TicketSystemService(ConfigurationService configurationService, TicketService ticketService) {
        this.configurationService = configurationService;
        this.ticketService = ticketService;
    }

    private ScheduledExecutorService vendorExecutor;
    private ScheduledExecutorService customerExecutor;
    private boolean systemRunning = false;

    public synchronized void startSystem(Long configurationId) {
        if (systemRunning) {
            System.out.println("System is already running");
            return;
        }

        Optional<ConfigurationDto> configOpt = configurationService.getConfigurationById(configurationId);
        if (configOpt.isEmpty()) {
            throw new RuntimeException("Configuration not found with ID: " + configurationId);
        }

        ConfigurationDto config = configOpt.get();

        // Activate the configuration
        configurationService.activateConfiguration(configurationId);

        // Initialize ticket pool
        ticketService.clearAllTickets();
        ticketService.addTickets(config.getTotalTickets());

        // Start vendor thread (adds tickets periodically)
        vendorExecutor = Executors.newSingleThreadScheduledExecutor();
        vendorExecutor.scheduleAtFixedRate(
                () -> {
                    try {
                        TicketPoolStatusDto status = ticketService.getTicketPoolStatus();
                        if (status.getAvailableTickets() + 5 <= config.getMaxTicketCapacity()) {
                            ticketService.addTickets(5);
                        }
                    } catch (Exception e) {
                        System.err.println("Error in vendor thread: " + e.getMessage());
                    }
                },
                0,
                config.getTicketReleaseRateMs(),
                TimeUnit.MILLISECONDS
        );

        // Start customer thread (purchases tickets periodically)
        customerExecutor = Executors.newSingleThreadScheduledExecutor();
        customerExecutor.scheduleAtFixedRate(
                () -> {
                    try {
                        String customerId = "CUSTOMER-" + System.currentTimeMillis();
                        ticketService.purchaseTicket(customerId);
                    } catch (Exception e) {
                        System.err.println("Error in customer thread: " + e.getMessage());
                    }
                },
                0,
                config.getCustomerRetrievalRateMs(),
                TimeUnit.MILLISECONDS
        );

        systemRunning = true;
        System.out.println("Ticket system started with configuration ID: " + configurationId);
    }

    public synchronized void stopSystem() {
        if (!systemRunning) {
            System.out.println("System is not running");
            return;
        }

        if (vendorExecutor != null) {
            vendorExecutor.shutdown();
        }
        if (customerExecutor != null) {
            customerExecutor.shutdown();
        }

        systemRunning = false;
        System.out.println("Ticket system stopped");
    }

    public TicketPoolStatusDto getSystemStatus() {
        TicketPoolStatusDto status = ticketService.getTicketPoolStatus();
        status.setSystemRunning(systemRunning);

        Optional<ConfigurationDto> activeConfig = configurationService.getActiveConfiguration();
        if (activeConfig.isPresent()) {
            status.setMaxCapacity(activeConfig.get().getMaxTicketCapacity());
        }

        return status;
    }

    public boolean isSystemRunning() {
        return systemRunning;
    }
}
