package org.networking.rpcprotocol;

import java.io.Serializable;

public enum RequestType implements Serializable {
    LOGIN,
    LOGOUT,
    CREATE_GAME, MAKE_SHOT, GET_GAME_POSITIONS, GET_BOAT_GAME_POSITIONS, GET_GAME_BY_ID, GET_FINISHED_GAMES, UPDATE, GET_ORGANIZATORI
}