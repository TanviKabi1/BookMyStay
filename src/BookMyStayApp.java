import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    public static void main(String[] args) {

        // ===========================
        // UC1: Welcome Message
        // ===========================
        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version 3.1 ");
        System.out.println("=================================\n");

        // ===========================
        // UC2: Room Initialization
        // ===========================
        System.out.println("Hotel Room Initialization\n");

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println(single.getRoomType() + ":");
        single.displayDetails();
        System.out.println();

        System.out.println(dbl.getRoomType() + ":");
        dbl.displayDetails();
        System.out.println();

        System.out.println(suite.getRoomType() + ":");
        suite.displayDetails();
        System.out.println();

        // ===========================
        // UC3: Centralized Room Inventory
        // ===========================
        System.out.println("Hotel Room Inventory\n");

        RoomInventory inventory = new RoomInventory();

        System.out.println("Current Room Availability:");
        System.out.println("Single Rooms Available: " + inventory.getAvailability("Single"));
        System.out.println("Double Rooms Available: " + inventory.getAvailability("Double"));
        System.out.println("Suite Rooms Available: " + inventory.getAvailability("Suite"));

        // Example of updating inventory
        System.out.println("\nUpdating inventory: Booking 1 Single Room...");
        inventory.updateAvailability("Single", inventory.getAvailability("Single") - 1);

        System.out.println("\nUpdated Room Availability:");
        System.out.println("Single Rooms Available: " + inventory.getAvailability("Single"));
        System.out.println("Double Rooms Available: " + inventory.getAvailability("Double"));
        System.out.println("Suite Rooms Available: " + inventory.getAvailability("Suite"));
    }
}

// ===========================
// Room Abstract Class & Subclasses
// ===========================
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
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
    public String getRoomType() { return "Suite Room"; }
}

// ===========================
// UC3: Room Inventory Class
// ===========================


class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

// ===========================
// UC4: Room Search & Availability Check
// ===========================

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Displays only available rooms with details
    public void showAvailableRooms(Room[] rooms) {
        System.out.println("\nAvailable Rooms for Guests:\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(getRoomKey(room));
            if (available > 0) {
                System.out.println(room.getRoomType() + " (Available: " + available + "):");
                room.displayDetails();
                System.out.println();
            }
        }
    }

    // Map Room object to inventory key
    private String getRoomKey(Room room) {
        if (room instanceof SingleRoom) return "Single";
        else if (room instanceof DoubleRoom) return "Double";
        else if (room instanceof SuiteRoom) return "Suite";
        else return "";
    }
}