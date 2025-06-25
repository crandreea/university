package org.rest.rest.dto;

import model.PositionsStatus;

public class ShotDTO {
    private int x;
    private int y;
    private PositionsStatus status; // HIT or MISS
    private double distanceToNearestBoat; // Only for MISS

    public ShotDTO(int x, int y, PositionsStatus status, double distanceToNearestBoat) {
        this.x = x;
        this.y = y;
        this.status = status;
        this.distanceToNearestBoat = distanceToNearestBoat;
    }

    // Getters and Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public PositionsStatus getStatus() { return status; }
    public void setStatus(PositionsStatus status) { this.status = status; }
    public double getDistanceToNearestBoat() { return distanceToNearestBoat; }
    public void setDistanceToNearestBoat(double distanceToNearestBoat) { this.distanceToNearestBoat = distanceToNearestBoat; }
}