package com.example.TicketSeillng.model;

public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final int consumerID;

    public Customer(TicketPool ticketPool, int retrievalRate, int consumerID) {

        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.consumerID = consumerID;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 0; i < retrievalRate; i++) {
                    Ticket ticket = ticketPool.getTicket();
                    if (ticket == null) {
                        System.out.println("Consumer " + consumerID + " could not retrieve a ticket. " + "No more tickets available.");
                        return;
                    }
                    System.out.println("Consumer " + consumerID + " purchased: " + ticket + " | Remaining Tickets: " + ticketPool.getTotalTickets());
                }
                Thread.sleep(300);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
