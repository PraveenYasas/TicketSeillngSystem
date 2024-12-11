package com.example.TicketSeillng.controller;

import com.example.TicketSeillng.model.Customer;
import com.example.TicketSeillng.model.TicketPool;
import com.example.TicketSeillng.model.Vendor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket-selling")
public class TicketController {
    private TicketPool ticketPool;
    private int totalTickets;
    private int ticketReleaseRate;
    private int ticketRetrievalRate;
    private int maxTicketCapacity;

    private int totalCus;
    private int totalVen;
    private Thread[] vendorThreads;
    private Thread[] customerThreads;

    private boolean isSystemRunning = false;
    @PostMapping("/config")
    public String configuration(@RequestParam int totalTickets, int ticketReleaseRate, int ticketRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketRetrievalRate = ticketRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;

        ticketPool = new TicketPool(maxTicketCapacity);
        return "Config Success";
    }

    @PostMapping("/set-customers")
    public String setCustomers(@RequestParam int totalCus) {
        this.totalCus = totalCus;
        return "Customers set";
    }

    @PostMapping("/set-vendors")
    public String setVendors(@RequestParam int totalVen) {
        this.totalVen = totalVen;
        return "Customers set";
    }

    @PostMapping("/start")
    public String startSystem() {

        if (isSystemRunning) {
            return "System is already running.";
        }

        isSystemRunning = true;
        vendorThreads = new Thread[totalVen];
        customerThreads = new Thread[totalCus];

        for (int i = 0; i < totalVen; i++) {
            Vendor vendorThread = new Vendor(ticketPool, ticketReleaseRate, totalTickets, (i + 1));
            vendorThreads[i] = new Thread(vendorThread);
            vendorThreads[i].start();
        }

        for (int i = 0; i < totalCus; i++) {
            Customer customerThread = new Customer(ticketPool, ticketRetrievalRate, (i + 1));
            customerThreads[i] = new Thread(customerThread);
            customerThreads[i].start();
        }

        return "System Starting";
    }

    @PostMapping("/stop")
    public String stopSystem() {
        if (!isSystemRunning) {
            return "System is not running.";
        }

        isSystemRunning = false;

        for (int i = 0;i < vendorThreads.length; i++) {
            if (vendorThreads[i] != null && vendorThreads[i].isAlive()) {
                vendorThreads[i].interrupt();
            }
        }

        for (int i = 0; i < customerThreads.length; i++) {
            if (customerThreads[i] != null && customerThreads[i].isAlive()) {
                customerThreads[i].interrupt();
            }
        }

        ticketPool.signalProductionComplete();

        return "System Stopped";
    }
}
