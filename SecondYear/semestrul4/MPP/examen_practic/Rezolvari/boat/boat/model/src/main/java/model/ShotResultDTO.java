package model;

public class ShotResultDTO extends Entity<Integer> {
    private GamePosition position;
    private Double distanceToNearestBoat; // null dacă e HIT

    public ShotResultDTO(GamePosition position, Double distanceToNearestBoat) {
        this.position = position;
        this.distanceToNearestBoat = distanceToNearestBoat;
    }

    public GamePosition getPosition() {
        return position;
    }

    public Double getDistanceToNearestBoat() {
        return distanceToNearestBoat;
    }
}
