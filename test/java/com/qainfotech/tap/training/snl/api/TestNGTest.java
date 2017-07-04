package com.qainfotech.tap.training.snl.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author varunaggarwal
 */
public class TestNGTest {

//    Board board;
//    BoardModel boardModel;
//
//    // The method setUp() will be invoked after the test class has been built and before any test method is run.
//    @BeforeClass
//    public void setUp() {
//        try {
//            board = new Board();
//            boardModel = new BoardModel();
//        } catch (IOException ex) {
//            Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @Test
    void testMaxPlayerPossible() {
        try {
            Board board = new Board();
            board.registerPlayer("Akash");
            board.registerPlayer("DJ");
            board.registerPlayer("Mr. abc");
            board.registerPlayer("Dr. Mojo");
            board.registerPlayer("3:30 PM");
        } catch (PlayerExistsException | GameInProgressException | MaxPlayersReachedExeption | IOException ex) {
            Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    void addPlayerWithSameName() {
        try {
            Board board = new Board();
            // Trying to register same player 2 times
            for (int i = 0; i < 2; i++) {
                board.registerPlayer("Varun");
            }
        } catch (PlayerExistsException | GameInProgressException | MaxPlayersReachedExeption | IOException ex) {
            Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    void fileNotFound() {
        try {
            Board board = new Board();
            String absoluteFilePath;
            String workingDirectory = "C:\\Users\\varunaggarwal\\Documents\\NetBeansProjects\\assignment-snl-java-api-master";
            absoluteFilePath = workingDirectory + File.separator + board.uuid.toString() + ".board";
            System.out.println("absolute path - " + absoluteFilePath);
            File file = new File(absoluteFilePath);
            Path path = Paths.get(absoluteFilePath);
            //Path path = Paths.get(board.uuid.toString() + ".board");
            System.out.println("path - " + path);
            if (!file.isDirectory() && file.exists()) {
                try {
                    Files.delete(path);
                    try {
                        BoardModel.data(board.uuid);
                    } catch (IOException ex) {
                        Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    void deletePlayer() throws NoUserWithSuchUUIDException, UnsupportedEncodingException, FileNotFoundException, IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {

        Board board = new Board();
        board.registerPlayer("Varun");
        board.registerPlayer("vd");
        board.registerPlayer("Adi");
        board.registerPlayer("Shivam");
        //System.out.println(board.getData().toString());
        UUID uuid = (UUID) board.getData().getJSONArray("players").getJSONObject(0).get("uuid");
        //System.out.println(uuid.toString());

        // Length should be 4 before deleting an element
        assertEquals(4, board.getData().getJSONArray("players").length());

        // Length should be 3 as i have deleted an element
        board.deletePlayer(uuid);
        assertEquals(3, board.getData().getJSONArray("players").length());
    }

    @Test
    void movingUpTheLadder() throws UnsupportedEncodingException, IOException, PlayerExistsException, GameInProgressException, FileNotFoundException, MaxPlayersReachedExeption, InvalidTurnException {
        Board board = new Board();

        JSONArray players = board.getData().getJSONArray("players");
        JSONArray steps = board.getData().getJSONArray("steps");
        
        // Changing data inside JSON File, modifying both target and type
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            double newTarget = 24 + (int) (random.nextDouble() * 10);
            JSONObject stepObj = steps.getJSONObject(i);
            stepObj.put("type", 2);
            stepObj.put("target", newTarget);
        }

        System.out.println("Printing STEPS " + steps.toString());

        board.registerPlayer("Dink");
        UUID uuid = (UUID) players.getJSONObject(0).get("uuid");
        int currentPosition = players.getJSONObject(0).getInt("position");
        
        //System.out.println("Current Position of player is " + currentPosition);
        
        JSONObject response = board.rollDice(uuid);
        UUID playerUuid = (UUID) response.get("playerUuid");
        int dice = response.getInt("dice");
        int flag = 0;
        if (steps.getJSONObject(dice).getInt("target") == players.getJSONObject(0).getInt("position")) {
            flag = 1;
        }
        System.out.println("PLayer is at new position");
        assertEquals(flag, 1);
        
//        int newPosition = players.getJSONObject(0).getInt("position");
//        System.out.println("New Position of player is " + newPosition);

    }
    
     @Test
    void ifBtw95to99() {

    }

    // a player should be allowed to move only if rollDice returns a valid value.
    @Test
    void move() {

    }

    @Test
    void snakeBite() {

    }

    @Test
    void rollDie() {

    }

    @Test
    void while3PlayersFinish() {

    }


//    public static void main(String[] args) throws IOException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, FileNotFoundException, MaxPlayersReachedExeption, InvalidTurnException {
//        TestNGTest t = new TestNGTest();
//        t.movingUpTheLadder();
//    }

}
