package org.rest.rest.services;

import model.*;
import model.dto.GameDetailsDTO;
import model.dto.PositionDTO;
import org.services.ProjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistence.interfaces.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProjectRestController {

    @Autowired
    private IGameRepo gameRepository;
    @Autowired
    private IUserRepo playerRepository;
    @Autowired
    private IConfigurationRepo configurationRepository;
    @Autowired
    private IPositionRepo positionRepository;
    @Autowired
    private IConfigurationWordRepo configurationWordRepository;

    @GetMapping("/configurations/{id}")
    public ConfigurationWord getConfigurationById(@PathVariable Integer id) throws ProjectException {
        return configurationWordRepository.findOne(id).orElseThrow(() -> new ProjectException("Configuration not found"));
    }

    @PutMapping("/configurations/{id}")
    public ResponseEntity<?> updateConfigurationWord(@PathVariable Integer id, @RequestBody ConfigurationWord configurationWord) {
        if (!id.equals(configurationWord.getId())) {
            return new ResponseEntity<String>("Path Id and Object Id do not match!", HttpStatus.BAD_REQUEST);
        }
        try {
            ConfigurationWord existingConfigurationWord = configurationWordRepository.findOne(id).orElse(null);
            if (existingConfigurationWord == null) {
                return new ResponseEntity<String>("ConfigurationWord not found", HttpStatus.NOT_FOUND);
            }
            else {
                configurationWordRepository.update(configurationWord);
                System.out.println("ConfigurationWord updated ..." + configurationWord);
                return new ResponseEntity<ConfigurationWord>(configurationWord, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/games/details/{id}")
    public GameDetailsDTO getGameDetails(@PathVariable Integer id) throws ProjectException, SQLException {
        Game game = gameRepository.findOne(id).orElseThrow(() -> new ProjectException("Game not found"));
        List<Position> positions = StreamSupport.stream(positionRepository.findAllByGame(game).spliterator(), false).toList();
        List<PositionDTO> positionDTOs = positions.stream().map(p -> {
            return new PositionDTO(p.getCoordinateX(), p.getCoordinateY(), p.getPositionIndex());
        }).collect(Collectors.toList());
        String playerAlias = game.getPlayer().getUsername();
        List<Word> words = StreamSupport.stream(configurationWordRepository.findWordsByConfiguration(game.getConfiguration()).spliterator(), false).toList();
        Integer score = game.getScore();

        return new GameDetailsDTO(id, positionDTOs, playerAlias, words, score);
    }

}
