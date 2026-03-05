public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version 2.1 ");
        System.out.println("=================================\n");

        System.out.println("Hotel Room Initialization\n");

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println(single.getRoomType() + ":");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println();

        System.out.println(dbl.getRoomType() + ":");
        dbl.displayDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println();

        System.out.println(suite.getRoomType() + ":");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}

abstract class Room {

    protected int beds;
    protected int size;
    protected double pricePerNight;

    public Room(int beds, int size, double pricePerNight) {
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    public abstract String getRoomType();

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500.0);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}