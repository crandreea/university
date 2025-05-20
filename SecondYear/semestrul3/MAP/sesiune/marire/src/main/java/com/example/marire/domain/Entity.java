package com.example.marire.domain;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID>  implements Serializable {
    static final long serialVersionUID = 1L;
    private ID id;
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}
