package com.OnlineTicketBooking.Main;

//import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.OnlineTicketBooking.Dao.EventDao;
import com.OnlineTicketBooking.Dao.TicketDao;
import com.OnlineTicketBooking.Dao.UserDao;
import com.OnlineTicketBooking.Entity.Event;
//import com.OnlineTicketBooking.Entity.Event;
//import com.OnlineTicketBooking.Entity.Ticket;
import com.OnlineTicketBooking.Entity.User;



import java.math.BigDecimal;
import java.sql.Date;
//import java.util.List;
//import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        UserDao userDAO = new UserDao();
        EventDao eventDAO = new EventDao();
        TicketDao ticketDAO = new TicketDao();
        Scanner scanner = new Scanner(System.in);

        User loggedInUser = null;

        while (true) {
            System.out.println("\n=== Online Ticket Booking System ===");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3.  Event");
            System.out.println("4. Book Ticket");
            System.out.println("5. View Users Who Booked an Event");
            System.out.println("6. View Events Booked by a User");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Register User
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    userDAO.registerUser(newUser);
                    System.out.println("User registered successfully!");
                    break;

                case 2: // Login User
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    loggedInUser = userDAO.loginUser(loginUsername, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("Login successful for user '" + loggedInUser.getUsername() + "'.");
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                    break;

                case 3: // Add Event
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }

                    System.out.print("Enter event name: ");
                    String eventName = scanner.nextLine();
                    System.out.print("Enter event location: ");
                    String eventLocation = scanner.nextLine();
                    System.out.print("Enter event date (yyyy-mm-dd): ");
                    String eventDateStr = scanner.nextLine();
                    System.out.print("Enter event price: ");
                    BigDecimal eventPrice = scanner.nextBigDecimal();
                    scanner.nextLine(); // Consume newline

                    Event event = new Event();
                    event.setName(eventName);
                    event.setLocation(eventLocation);
                    event.setDate(Date.valueOf(eventDateStr));
                    event.setPrice(eventPrice);

                    eventDAO.addEvent(event);
                    System.out.println("Event added successfully!");
                    break;

                case 4: // Book Ticket
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }

                    List<Event> events = eventDAO.listEvents();
                    if (events.isEmpty()) {
                        System.out.println("No events available to book.");
                        break;
                    }

                    System.out.println("Available Events:");
                    for (int i = 0; i < events.size(); i++) {
                        Event e = events.get(i);
                        System.out.println((i + 1) + ". " + e.getName() + " at " + e.getLocation());
                    }
                    System.out.print("Choose an event to book (1 to " + events.size() + "): ");
                    int eventChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (eventChoice >= 1 && eventChoice <= events.size()) {
                        Event selectedEvent = events.get(eventChoice - 1);
                        ticketDAO.bookTicket(loggedInUser, selectedEvent);
                        System.out.println("Ticket booked successfully for event '" + selectedEvent.getName() + "'.");
                    } else {
                        System.out.println("Invalid event choice.");
                    }
                    break;

                case 5: // View Users Who Booked an Event
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }

                    events = eventDAO.listEvents();
                    if (events.isEmpty()) {
                        System.out.println("No events available.");
                        break;
                    }

                    System.out.println("Select an event to view booked users:");
                    for (int i = 0; i < events.size(); i++) {
                        Event e = events.get(i);
                        System.out.println((i + 1) + ". " + e.getName());
                    }
                    System.out.print("Choose an event (1 to " + events.size() + "): ");
                    int eventToView = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (eventToView >= 1 && eventToView <= events.size()) {
                        Event selectedEvent = events.get(eventToView - 1);
                        List<User> users = ticketDAO.getUsersByEvent(selectedEvent);
                        System.out.println("Users who booked tickets for '" + selectedEvent.getName() + "':");
                        if (users.isEmpty()) {
                            System.out.println("No users have booked this event yet.");
                        } else {
                            for (User user : users) {
                                System.out.println("- " + user.getUsername());
                            }
                        }
                    } else {
                        System.out.println("Invalid event choice.");
                    }
                    break;

                case 6: // View Events Booked by a User
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }

                    List<Event> bookedEvents = ticketDAO.getEventsByUser(loggedInUser);
                    System.out.println("Events booked by '" + loggedInUser.getUsername() + "':");
                    if (bookedEvents.isEmpty()) {
                        System.out.println("No events booked yet.");
                    } else {
                        for (Event e : bookedEvents) {
                            System.out.println("- " + e.getName());
                        }
                    }
                    break;

                case 0: // Exit
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

