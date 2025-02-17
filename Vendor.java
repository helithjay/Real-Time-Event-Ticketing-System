package com.ticketingsystem;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRateMs;

    public Vendor(TicketPool ticketPool, int ticketReleaseRateMs) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRateMs = ticketReleaseRateMs;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.addTickets(5); // Fixed count for simplicity
            try {
                Thread.sleep(ticketReleaseRateMs); // Use milliseconds for delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
