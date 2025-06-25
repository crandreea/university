package org.services;

import model.Game;

public interface ProjectObserver {
    void gameAdded(Game addedGame);
    //void inscriereAdded(Inscriere inscriere) throws ProjectException;
}
