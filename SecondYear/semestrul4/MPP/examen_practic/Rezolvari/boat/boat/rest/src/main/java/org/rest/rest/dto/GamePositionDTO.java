package org.rest.rest.dto;

public class GamePositionDTO {
    private int x;
    private int y;

    public GamePositionDTO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}