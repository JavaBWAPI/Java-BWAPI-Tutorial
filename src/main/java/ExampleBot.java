import bwapi.*;

public class ExampleBot extends DefaultBWListener {
    BWClient bwClient;
    Game game;

    @Override
    public void onStart() {
        game = bwClient.getGame();
    }

    @Override
    public void onFrame() {
        Player self = game.self();
        game.drawTextScreen(20, 20, self.getName() + " has " + self.minerals() + " minerals");

        // Train units while we can
        for (Unit trainer : self.getUnits()) {
            UnitType unitType = trainer.getType();
            if (unitType.isBuilding() && !unitType.buildsWhat().isEmpty()) {
                UnitType toTrain = unitType.buildsWhat().get(0);
                if (game.canMake(toTrain, trainer)) {
                    trainer.train(toTrain);
                }
            }
        }
    }

    @Override
    public void onUnitComplete(Unit unit) {
        if (unit.getType().isWorker()) {
            Unit closestMineral = null;
            int closestDistance = Integer.MAX_VALUE;
            for (Unit mineral : game.getMinerals()) {
                int distance = unit.getDistance(mineral);
                if (distance < closestDistance) {
                    closestMineral = mineral;
                    closestDistance = distance;
                }
            }
            unit.gather(closestMineral);
        }
    }

    void run() {
        bwClient = new BWClient(this);
        bwClient.startGame();
    }

    public static void main(String[] args) {
        new ExampleBot().run();
    }
}
