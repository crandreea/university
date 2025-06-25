package model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "positions")
public class GamePosition extends Entity<Integer> {
    @ManyToOne
    @JoinColumn(name = "gameId", nullable = false)
    private Game gameId;
    private int x;
    private int y;
    @Enumerated(EnumType.STRING)
    private PositionsStatus status;

    @Column(name = "hasBoat", nullable = false)// "EMPTY", "BOAT", "HIT", "MISS"
    private boolean hasBoat;
    private boolean isRevealed;

    @Column(name = "shotOrder") // Nou câmp pentru ordinea șuturilor
    private Integer shotOrder;

    @Column(name = "shotTimestamp") // Nou câmp pentru timestamp (pentru precizie, opțional)
    private LocalDateTime shotTimestamp;

    public GamePosition() {
        this.hasBoat = false;
        this.isRevealed = false;
        this.status = PositionsStatus.EMPTY;
        this.shotOrder = 0; // Inițial, niciun șut făcut
        this.shotTimestamp = LocalDateTime.now();
    }

    public GamePosition(Game gameId, int x, int y) {
        this();
        this.gameId = gameId;
        this.x = x;
        this.y = y;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public Integer getGameId() {
        return gameId.getId();
    }

    public Game getGame(){
        return gameId;
    }

    public void setGame(Game gameId) {
        this.gameId = gameId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PositionsStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = PositionsStatus.valueOf(status);
    }

    public boolean isHasBoat() {
        return hasBoat;
    }

    public void setHasBoat(boolean hasBoat) {
        this.hasBoat = hasBoat;
        if (hasBoat) {
            this.status = PositionsStatus.BOAT;
        }
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        this.isRevealed = revealed;
        if (revealed) {
            if (this.shotTimestamp == null) { // Set only if not already set
                this.shotTimestamp = LocalDateTime.now();
            }
            if (hasBoat) {
                this.status = PositionsStatus.BOAT;
            } else {
                this.status = PositionsStatus.MISS;
            }
        }else{
            this.shotOrder = null;
            this.shotTimestamp = null;
        }
    }

    public Integer getShotOrder() {
        return shotOrder;
    }

    public void setShotOrder(Integer shotOrder) {
        this.shotOrder = shotOrder;
    }

    public LocalDateTime getShotTimestamp() {
        return shotTimestamp;
    }

    public void setShotTimestamp(LocalDateTime shotTimestamp) {
        this.shotTimestamp = shotTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePosition that = (GamePosition) o;
        return x == that.x &&
                y == that.y &&
                hasBoat == that.hasBoat &&
                isRevealed == that.isRevealed &&
                Objects.equals(gameId, that.gameId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, x, y, status, hasBoat, isRevealed);
    }

    @Override
    public String toString() {
        return "GamePosition{" +
                "gameId=" + gameId +
                ", x=" + x +
                ", y=" + y +
                ", status='" + status + '\'' +
                ", hasBoat=" + hasBoat +
                ", isRevealed=" + isRevealed +
                '}';
    }
}
