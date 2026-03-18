import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();

        // Initial room counts
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }


    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }


    public void updateAvailability(String roomType, int change) {
        int current = availabilityMap.getOrDefault(roomType, 0);
        availabilityMap.put(roomType, current + change);
    }


    public void displayInventory() {
        System.out.println("=== Room Inventory ===");

        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single Room", -1);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}