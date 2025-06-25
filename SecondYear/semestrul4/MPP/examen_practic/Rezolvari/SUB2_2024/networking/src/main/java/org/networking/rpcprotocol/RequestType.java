package org.networking.rpcprotocol;

import java.io.Serializable;

public enum RequestType implements Serializable {
    LOGIN,
    LOGOUT,
    GET_ALL_GAMES, FIND_PLAYER_BY_ALIAS, ADD_GAME, ADD_POSITION, GET_ALL_POSITIONS, GET_ALL_TRAPS, GET_ALL_POSITIONS_BY_GAME, GET_ORGANIZATORI
}