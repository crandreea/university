package ro.mpp2025.javaprojectui;

import ro.mpp2025.javaprojectui.service.GlobalService;
import ro.mpp2025.javaprojectui.service.MainService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class Authenticator {
    public static Organizator login(String username, String password) throws Exception {
        MainService network = GlobalService.getNetwork();
        if (username == null) {
            throw new Exception("Username must not be null");
        }
        if (password == null) {
            throw new Exception("Password must not be null");
        }

        System.out.println("Attempting login with: " + username);

        try{
            System.out.println("Incerc sa iau toti organizatorii");
            Iterable<Organizator> allOrganizatori = network.getAllOrganizatori();
            System.out.println("Organizatori: " + allOrganizatori);
            if(allOrganizatori == null) {
                throw new Exception("All organizatori must not be null");
            }

            StreamSupport.stream(allOrganizatori.spliterator(), false)
                    .forEach(u -> System.out.println("User: " + u.getUsername() + ", Password: " + u.getPassword()));

            Optional<Organizator> user = StreamSupport.stream(allOrganizatori.spliterator(), false)
                    .filter(u -> Objects.equals(u.getUsername(), username) && Objects.equals(u.getPassword(), password))
                    .findFirst();

            if (user.isEmpty() || user == null) {
                throw new ProjectException("User not found or incorrect password.");
            }

            return user.get();

        }catch (Exception e){
            System.out.println("Error in Authentiifif: " + e.getMessage());
        }

        return null;
    }
}
