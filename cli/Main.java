import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config;

        System.out.println("Load existing configuration = press 1 \n" +
                           "New configuration = press 2 ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: config = ConfigurationManager.loadConfiguration("config.txt");
            break;

            case 2: config = new Configuration();
                System.out.println("Enter total tickets: ");
                int totalTickets = scanner.nextInt();
                config.setTotalTickets(totalTickets);

                System.out.println("Enter ticket release rate (ms): ");
                int ticketReleaseRate = scanner.nextInt();
                config.setTicketReleaseRateMs(ticketReleaseRate);

                System.out.println("Enter customer retrieval rate (ms): ");
                int customerRetrievalRate = scanner.nextInt();
                config.setCustomerRetrievalRateMs(customerRetrievalRate);

                System.out.println("Enter max ticket capacity: ");
                int maxTicketCapacity = scanner.nextInt();
                config.setMaxTicketCapacity(maxTicketCapacity);

                ConfigurationManager.saveConfiguration(config, "config.txt");
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }



        TicketPool ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());
        Thread vendorThread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRateMs()));
        Thread customerThread = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRateMs()));

        vendorThread.start();
        customerThread.start();
    }
}
