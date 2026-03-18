import java.util.LinkedList;
import java.util.Queue;


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

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}


class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }


    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }


    public void displayQueue() {
        System.out.println("\n=== Booking Request Queue ===");

        for (Reservation r : queue) {
            r.display();
        }
    }


    public Reservation getNextRequest() {
        return queue.peek(); // No removal yet
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingQueue bookingQueue = new BookingQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room"));

        // Display queued requests
        bookingQueue.displayQueue();

        System.out.println("\nRequests are stored in arrival order (FIFO).");
        System.out.println("No rooms allocated yet. Inventory unchanged.");
    }
}