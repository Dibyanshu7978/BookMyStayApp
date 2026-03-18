import java.util.*;

class Reservation {

    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public boolean isActive() { return isActive; }

    public void cancel() {
        this.isActive = false;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room: " + roomType +
                " | ID: " + roomId +
                " | Status: " + (isActive ? "ACTIVE" : "CANCELLED"));
    }
}


class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 1);
    }

    public void increment(String type) {
        availability.put(type, availability.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println("Inventory: " + availability);
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public Reservation findByRoomId(String roomId) {
        for (Reservation r : history) {
            if (r.getRoomId().equals(roomId)) {
                return r;
            }
        }
        return null;
    }

    public void displayAll() {
        for (Reservation r : history) {
            r.display();
        }
    }
}


class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelReservation(String roomId) {

        System.out.println("\nProcessing cancellation for Room ID: " + roomId);

        // Step 1: Validate existence
        Reservation r = history.findByRoomId(roomId);

        if (r == null) {
            System.out.println("❌ Reservation does not exist.");
            return;
        }

        // Step 2: Validate already cancelled
        if (!r.isActive()) {
            System.out.println("❌ Reservation already cancelled.");
            return;
        }

        // Step 3: Push to rollback stack
        rollbackStack.push(roomId);

        // Step 4: Restore inventory
        inventory.increment(r.getRoomType());

        // Step 5: Update reservation status
        r.cancel();

        // Step 6: Confirmation
        System.out.println("✅ Reservation cancelled successfully.");
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack (recent cancellations): " + rollbackStack);
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Alice", "Single Room", "SI101");
        history.add(r1);

        // Initialize cancellation service
        CancellationService cancelService = new CancellationService(inventory, history);

        // Valid cancellation
        cancelService.cancelReservation("SI101");

        // Invalid cases
        cancelService.cancelReservation("SI999"); // non-existent
        cancelService.cancelReservation("SI101"); // already cancelled

        // Display system state
        System.out.println("\n=== Booking History ===");
        history.displayAll();

        inventory.display();
        cancelService.displayRollbackStack();
    }
}