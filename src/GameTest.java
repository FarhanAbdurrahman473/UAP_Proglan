
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

public class GameTest {
    private GameManager gameManager;

    @Before
    public void setUp() {
        gameManager = new GameManager();
    }

    @Test
    public void testAddGame() throws GameException {
        GameData game = new GameData("1", "Test Game", "Action", "Test Dev",
                "2024", 4.5, "test.jpg");
        gameManager.addGame(game);
        assertEquals(1, gameManager.getAllGames().size());
    }

    @Test(expected = GameException.class)
    public void testAddGameWithEmptyTitle() throws GameException {
        GameData game = new GameData("1", "", "Action", "Test Dev",
                "2024", 4.5, "test.jpg");
        gameManager.addGame(game);
    }

    @Test
    public void testUpdateGame() throws GameException {
        GameData game = new GameData("1", "Test Game", "Action", "Test Dev",
                "2024", 4.5, "test.jpg");
        gameManager.addGame(game);

        GameData updatedGame = new GameData("1", "Updated Game", "Action",
                "Test Dev", "2024", 4.5, "test.jpg");
        gameManager.updateGame("1", updatedGame);

        GameData retrievedGame = gameManager.getGameById("1");
        assertEquals("Updated Game", retrievedGame.getTitle());
    }

    @Test
    public void testDeleteGame() throws GameException {
        GameData game = new GameData("1", "Test Game", "Action", "Test Dev",
                "2024", 4.5, "test.jpg");
        gameManager.addGame(game);
        gameManager.deleteGame("1");
        assertEquals(0, gameManager.getAllGames().size());
    }



    public  class TestRunner {
        public static void main(String[] args) {
            System.out.println("Starting tests...");

            Result result = JUnitCore.runClasses(GameTest.class);

            System.out.println("Tests finished.");
            System.out.println("Total tests run: " + result.getRunCount());
            System.out.println("Total tests failed: " + result.getFailureCount());
            System.out.println("Total tests ignored: " + result.getIgnoreCount());


            if (!result.getFailures().isEmpty()) {
                System.out.println("\n--- Failures ---");
                for (Failure failure : result.getFailures()) {
                    System.out.println("Test failed: " + failure.getTestHeader());
                    System.out.println("Message: " + failure.getMessage());
                    System.out.println("Trace: ");
                    System.out.println(failure.getTrace());
                }
            }


            if (result.wasSuccessful()) {
                System.out.println("\nAll tests passed!");
            } else {
                System.out.println("\nSome tests failed. Check the details above.");
            }
        }
    }

}