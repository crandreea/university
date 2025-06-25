package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "games")
public class Game extends Entity<Integer> {
    @ManyToOne // Define the many-to-one relationship
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status")
    private GameStatus status;
    // "SETUP", "PLAYING", "FINISHED"

    @Column(name = "total_shots")
    private int total_shots;

    @Column(name = "successful_shots")
    private int successful_shots;

    @Column(name = "start_time")
    private LocalDateTime start_time;

    @Column(name = "end_time")
    private LocalDateTime end_time;

    @Column(name = "score")
    private int score;


    public Game() {
        this.status = GameStatus.SETUP;
        this.total_shots = 0;
        this.successful_shots = 0;
        this.start_time = LocalDateTime.now();
        this.score = 0;
    }

    public Game(User user) {
        this();
        this.user_id = user;
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

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User userId) {
        this.user_id = userId;
    }


    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getTotal_shots() {
        return total_shots;
    }

    public void setTotal_shots(int totalShots) {
        this.total_shots = totalShots;
    }

    public int getSuccessful_shots() {
        return successful_shots;
    }

    public void setSuccessful_shots(int successful_shots) {
        this.successful_shots = successful_shots;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementTotalShots() {
        this.total_shots++;
    }

    public void incrementSuccessfulShots() {
        this.successful_shots++;
    }

    public void incrementScore(int delta) {
        this.score += delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return total_shots == game.total_shots &&
                successful_shots == game.successful_shots &&
                score == game.score &&
                Objects.equals(user_id, game.user_id) &&
                Objects.equals(status, game.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, status, total_shots, successful_shots, score);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + getId() +
                ", userId=" + user_id.getId() +
                ", status='" + status + '\'' +
                ", totalShots=" + total_shots +
                ", successful_shots=" + successful_shots +
                ", score=" + score +
                '}';
    }
}