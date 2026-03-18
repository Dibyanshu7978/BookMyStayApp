import java.util.*;


class AddOnService {

    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}


class AddOnServiceManager {

    // One-to-Many mapping
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();


    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getName() + " to " + reservationId);
    }


    public double calculateTotalServiceCost(String reservationId) {

        List<AddOnService> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        double total = 0;
        for (AddOnService s : services) {
            total += s.getPrice();
        }

        return total;
    }


    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation: " + reservationId);

        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getPrice() + ")");
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalServiceCost(reservationId));
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        // Assume these reservation IDs came from BookingService (UC6)
        String reservation1 = "SI101";
        String reservation2 = "SU202";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa Access", 1200);
        AddOnService wifi = new AddOnService("Premium WiFi", 300);

        // Guest selects services
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, wifi);

        manager.addService(reservation2, spa);

        // Display services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        System.out.println("\nCore booking & inventory remain unchanged.");
    }
}