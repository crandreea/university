package org.server;


import model.User;
import org.server.service.GlobalService;
import org.server.service.MainService;
import org.services.ProjectException;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class Authenticator {
    public static User login(String username, String password) throws Exception {
        MainService network = GlobalService.getNetwork();
        if (username == null) {
            throw new Exception("Username must not be null");
        }
        if (password == null) {
            throw new Exception("Password must not be null");
        }

        System.out.println("Attempting login with: " + username);

        try{
            Iterable<User> allOrganizatori = network.getAllOrganizatori();
            if(allOrganizatori == null) {
                throw new Exception("All organizatori must not be null");
            }

            StreamSupport.stream(allOrganizatori.spliterator(), false)
                    .forEach(u -> System.out.println("User: " + u.getUsername() + ", Password: " + u.getPassword()));

            Optional<User> user = StreamSupport.stream(allOrganizatori.spliterator(), false)
                    .filter(u -> Objects.equals(u.getUsername(), username) && Objects.equals(u.getPassword(), password))
                    .findFirst();

            if (user.isEmpty() || user == null) {
                throw new ProjectException("User not found or incorrect password.");
            }

            return user.get();

        }catch (Exception e){
            System.out.println("Error in Authentif: " + e.getMessage());
        }

        return null;
    }
}
