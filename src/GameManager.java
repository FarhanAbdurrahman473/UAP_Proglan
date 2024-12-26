
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<GameData> games;

    public GameManager() {
        this.games = new ArrayList<>();
    }

    public void addGame(GameData game) throws GameException {
        if (game.getTitle().isEmpty()) {
            throw new GameException("Game title cannot be empty");
        }
        games.add(game);
    }

    public void updateGame(String id, GameData updatedGame) throws GameException {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId().equals(id)) {
                games.set(i, updatedGame);
                return;
            }
        }
        throw new GameException("Game not found with ID: " + id);
    }

    public void deleteGame(String id) throws GameException {
        games.removeIf(game -> game.getId().equals(id));
    }

    public List<GameData> getAllGames() {
        return new ArrayList<>(games);
    }

    public GameData getGameById(String id) throws GameException {
        return games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new GameException("Game not found with ID: " + id));
    }
}