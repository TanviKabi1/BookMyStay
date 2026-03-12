import java.io.*;
import java.util.*;

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


class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        List<AddOnService> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
        services.add(service);
        servicesByReservation.put(reservationId, services);
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;
        List<AddOnService> services = servicesByReservation.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) total += s.getCost();
        }
        return total;
    }
}
class Discount {
    private String reservationId;
    private double percent; // e.g., 10.0 for 10%

    public Discount(String reservationId, double percent) {
        this.reservationId = reservationId;
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }

    public String getReservationId() {
        return reservationId;
    }
}

class DiscountManager {
    private Map<String, Discount> discounts;

    public DiscountManager() {
        discounts = new HashMap<>();
    }

    public void applyDiscount(String reservationId, double percent) {
        discounts.put(reservationId, new Discount(reservationId, percent));
    }

    public double calculateDiscountedTotal(double originalTotal, String reservationId) {
        Discount discount = discounts.get(reservationId);
        if (discount != null) {
            return originalTotal * (1 - discount.getPercent() / 100);
        }
        return originalTotal;
    }
}
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {

    // Validate room type
    public static void validateRoomType(String roomType) throws InvalidBookingException {
        if (!roomType.equals("Single Room") &&
                !roomType.equals("Double Room") &&
                !roomType.equals("Suite Room")) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Validate availability
    public static void validateAvailability(int availableRooms) throws InvalidBookingException {
        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available for booking.");
        }
    }

    // Validate add-on cost (example)
    public static void validateAddOnCost(double cost) throws InvalidBookingException {
        if (cost < 0) {
            throw new InvalidBookingException("Add-On cost cannot be negative: " + cost);
        }
    }
}

// ---------- Use Case 9: Error Handling & Validation Demo ----------

class UseCase9Demo {

    // This method simulates bookings with error handling
    public static void demo() {
        System.out.println("\n--- Use Case 9: Error Handling & Validation ---\n");

        try {
            // Valid room
            BookingValidator.validateRoomType("Single Room");
            System.out.println("Room type 'Single Room' is valid.");

            // Invalid room
            BookingValidator.validateRoomType("King Room"); // should throw exception
        } catch (InvalidBookingException e) {
            System.out.println("Caught error: " + e.getMessage());
        }

        try {
            // Valid availability
            BookingValidator.validateAvailability(3);
            System.out.println("Room availability is valid.");

            // No availability
            BookingValidator.validateAvailability(0); // should throw exception
        } catch (InvalidBookingException e) {
            System.out.println("Caught error: " + e.getMessage());
        }

        try {
            // Valid add-on cost
            BookingValidator.validateAddOnCost(200.0);
            System.out.println("Add-on cost is valid.");

            // Negative add-on cost
            BookingValidator.validateAddOnCost(-50.0); // should throw exception
        } catch (InvalidBookingException e) {
            System.out.println("Caught error: " + e.getMessage());
        }

        System.out.println("\n--- End of Use Case 9 Demo ---\n");
    }
}

class UseCase10Demo {

    // Inventory counts for demonstration
    private static Map<String, Integer> inventory = new HashMap<>();
    private static Stack<String> cancelledReservations = new Stack<>();
    private static Map<String, String> reservationToRoom = new HashMap<>();

    static {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Simulate confirming a booking
    public static void confirmBooking(String reservationId, String roomType) {
        Integer available = inventory.get(roomType);
        if (available == null || available <= 0) {
            System.out.println("Cannot book " + roomType + " for " + reservationId + " — no availability.");
            return;
        }
        inventory.put(roomType, available - 1);
        reservationToRoom.put(reservationId, roomType);
        System.out.println("Booking confirmed: " + reservationId + " -> " + roomType);
    }

    // Cancel a booking with rollback
    public static void cancelBooking(String reservationId) {
        String roomType = reservationToRoom.get(reservationId);
        if (roomType == null) {
            System.out.println("Cannot cancel " + reservationId + " — reservation does not exist.");
            return;
        }

        // Restore inventory
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Track cancelled reservation
        cancelledReservations.push(reservationId);

        // Remove reservation mapping
        reservationToRoom.remove(reservationId);

        System.out.println("Booking cancelled: " + reservationId + " -> " + roomType);
        System.out.println("Updated inventory for " + roomType + ": " + inventory.get(roomType));
    }

    // Demo method
    public static void demo() {
        System.out.println("\n--- Use Case 10: Booking Cancellation & Inventory Rollback ---\n");

        // Confirm some bookings
        confirmBooking("Res-101", "Single Room");
        confirmBooking("Res-102", "Double Room");
        confirmBooking("Res-103", "Suite Room");

        // Attempt cancellations
        cancelBooking("Res-102"); // valid cancellation
        cancelBooking("Res-999"); // invalid cancellation
        cancelBooking("Res-101"); // valid cancellation

        System.out.println("\nCancelled reservations stack (LIFO order): " + cancelledReservations);
        System.out.println("\n--- End of Use Case 10 Demo ---\n");
    }
}

class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

// Queue of booking requests
class BookingRequestQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.offer(request);
    }

    public synchronized BookingRequest getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Shared inventory of rooms
class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public synchronized boolean allocate(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void release(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, available + 1);
    }

    public synchronized Map<String, Integer> getInventory() {
        return new HashMap<>(inventory);
    }
}

// Handles allocation logic
class RoomAllocationService {
    public void allocateRoom(BookingRequest request, RoomInventory inventory) {
        if (inventory.allocate(request.getRoomType())) {
            System.out.println("Booking confirmed for Guest: " + request.getGuestName() +
                    ", Room Type: " + request.getRoomType());
        } else {
            System.out.println("Booking failed for Guest: " + request.getGuestName() +
                    " -> " + request.getRoomType() + " (No availability)");
        }
    }
}

// Runnable processor for concurrent bookings
class ConcurrentBookingProcessor implements Runnable {
    private final BookingRequestQueue bookingQueue;
    private final RoomInventory inventory;
    private final RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue bookingQueue,
                                      RoomInventory inventory,
                                      RoomAllocationService allocationService) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                request = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {
                allocationService.allocateRoom(request, inventory);
            }
        }
    }
}


class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Map<String, Integer> currentInventory = inventory.getInventory();

            for (Map.Entry<String, Integer> entry : currentInventory.entrySet()) {
                // Formatting as roomType-availableCount
                writer.write(entry.getKey() + "-" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length == 2) {
                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    // Directly updating the map inside the inventory object
                    inventory.getInventory().put(roomType, count);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error during system recovery: " + e.getMessage());
        }
    }
}


