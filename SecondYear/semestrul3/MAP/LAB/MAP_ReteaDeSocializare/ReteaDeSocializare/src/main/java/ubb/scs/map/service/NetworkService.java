package ubb.scs.map.service;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Tuplu;
import ubb.scs.map.domain.Utilizator;

import java.sql.SQLException;
import java.util.stream.StreamSupport;

import java.util.Optional;

public class NetworkService {
    private final Service<Long, Utilizator> userService;
    private final Service<Tuplu<Long, Long>, Prietenie> prietenieService;
    //private final CommunityService communityService;

    public NetworkService(Service<Long, Utilizator> userService, Service<Tuplu<Long, Long>, Prietenie> prietenieService) {
        this.userService = userService;
        this.prietenieService = prietenieService;
        //this.communityService = communityService;
    }

    public Long getNewUserId() {
        return StreamSupport.stream(userService.findAll().spliterator(), false)
                .mapToLong(Utilizator::getId) //mapeaza la id
                .max() //gaseste id ul maxim
                .orElse(0L) + 1;
    }


    public void addUtilizator(Utilizator utilizator) {
        utilizator.setId(getNewUserId());
        userService.save(utilizator);
    }


    public void deleteUtilizator(Long id) {
        Optional<Utilizator> utilizatorsters = userService.delete(id);

        if (utilizatorsters.isPresent()) {
            StreamSupport.stream(prietenieService.findAll().spliterator(), false)
                    .filter(friendship -> friendship.containsUser(id))
                    .forEach(friendship -> {
                        prietenieService.delete(friendship.getId());
//                        System.out.println("Prietenia între utilizatorii "
//                                + friendship.getIdUser1() + " și "
//                                + friendship.getIdUser2() + " a fost ștearsă.");
                    });
        }
    }



    public void addPrietenie(Long id1, Long id2, CommunityService communityService) {
        Prietenie pr = new Prietenie(id1, id2);
        pr.setId(new Tuplu<>(id1, id2));

        prietenieService.save(pr);
        communityService.addEdge(id1, id2);
    }

    public void deletePrietenie(Long id1, Long id2, CommunityService communityService) {

        prietenieService.delete(new Tuplu<>(id1, id2));
        prietenieService.delete(new Tuplu<>(id2, id1));
        communityService.deleteEdge(id1, id2);

    }

    public Optional<Utilizator> findUser(Long id) throws SQLException {
        return userService.findOne(id);
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return userService.findAll();
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return prietenieService.findAll();
    }

}
