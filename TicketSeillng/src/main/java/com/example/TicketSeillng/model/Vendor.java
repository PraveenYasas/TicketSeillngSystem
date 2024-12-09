package com.example.TicketSeillng.model;

public class Vendor implements Runnable{
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int totalTickets;
    private final int producerID;

    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets, int producerID) {

        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
        this.producerID = producerID;
    }

    @Override
    public void run() {
        int ticketCount = 0;

        while (ticketCount < totalTickets) {
            try {
                for (int i = 0; i < releaseRate && ticketCount < totalTickets; i++) {
                    Ticket ticket = new Ticket("Ticket - " + (++ticketCount), 50);
                    ticketPool.addTicket(ticket);
                    System.out.println("Producer " + producerID + " added: " + ticket + " | Total Tickets: " + ticketPool.getTotalTickets());
                }
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;

            }
        }
    }
}
