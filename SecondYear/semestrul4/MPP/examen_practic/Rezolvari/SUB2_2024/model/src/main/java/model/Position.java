package model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "positions")
public class Position extends Entity<Integer> {

    private Game game;
    private Integer coordinateX;
    private Integer coordinateY;
    private boolean isRevealed;
    private boolean isTrap;
    private Integer shotOrder;

    public Position() {
    }

    public Position(Integer coordinateX, Integer coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.isRevealed = false;
        this.isTrap = false;
        this.shotOrder = 0;
    }

    public Position(Game game, Integer coordinateX, Integer coordinateY) {
        this.game = game;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.isRevealed = false;
        this.isTrap = false;
        this.shotOrder = 0;
    }

    @ManyToOne
    @JoinColumn(name = "game_id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Column(name = "coordinateX")
    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    @Column(name = "coordinateY")
    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Column(name = "is_revealed")
    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    @Column(name = "is_trap")
    public boolean isTrap() {
        return isTrap;
    }

    public void setTrap(boolean trap) {
        isTrap = trap;
    }

    @Column(name = "shot_order")
    public Integer getShotOrder() {
        return shotOrder;
    }

    public void setShotOrder(Integer shotOrder) {
        this.shotOrder = shotOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return isRevealed == position.isRevealed && isTrap == position.isTrap && Objects.equals(game, position.game) && Objects.equals(coordinateX, position.coordinateX) && Objects.equals(coordinateY, position.coordinateY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, coordinateX, coordinateY, isRevealed, isTrap);
    }

    @Override
    public String toString() {
        return "Position{" +
                "game=" + game +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", isRevealed=" + isRevealed +
                ", isTrap=" + isTrap +
                '}';
    }
}