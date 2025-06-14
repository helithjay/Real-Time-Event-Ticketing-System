public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRateMs;

    public Customer(TicketPool ticketPool, int customerRetrievalRateMs) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRateMs = customerRetrievalRateMs;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.removeTicket();
            try {
                Thread.sleep(customerRetrievalRateMs); // Use milliseconds for delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
