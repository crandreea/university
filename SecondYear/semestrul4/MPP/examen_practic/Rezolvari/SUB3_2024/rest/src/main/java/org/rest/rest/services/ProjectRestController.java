package org.rest.rest.services;

import model.Configuration;
import model.Game;
import model.User;
import org.services.ProjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistence.database.ConfigurationRepo;
import persistence.database.GameRepo;
import persistence.database.UserRepo;
import persistence.interfaces.IGameRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProjectRestController {

    private GameRepo gameRepository;
    private UserRepo playerRepository;
    private ConfigurationRepo configurationRepository;

    @Autowired
    public ProjectRestController(GameRepo gameRepository, UserRepo playerRepository, ConfigurationRepo configurationRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.configurationRepository = configurationRepository;
    }

    @PostMapping("/configurations")
    public ResponseEntity<Configuration> addConfiguration(@RequestBody Configuration configuration) throws SQLException, ProjectException {
        Configuration savedConfiguration = configurationRepository.save(configuration).orElseThrow(() -> new ProjectException("Configuration could not be saved"));
        return new ResponseEntity<>(savedConfiguration, HttpStatus.CREATED);
    }

    @GetMapping("/games/{alias}")
    public List<Game> getGamesByPlayer(@PathVariable String alias) throws ProjectException {
        User foundPlayer = null;
        Iterable<User> players = playerRepository.findAll();
        for (User player : players) {
            if (player.getUsername().equals(alias)) {
                foundPlayer = player;
                break;
            }
        }
        if (foundPlayer == null)
            throw new ProjectException("Player not found");

        List<Game> games = StreamSupport.stream(gameRepository.findAllByPlayer(foundPlayer).spliterator(), false).toList();
        List<Game> gamesWithAtLeast2Guesses = new ArrayList<>();

        for (Game game: games)
            if (game.getNoOfGuessedWords() >= 2)
                gamesWithAtLeast2Guesses.add(game);

        return gamesWithAtLeast2Guesses;
    }

}
