package org.rest.rest.dto;

import java.util.List;

public class BoatPlacementRequest {
    private List<GamePositionDTO> positions; // Lista de 3 poziții (x, y)

    // Constructor
    public BoatPlacementRequest() {} // Default constructor for JSON deserialization

    public BoatPlacementRequest(List<GamePositionDTO> positions) {
        this.positions = positions;
    }

    // Getter and Setter
    public List<GamePositionDTO> getPositions() { return positions; }
    public void setPositions(List<GamePositionDTO> positions) { this.positions = positions; }
}