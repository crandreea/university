package org.networking.rpcprotocol;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    ERROR,
    GET_ORGANIZATORI,
    GET_ALL_GAMES, GET_ALL_TRAPS, GET_ALL_POSITIONS, FIND_PLAYER_BY_ALIAS, ADD_POSITION, ADD_GAME, UPDATE_GAMES, UPDATE

}