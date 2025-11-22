package thor.catacombs.game.configurators;

public class GameVariableConfigurator implements VariablesConfigurator{
    @Override
    public int getWaitingTime() {
        return 30;
    }

    @Override
    public int getGameTime() {
        return 1800;
    }

    @Override
    public int getTotal() {
        return 10;
    }
}
