package org.rest.rest.services;

import model.Game;
import model.GamePosition;
import model.PositionsStatus;
import model.User;
import org.rest.rest.dto.BoatPlacementRequest;
import org.rest.rest.dto.GameDetailsDTO;
import org.rest.rest.dto.GamePositionDTO;
import org.rest.rest.dto.ShotDTO;
import org.server.service.GamePositionService;
import org.server.service.UserService;
import org.services.ProjectException;
import org.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/games")
public class ProjectRestController {
    private final ProjectServices projectServices;

    @Autowired
    public ProjectRestController(ProjectServices projectServices) {
        this.projectServices = projectServices;
    }


    @GetMapping("/player/{playerId}/successful")
    public ResponseEntity<?> getPlayerGamesWithSuccessfulShots(@PathVariable Integer playerId) {
        try {
            List<Game> allUserGames = new ArrayList<>();
            for (Game game : projectServices.getFinishedGames()) { // Sau projectServices.getAllGames()
                if (game.getUser_id() != null && game.getUser_id().getId().equals(playerId)) {
                    allUserGames.add(game);
                }
            }

            List<GameDetailsDTO> gameDetailsList = new ArrayList<>();

            for (Game game : allUserGames) {
                if (game.getSuccessful_shots() > 0) {
                    List<GamePosition> revealedPositions = projectServices.getRevealedGamePositions(game.getId());
                    List<ShotDTO> playerShots = revealedPositions.stream()
                            .sorted(Comparator.comparing(GamePosition::getShotOrder, Comparator.nullsLast(Integer::compareTo)))
                            .map(pos -> new ShotDTO(pos.getX(), pos.getY(), pos.getStatus(),
                                    pos.getStatus().equals(PositionsStatus.MISS.toString()) ? calculateDistanceInController(game.getId(), pos.getX(), pos.getY(), projectServices) : 0))
                            .collect(Collectors.toList());

                    List<GamePosition> boatPositions = projectServices.getBoatGamePositions(game.getId());
                    List<GamePositionDTO> boatPositionsDTO = boatPositions.stream()
                            .map(pos -> new GamePositionDTO(pos.getX(), pos.getY()))
                            .collect(Collectors.toList());

                    gameDetailsList.add(new GameDetailsDTO(
                            game.getId(),
                            game.getUser_id(), // The User object linked to the game
                            game.getScore(),
                            game.getSuccessful_shots(),
                            game.getTotal_shots(),
                            game.getStatus(),
                            game.getStart_time(),
                            game.getEnd_time(),
                            playerShots,
                            boatPositionsDTO
                    ));
                }
            }

            return new ResponseEntity<>(gameDetailsList, HttpStatus.OK);

        } catch (ProjectException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private double calculateDistanceInController(int gameId, int x, int y, ProjectServices projectServices) {
        try {
            List<GamePosition> boatPositions = projectServices.getBoatGamePositions(gameId);
            if (boatPositions.isEmpty()) {
                return Double.MAX_VALUE;
            }
            return boatPositions.stream()
                    .mapToDouble(p -> Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2)))
                    .min().orElse(0);
        } catch (ProjectException e) {
            return 0;
        }
    }


    @PostMapping("/boats")
    public ResponseEntity<?> addBoat(@RequestBody BoatPlacementRequest request) {
        try {
            List<GamePositionDTO> positions = request.getPositions();

            if (positions == null || positions.size() != 3) {
                return new ResponseEntity<>("A boat must consist of exactly 3 positions.", HttpStatus.BAD_REQUEST);
            }

            if (!isValidBoatPlacement(positions)) {
                return new ResponseEntity<>("Boat positions must be contiguous either horizontally or vertically.", HttpStatus.BAD_REQUEST);
            }

            // Dacă scopul este să creezi un NOU joc cu o barcă specifică, ar arăta așa:

//            Optional<User> testUserOp = projectServices.findUserById(2); // Get a user
//            if (testUserOp.isEmpty()) {
//                return new ResponseEntity<>("Test user for boat placement not found", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            User testUser = testUserOp.get();
//            Game newGame = projectServices.createGame(testUser); // This will create random boat, but we override
//            for(GamePositionDTO posDto : positions) {
//                // Find GamePosition in newGame, set hasBoat=true, update in DB
//                GamePosition gp = projectServices.findByGameAndCoordinates(newGame.getId(), posDto.getX(), posDto.getY());
//                if (gp != null) {
//                    gp.setHasBoat(true);
//                    ((GamePositionService)projectServices).update(gp);
//                }
//            }
//            return new ResponseEntity<>("Boat placed in new game " + newGame.getId(), HttpStatus.CREATED);


            return new ResponseEntity<>("Boat positions are valid. (Actual placement needs a Game ID)", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidBoatPlacement(List<GamePositionDTO> positions) {
        if (positions.size() != 3) {
            return false;
        }

        List<Point> points = positions.stream()
                .map(p -> new Point(p.getX(), p.getY()))
                .collect(Collectors.toList());

        points.sort(Comparator.comparingInt(Point::getX).thenComparingInt(Point::getY));

        boolean isHorizontal = true;
        for (int i = 0; i < 2; i++) {
            if (points.get(i).getY() != points.get(i+1).getY() ||
                    points.get(i).getX() + 1 != points.get(i+1).getX()) {
                isHorizontal = false;
                break;
            }
        }
        if (isHorizontal) {
            return true;
        }

        boolean isVertical = true;
        points.sort(Comparator.comparingInt(Point::getY).thenComparingInt(Point::getX)); // Sort by Y then X
        for (int i = 0; i < 2; i++) {
            if (points.get(i).getX() != points.get(i+1).getX() || // Same column
                    points.get(i).getY() + 1 != points.get(i+1).getY()) { // Y coordinates are consecutive
                isVertical = false;
                break;
            }
        }
        return isVertical;
    }

    private static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
        public int getX() { return x; }
        public int getY() { return y; }
    }

}
