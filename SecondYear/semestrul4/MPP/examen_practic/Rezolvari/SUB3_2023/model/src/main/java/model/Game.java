package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "games")
public class Game extends Entity<Integer> {

    private User player;
    private Configuration configuration;
    private Integer noOfSeconds;
    private Integer score;

    public Game() {
    }

    public Game(User player, Configuration configuration, Integer noOfSeconds, Integer score) {
        this.player = player;
        this.configuration = configuration;
        this.noOfSeconds = noOfSeconds;
        this.score = score;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id", nullable = false)
    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    @ManyToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Column(name = "no_of_seconds", nullable = false)
    public Integer getNoOfSeconds() {
        return noOfSeconds;
    }

    public void setNoOfSeconds(Integer noOfSeconds) {
        this.noOfSeconds = noOfSeconds;
    }

    @Column(name = "score", nullable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Game{" +
                "player=" + player +
                ", configuration=" + configuration +
                ", noOfSeconds=" + noOfSeconds +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Game game = (Game) o;
        return Objects.equals(player, game.player) && Objects.equals(configuration, game.configuration) && Objects.equals(noOfSeconds, game.noOfSeconds) && Objects.equals(score, game.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, configuration, noOfSeconds, score);
    }
}