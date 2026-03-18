import java.io.*;
import java.util.*;


public class BookMyStayApp {

    public static void main(String[] args) {

        // 🔹 Load previous state
        SystemState state = PersistenceService.loadState();

        Map<String, Integer> inventory = state.getInventory();
        List<Reservation> bookingHistory = state.getBookingHistory();

        // 🔹 Initialize inventory if first run
        if (inventory.isEmpty()) {
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }

        // 🔹 Display inventory
        System.out.println("\n--- CURRENT INVENTORY ---");
        inventory.forEach((k, v) -> System.out.println(k + " Rooms: " + v));

        // 🔹 Display booking history
        System.out.println("\n--- BOOKING HISTORY ---");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // 🔹 Simulate booking
        System.out.println("\n--- NEW BOOKING ---");
        String roomType = "Single";

        if (inventory.get(roomType) > 0) {
            String reservationId = "RES" + (bookingHistory.size() + 1);

            Reservation reservation = new Reservation(reservationId, roomType);
            bookingHistory.add(reservation);

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("✅ Booking Confirmed: " + reservation);
        } else {
            System.out.println("❌ No rooms available!");
        }

        // 🔹 Save state before exit
        state.setInventory(inventory);
        state.setBookingHistory(bookingHistory);

        PersistenceService.saveState(state);

        System.out.println("\n💾 State saved. Application exiting...");
    }
}

/* =========================
   Persistence Service
   ========================= */
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public static void saveState(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("✅ System state saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("✅ System state restored successfully.");
            return state;
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("❌ Error loading state: " + e.getMessage());
        }
        return new SystemState();
    }
}

/* =========================
   System State Class
   ========================= */
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;
    private List<Reservation> bookingHistory;

    public SystemState() {
        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setBookingHistory(List<Reservation> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
}

/* =========================
   Reservation Class
   ========================= */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Room Type: " + roomType;
    }
}