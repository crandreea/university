package org.networking.rpcprotocol;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    ERROR,
    GET_ORGANIZATORI,
    R_MAKE_SHOT, R_CREATE_GAME, R_GET_GAME_BY_ID, R_GET_FINISHED_GAMES, R_GET_GAME_POSITIONS, R_GET_BOAT_GAME_POSITIONS, R_UPDATE, UPDATE_CLASAMENT, GAME_FINISHED, UPDATE

}