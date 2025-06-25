package org.networking.rpcprotocol;

import java.io.Serializable;

public enum RequestType implements Serializable {
    LOGIN,
    LOGOUT,
    GET_ORGANIZATORI,
    ADD_GAME,
    GET_ALL_GAMES,
    FIND_PLAYER_BY_ALIAS,
    GET_ALL_CONFIGURATIONS
}