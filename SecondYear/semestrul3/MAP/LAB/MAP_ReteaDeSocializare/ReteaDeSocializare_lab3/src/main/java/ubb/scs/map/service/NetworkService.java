package ubb.scs.map.service;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Tuplu;
import ubb.scs.map.domain.Utilizator;

import java.util.Iterator;

public class NetworkService {
    private final Service<Long, Utilizator> userService;
    private final Service<Tuplu<Long, Long>, Prietenie> prietenieService;

    public NetworkService(Service<Long, Utilizator> userService, Service<Tuplu<Long, Long>, Prietenie> prietenieService) {
        this.userService = userService;
        this.prietenieService = prietenieService;
    }

    public Long getNewUserId() {
        Long id = 0L;
        for (Utilizator u : userService.findAll()) {
            id = u.getId();
        }
        id++;
        return id;
    }


    public void addUtilizator(Utilizator utilizator) {
        utilizator.setId(getNewUserId());
        userService.save(utilizator);
    }

    public void deleteUtilizator(Long id) {
        Utilizator utilizatorsters = userService.delete(id);
        if (utilizatorsters != null) {

            Iterator<Prietenie> iterator = prietenieService.findAll().iterator();
            while (iterator.hasNext()) {
                Prietenie friendship = iterator.next();
                if (friendship.containsUser(id)) {
                    iterator.remove(); prietenieService.delete(friendship.getId());
                    System.out.println("Prietenia între utilizatorii " + friendship.getIdUser1() + " și " + friendship.getIdUser2() + " a fost ștearsă.");
                }
            }
        }
    }

    public void addPrietenie(Long id1, Long id2) {
        Prietenie pr = new Prietenie(id1, id2);
        pr.setId(new Tuplu<>(id1, id2));

        prietenieService.save(pr);
    }

    public void deletePrietenie(Long id1, Long id2) {

        prietenieService.delete(new Tuplu<>(id1, id2));
        prietenieService.delete(new Tuplu<>(id2, id1));

    }

    public Utilizator findUser(Long id) {
        return userService.findOne(id);
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return userService.findAll();
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return prietenieService.findAll();
    }

}
