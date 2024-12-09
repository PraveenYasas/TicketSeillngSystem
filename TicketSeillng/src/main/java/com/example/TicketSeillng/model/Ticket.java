package com.example.TicketSeillng.model;

public class Ticket {
    private String eventName;
    private double price;
    private boolean isBooked;

    public Ticket(String eventName, double price) {
        this.eventName = eventName;
        this.price = price;
        this.isBooked = false;
    }


    @Override
    public String toString() {
        return "Ticket{ eventName = " + eventName + ", Price = " + price + "}";
    }
}
