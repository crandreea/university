package org.services;

import model.Game;

public interface ProjectObserver {
    void gameFinished(Game game) throws ProjectException;

    void leaderboardUpdated() throws ProjectException;
}
