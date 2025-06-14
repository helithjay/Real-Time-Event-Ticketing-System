import java.util.Vector;

public class TicketPool {
    private final Vector<String> tickets = new Vector<>();
    private final int maxTicketCapacity;

    public TicketPool(int initialTickets, int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        for (int i = 0; i < initialTickets; i++) {
            tickets.add(" Ticket " + (i + 1));
        }
    }

    public synchronized void addTickets(int count) {
        while (tickets.size() + count > maxTicketCapacity) {
            try {
                wait(); // Wait if adding tickets exceeds capacity
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 0; i < count; i++) {
            tickets.add(" Ticket " + (tickets.size() + 1));
        }
        System.out.println(count + " tickets added. Current ticket count: " + tickets.size());
        notifyAll(); // Notify waiting customers
    }

    public synchronized void removeTicket() {
        while (tickets.isEmpty()) {
            try {
                wait(); // Wait if no tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        String ticket = tickets.remove(0);
        System.out.println("Ticket " + ticket + " is purchased by a coustomer.  Remaining tickets: " + tickets.size());
        notifyAll(); // Notify waiting vendors
    }
}
