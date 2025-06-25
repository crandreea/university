package org.networking.rpcprotocol;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    ERROR,
    GET_ORGANIZATORI,
    UPDATE_GAMES,
    ADD_GAME,
    ADD_POSITION,
    ADD_CONFIGURATION,
    ADD_CONFIGURATION_WORD,
    GET_ALL_GAMES,
    GET_ALL_WORDS,
    GET_ALL_CONFIGURATIONS,
    FIND_PLAYER_BY_ALIAS

}