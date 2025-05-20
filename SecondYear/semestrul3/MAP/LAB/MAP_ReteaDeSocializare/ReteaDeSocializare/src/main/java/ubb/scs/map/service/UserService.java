package ubb.scs.map.service;

import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.repository.Repository;

public class UserService extends AbstractService<Long, Utilizator> {
    public UserService(Repository<Long, Utilizator> repository) {
        super(repository);
    }

}
