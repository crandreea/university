package org.networking.rpcprotocol;

import java.io.Serializable;

public enum RequestType implements Serializable {
    LOGIN,
    LOGOUT,
    ADD_POSITION, ADD_GAME, GET_ALL_GAMES, FIND_PLAYER_BY_ALIAS, GET_ALL_POSITIONS, GET_ALL_POSITIONS_BY_GAME, GET_ORGANIZATORI
}