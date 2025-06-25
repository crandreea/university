package org.server.service;

import model.User;
import persistence.Repository;

public class UserService extends AbstractService<Integer, User> {
    public UserService(Repository repository) {
        super(repository);
    }
}
