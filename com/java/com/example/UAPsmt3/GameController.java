package com.example.UAPsmt3;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/games"})
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    public GameController() {
    }

    @GetMapping
    public List<Game> getAllGames() {
        return this.gameRepository.findAll();
    }

    @PostMapping
    public Game addGame(@RequestBody Game game) {
        System.out.println("Saving game: " + String.valueOf(game));
        return (Game)this.gameRepository.save(game);
    }

    @PutMapping({"/{id}"})
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        game.setId(id);
        return (Game)this.gameRepository.save(game);
    }

    @DeleteMapping({"/{id}"})
    public void deleteGame(@PathVariable Long id) {
        this.gameRepository.deleteById(id);
    }
}
