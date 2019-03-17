import bwapi.BWClient;
import bwapi.DefaultBWListener;
import bwapi.Game;

public class ExampleBot extends DefaultBWListener {
    BWClient bwClient;
    Game game;

    @Override
    public void onStart() {
        game = bwClient.getGame();
    }

    @Override
    public void onFrame() {
        game.drawTextScreen(100, 100, "Hello World!");
    }

    void run() {
        bwClient = new BWClient(this);
        bwClient.startGame();
    }

    public static void main(String[] args) {
        new ExampleBot().run();
    }
}