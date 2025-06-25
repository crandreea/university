package org.networking.rpcprotocol;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    ERROR,
    GET_ORGANIZATORI,
    UPDATE_GAMES, GET_ALL_GAMES, GET_ALL_POSITIONS, FIND_PLAYER_BY_ALIAS, GET_ALL_POSITIONS_BY_GAME, ADD_POSITION, ADD_GAME, UPDATE

}