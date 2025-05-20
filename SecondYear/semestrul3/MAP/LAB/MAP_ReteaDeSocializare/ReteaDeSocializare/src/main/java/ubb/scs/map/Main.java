package ubb.scs.map;

import ubb.scs.map.domain.validators.PrietenieValidator;
import ubb.scs.map.domain.validators.UtilizatorValidator;
import ubb.scs.map.repository.database.PrietenieDBRepository;
import ubb.scs.map.repository.database.UtilizatorDBRepository;
import ubb.scs.map.repository.file.PrietenieRepository;
import ubb.scs.map.repository.file.UtilizatorRepository;
import ubb.scs.map.service.CommunityService;
import ubb.scs.map.service.NetworkService;
import ubb.scs.map.service.PrietenieService;
import ubb.scs.map.service.UserService;
import ubb.scs.map.ui.Console;

import java.util.Scanner;

public class

Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

//        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(new UtilizatorValidator(), "/Users/croitoruandreea/Desktop/ANUL2/MAP/MAP_ReteaDeSocializare/ReteaDeSocializare/data/utilizatori.txt");
//        PrietenieRepository prietenieRepository = new PrietenieRepository(new PrietenieValidator(utilizatorRepository), "/Users/croitoruandreea/Desktop/ANUL2/MAP/MAP_ReteaDeSocializare/ReteaDeSocializare/data/friends.txt");

        UtilizatorDBRepository utilizatorRepository = new UtilizatorDBRepository(new UtilizatorValidator());
        PrietenieDBRepository prietenieRepository = new PrietenieDBRepository(new PrietenieValidator(utilizatorRepository));

        UserService userService = new UserService(utilizatorRepository);
        PrietenieService prietenieService = new PrietenieService(prietenieRepository);
        NetworkService networkService = new NetworkService(userService, prietenieService);
        CommunityService communityService = new CommunityService(networkService);

        Console console = new Console(communityService, networkService, scanner);
        console.run();

    }
}