import java.util.*;

class HotelRoomBookingSystem {

    static class Room {
        int roomNo;
        String type;
        double price;
        boolean available = true;

        Room(int roomNo, String type, double price) {
            this.roomNo = roomNo;
            this.type = type;
            this.price = price;
        }
    }

    static class Booking {
        int bookingId;
        String customerName;
        String phone;
        Room room;
        String checkIn;
        String checkOut;
        int days;
        double totalCost;

        Booking(int bookingId, String customerName, String phone,
                Room room, String checkIn, String checkOut, int days) {
            this.bookingId = bookingId;
            this.customerName = customerName;
            this.phone = phone;
            this.room = room;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.days = days;
            this.totalCost = room.price * days;
        }
    }

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int nextBookingId = 101;

    static void displayAvailableRooms() {
        System.out.println("\\nAvailable Rooms:");
        boolean found = false;

        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room No: " + r.roomNo
                        + ", Type: " + r.type
                        + ", Price: Rs." + r.price);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    static void searchRoomsByType() {
        System.out.print("Enter room type (Single/Double/Deluxe): ");
        String type = sc.nextLine();
        boolean found = false;

        for (Room r : rooms) {
            if (r.type.equalsIgnoreCase(type) && r.available) {
                System.out.println("Room No: " + r.roomNo
                        + ", Type: " + r.type
                        + ", Price: Rs." + r.price);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms found.");
        }
    }

    static void bookRoom() {
        displayAvailableRooms();
        System.out.print("Enter room number: ");
        int roomNo = Integer.parseInt(sc.nextLine());

        Room selectedRoom = null;
        for (Room r : rooms) {
            if (r.roomNo == roomNo && r.available) {
                selectedRoom = r;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not available.");
            return;
        }

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();
        System.out.print("Enter check-in date (DD-MM-YYYY): ");
        String checkIn = sc.nextLine();
        System.out.print("Enter check-out date (DD-MM-YYYY): ");
        String checkOut = sc.nextLine();

        try {
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter
                            .ofPattern("dd-MM-uuuu")
                            .withResolverStyle(
                                    java.time.format.ResolverStyle.STRICT);

            java.time.LocalDate in =
                    java.time.LocalDate.parse(checkIn, formatter);
            java.time.LocalDate out =
                    java.time.LocalDate.parse(checkOut, formatter);

            long days = java.time.temporal.ChronoUnit.DAYS.between(in, out);

            if (days <= 0) {
                System.out.println(
                        "Invalid dates! Check-out must be after check-in.");
                return;
            }

            Booking b = new Booking(nextBookingId++, name, phone,
                    selectedRoom, checkIn, checkOut, (int) days);

            bookings.add(b);
            selectedRoom.available = false;

            System.out.println("\\nBooking Successful!");
            System.out.println("Booking ID: " + b.bookingId);
            System.out.println("Customer: " + b.customerName);
            System.out.println("Room Number: " + b.room.roomNo);
            System.out.println("Number of Days: " + b.days);
            System.out.println("Total Cost: Rs." + b.totalCost);

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Invalid date format. Use DD-MM-YYYY.");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter booking ID to cancel: ");
        int id = Integer.parseInt(sc.nextLine());

        Iterator<Booking> it = bookings.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.bookingId == id) {
                b.room.available = true;
                it.remove();
                System.out.println("Booking cancelled successfully.");
                return;
            }
        }

        System.out.println("Booking not found.");
    }

    static void searchBooking() {
        System.out.print("Enter booking ID: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Booking b : bookings) {
            if (b.bookingId == id) {
                System.out.println("Booking ID: " + b.bookingId);
                System.out.println("Customer: " + b.customerName);
                System.out.println("Phone: " + b.phone);
                System.out.println("Room: " + b.room.roomNo);
                System.out.println("Check-in: " + b.checkIn);
                System.out.println("Check-out: " + b.checkOut);
                System.out.println("Days: " + b.days);
                System.out.println("Total Cost: Rs." + b.totalCost);
                return;
            }
        }

        System.out.println("Booking not found.");
    }

    static void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking b : bookings) {
            System.out.println("ID: " + b.bookingId
                    + ", Customer: " + b.customerName
                    + ", Room: " + b.room.roomNo
                    + ", Days: " + b.days
                    + ", Total: Rs." + b.totalCost);
        }
    }

    public static void main(String[] args) {
        rooms.add(new Room(101, "Single", 1000));
        rooms.add(new Room(102, "Double", 2000));
        rooms.add(new Room(103, "Deluxe", 3000));
        rooms.add(new Room(104, "Single", 1000));
        rooms.add(new Room(105, "Double", 2000));

        while (true) {
            System.out.println("\\n=== HOTEL ROOM BOOKING SYSTEM ===");
            System.out.println("1. Display Available Rooms");
            System.out.println("2. Search Rooms by Type");
            System.out.println("3. Book a Room");
            System.out.println("4. Cancel a Booking");
            System.out.println("5. Search a Booking");
            System.out.println("6. Display All Bookings");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    displayAvailableRooms();
                    break;
                case 2:
                    searchRoomsByType();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    searchBooking();
                    break;
                case 6:
                    displayAllBookings();
                    break;
                case 7:
                    System.out.println("Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
