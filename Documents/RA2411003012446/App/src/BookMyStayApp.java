import java.util.*;


class Reservation {

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}


class BookingHistory {

    private List<Reservation> history = new ArrayList<>();


    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }


    public List<Reservation> getAllReservations() {
        return history;
    }
}


class BookingReportService {


    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n=== Booking History ===");

        for (Reservation r : reservations) {
            r.display();
        }
    }

    public void generateSummaryReport(List<Reservation> reservations) {

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : reservations) {
            countMap.put(
                    r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\n=== Booking Summary Report ===");

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + " → Bookings: " + entry.getValue());
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from BookingService UC6)
        history.addReservation(new Reservation("Alice", "Single Room", "SI101"));
        history.addReservation(new Reservation("Bob", "Suite Room", "SU201"));
        history.addReservation(new Reservation("Charlie", "Single Room", "SI102"));

        // Initialize reporting service
        BookingReportService reportService = new BookingReportService();

        // Admin views booking history
        reportService.displayAllBookings(history.getAllReservations());

        // Admin generates summary report
        reportService.generateSummaryReport(history.getAllReservations());

        System.out.println("\nReporting completed. No data modified.");
    }
}