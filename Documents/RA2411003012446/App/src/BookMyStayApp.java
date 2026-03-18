 abstract class Room {

        private int beds;
        private double price;
        private String type;

        // Constructor
        public Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        // Method to display room details
        public void displayDetails() {
            System.out.println("Room Type: " + type);
            System.out.println("Beds: " + beds);
            System.out.println("Price per night: ₹" + price);
        }
    }

    /**
     * Single Room implementation
     */
    class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 1500.0);
        }
    }

    /**
     * Double Room implementation
     */
    class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 2500.0);
        }
    }

    /**
     * Suite Room implementation
     */
    class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 5000.0);
        }
    }

    /**
     * Application Entry Point
     * Demonstrates object modeling and simple availability tracking
     */
    public class BookMyStayApp {

        public static void main(String[] args) {

            // Creating room objects (Polymorphism)
            Room single = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suite = new SuiteRoom();

            // Static availability variables
            int singleAvailable = 5;
            int doubleAvailable = 3;
            int suiteAvailable = 2;

            // Displaying details
            System.out.println("=== Hotel Room Availability ===\n");

            single.displayDetails();
            System.out.println("Available: " + singleAvailable + "\n");

            doubleRoom.displayDetails();
            System.out.println("Available: " + doubleAvailable + "\n");

            suite.displayDetails();
            System.out.println("Available: " + suiteAvailable + "\n");

            System.out.println("Application terminated.");
        }
    }
}