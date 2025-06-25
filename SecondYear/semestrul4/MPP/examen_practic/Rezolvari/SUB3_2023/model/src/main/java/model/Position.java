package model;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "positions")
public class Position extends Entity<Integer> {

    private Game game;
    private Integer coordinateX;
    private Integer coordinateY;
    private Integer positionIndex;

    public Position() {
    }

    public Position(Integer coordinateX, Integer coordinateY, Integer positionIndex) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.positionIndex = positionIndex;
    }

    public Position(Game game, Integer coordinateX, Integer coordinateY, Integer positionIndex) {
        this.game = game;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.positionIndex = positionIndex;
    }

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Column(name = "coordinateX", nullable = false)
    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    @Column(name = "coordinateY", nullable = false)
    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Column(name = "position_index", nullable = false)
    public Integer getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Integer positionIndex) {
        this.positionIndex = positionIndex;
    }

    @Override
    public String toString() {
        return "Position{" +
                "game=" + game +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", positionIndex=" + positionIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return game.equals(position.game) &&
                coordinateX.equals(position.coordinateX) &&
                coordinateY.equals(position.coordinateY) &&
                positionIndex.equals(position.positionIndex);
    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + coordinateX.hashCode();
        result = 31 * result + coordinateY.hashCode();
        result = 31 * result + positionIndex.hashCode();
        return result;
    }
}