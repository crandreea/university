package model.dto;

import java.util.Objects;

public class PositionDTO {

    private Integer coordinateX;
    private Integer coordinateY;
    private Integer positionIndex;

    public PositionDTO(Integer coordinateX, Integer coordinateY, Integer positionIndex) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.positionIndex = positionIndex;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Integer getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Integer positionIndex) {
        this.positionIndex = positionIndex;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", positionIndex=" + positionIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionDTO that = (PositionDTO) o;
        return Objects.equals(coordinateX, that.coordinateX) && Objects.equals(coordinateY, that.coordinateY) && Objects.equals(positionIndex, that.positionIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateX, coordinateY, positionIndex);
    }
}