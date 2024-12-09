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

        if (totalVen > 0) {
            for (int i = 0; i < totalVen; i++) {
                Vendor vendorThread = new Vendor(ticketPool,ticketReleaseRate,totalTickets,(i+1));
                new Thread(vendorThread).start();
            }
        }

        if (totalVen > 0) {
            for (int i = 0; i < totalVen; i++) {
                Customer customerThread = new Customer(ticketPool,ticketReleaseRate,(i+1));
                new Thread(customerThread).start();
            }
        }
        return "System Starting";
    }
}
