package ro.mpp2025.javaprojectui.services;

import ro.mpp2025.javaprojectui.Organizator;
import ro.mpp2025.javaprojectui.service.GlobalService;
import ro.mpp2025.javaprojectui.service.MainService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class Authenticator {
    public static String loginJWT(String username, String password) throws Exception {
        MainService network = GlobalService.getNetwork();

        if (username == null || password == null) {
            throw new Exception("Username and password must not be null");
        }

        Iterable<Organizator> allOrganizatori = network.getAllOrganizatori();

        Optional<Organizator> user = StreamSupport.stream(allOrganizatori.spliterator(), false)
                .filter(u -> Objects.equals(u.getUsername(), username) && Objects.equals(u.getPassword(), password))
                .findFirst();

        if (user.isEmpty()) {
            throw new Exception("User not found or incorrect password.");
        }

        return JwtUtil.generateToken(username);
    }
}
