import java.util.*;


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


class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // remove in FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        availability.put(type, getAvailability(type) - 1);
    }
}


class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per type
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Track ALL used IDs (global uniqueness)
    private Set<String> usedRoomIds = new HashSet<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }


    public void processReservation(Reservation r) {

        String type = r.getRoomType();

        System.out.println("\nProcessing request for " + r.getGuestName());

        // Step 1: Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("❌ No rooms available for " + type);
            return;
        }

        // Step 2: Generate unique room ID
        String roomId = generateRoomId(type);

        // Step 3: Store allocation
        allocatedRooms.putIfAbsent(type, new HashSet<>());
        allocatedRooms.get(type).add(roomId);

        // Step 4: Mark ID as used
        usedRoomIds.add(roomId);

        // Step 5: Update inventory (atomic with allocation)
        inventory.decrement(type);

        // Step 6: Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + type);
        System.out.println("Room ID: " + roomId);
    }


    private String generateRoomId(String type) {
        String prefix = type.substring(0, 2).toUpperCase(); // SI, DO, SU
        String roomId;

        do {
            roomId = prefix + (int)(Math.random() * 1000);
        } while (usedRoomIds.contains(roomId)); // ensure uniqueness

        return roomId;
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail

        // Process queue (FIFO)
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        System.out.println("\nAll requests processed.");
    }
}