import java.util.*;


class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 1);
        availability.put("Double Room", 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, -1);
    }

    public void decrement(String type) {
        availability.put(type, getAvailability(type) - 1);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }
}


class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}


class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {

        try {
            // Step 1: Validate (Fail-Fast)
            BookingValidator.validate(r, inventory);

            // Step 2: Proceed only if valid
            inventory.decrement(r.getRoomType());

            System.out.println("✅ Booking confirmed for " + r.getGuestName());

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("❌ Booking failed: " + e.getMessage());
        }
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Test cases
        Reservation valid = new Reservation("Alice", "Single Room");
        Reservation invalidRoom = new Reservation("Bob", "Luxury Room");
        Reservation noAvailability = new Reservation("Charlie", "Single Room");
        Reservation emptyName = new Reservation("", "Double Room");

        service.processReservation(valid);           // success
        service.processReservation(invalidRoom);     // invalid type
        service.processReservation(noAvailability);  // no stock
        service.processReservation(emptyName);       // invalid input

        System.out.println("\nSystem remains stable after errors.");
    }
}