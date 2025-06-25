package org.services;
import java.io.Serializable;
public class ProjectException extends Exception implements Serializable {
    public ProjectException() {
    }

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
