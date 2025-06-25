package org.rest.rest.dto;

import model.GameStatus;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public class GameDetailsDTO {
    private Integer gameId;
    private User player; // Or just playerId and username
    private int totalScore;
    private int successfulShots;
    private int totalShots;
    private GameStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<ShotDTO> playerShots; // Shots made by the player in this game, in order
    private List<GamePositionDTO> boatPositions; // Original boat positions for this game

    // Constructor
    public GameDetailsDTO(Integer gameId, User player, int totalScore, int successfulShots, int totalShots, GameStatus status, LocalDateTime startTime, LocalDateTime endTime, List<ShotDTO> playerShots, List<GamePositionDTO> boatPositions) {
        this.gameId = gameId;
        this.player = player;
        this.totalScore = totalScore;
        this.successfulShots = successfulShots;
        this.totalShots = totalShots;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playerShots = playerShots;
        this.boatPositions = boatPositions;
    }

    // Getters and Setters
    public Integer getGameId() { return gameId; }
    public void setGameId(Integer gameId) { this.gameId = gameId; }
    public User getPlayer() { return player; }
    public void setPlayer(User player) { this.player = player; }
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    public int getSuccessfulShots() { return successfulShots; }
    public void setSuccessfulShots(int successfulShots) { this.successfulShots = successfulShots; }
    public int getTotalShots() { return totalShots; }
    public void setTotalShots(int totalShots) { this.totalShots = totalShots; }
    public GameStatus getStatus() { return status; }
    public void setStatus(GameStatus status) { this.status = status; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public List<ShotDTO> getPlayerShots() { return playerShots; }
    public void setPlayerShots(List<ShotDTO> playerShots) { this.playerShots = playerShots; }
    public List<GamePositionDTO> getBoatPositions() { return boatPositions; }
    public void setBoatPositions(List<GamePositionDTO> boatPositions) { this.boatPositions = boatPositions; }
}