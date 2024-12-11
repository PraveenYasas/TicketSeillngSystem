// Package declaration for the controller
package com.example.TicketSeillng.controller;

// Importing required classes
import com.example.TicketSeillng.model.Customer;
import com.example.TicketSeillng.model.TicketPool;
import com.example.TicketSeillng.model.Vendor;
import org.springframework.web.bind.annotation.*;

// Mark this class as a REST controller for handling HTTP requests
@RestController
// Allow cross-origin requests to this controller
@CrossOrigin
// Base URL mapping for all endpoints in this controller
@RequestMapping("/api/ticket-selling")
public class TicketController {
    // Variables for ticket system configuration and management
    private TicketPool ticketPool;// Shared resource for managing tickets
    private int totalTickets;// Total number of tickets available
    private int ticketReleaseRate;// Rate at which vendors release tickets
    private int ticketRetrievalRate;// Rate at which customers retrieve tickets
    private int maxTicketCapacity;// Maximum capacity of the ticket pool

    private int totalCus;// Total number of customers
    private int totalVen;// Total number of vendors
    private Thread[] vendorThreads;// Array to hold vendor threads
    private Thread[] customerThreads;// Array to hold customer threads

    // Flag to check if the system is running
    private boolean isSystemRunning = false;
    // Endpoint to configure ticket system settings
    @PostMapping("/config")
    public String configuration(@RequestParam int totalTickets, int ticketReleaseRate, int ticketRetrievalRate, int maxTicketCapacity) {
        // Initializing variables and the ticket pool
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketRetrievalRate = ticketRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;

        // Create a new ticket pool with the specified maximum capacity
        ticketPool = new TicketPool(maxTicketCapacity);
        return "Config Success";// Return success message
    }

    // Endpoint to set the number of customers
    @PostMapping("/set-customers")
    public String setCustomers(@RequestParam int totalCus) {
        this.totalCus = totalCus;// Set total number of customers
        return "Customers set";// Return success message
    }

    // Endpoint to set the number of vendors
    @PostMapping("/set-vendors")
    public String setVendors(@RequestParam int totalVen) {
        this.totalVen = totalVen;// Set total number of vendors
        return "Customers set";// Return success message
    }

    // Endpoint to start the ticketing system
    @PostMapping("/start")
    public String startSystem() {
        // Check if the system is already running
        if (isSystemRunning) {
            return "System is already running.";
        }

        // Set the running flag and initialize threads
        isSystemRunning = true;
        vendorThreads = new Thread[totalVen];
        customerThreads = new Thread[totalCus];

        // Start vendor threads
        for (int i = 0; i < totalVen; i++) {
            Vendor vendorThread = new Vendor(ticketPool, ticketReleaseRate, totalTickets, (i + 1));
            vendorThreads[i] = new Thread(vendorThread);
            vendorThreads[i].start();
        }

        // Start customer threads
        for (int i = 0; i < totalCus; i++) {
            Customer customerThread = new Customer(ticketPool, ticketRetrievalRate, (i + 1));
            customerThreads[i] = new Thread(customerThread);
            customerThreads[i].start();
        }

        return "System Starting";
    }

    // Endpoint to stop the ticketing system
    @PostMapping("/stop")
    public String stopSystem() {
        // Check if the system is running
        if (!isSystemRunning) {
            return "System is not running.";
        }

        // Stop the system and interrupt threads
        isSystemRunning = false;

        // Interrupt all vendor threads
        for (int i = 0;i < vendorThreads.length; i++) {
            if (vendorThreads[i] != null && vendorThreads[i].isAlive()) {
                vendorThreads[i].interrupt();
            }
        }

        // Interrupt all customer threads
        for (int i = 0; i < customerThreads.length; i++) {
            if (customerThreads[i] != null && customerThreads[i].isAlive()) {
                customerThreads[i].interrupt();
            }
        }

        // Notify the ticket pool that production is complete
        ticketPool.signalProductionComplete();

        return "System Stopped";
    }
}
