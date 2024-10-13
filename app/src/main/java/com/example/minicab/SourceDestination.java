package com.example.minicab;

public class SourceDestination {
    String pickup,destination;

    public SourceDestination() {
    }

    public SourceDestination(String pickup, String destination) {
        this.pickup = pickup;
        this.destination = destination;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
