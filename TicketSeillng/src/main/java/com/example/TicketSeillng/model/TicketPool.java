package com.example.TicketSeillng.model;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;
    private boolean productionComplete = false;

    public TicketPool(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero.");
        }
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            wait();
        }
        tickets.add(ticket);
        notifyAll();
    }

    public synchronized Ticket  getTicket() throws InterruptedException{
        while (tickets.isEmpty() && !productionComplete) {
            wait();
        }
        if (tickets.isEmpty()) {
            return null;
        }
        Ticket ticket = tickets.poll();
        notifyAll();
        return ticket;
    }

    public synchronized int getTotalTickets() {
        return tickets.size();
    }

    public synchronized void signalProductionComplete() {
        productionComplete = true;
        notifyAll();
    }
}