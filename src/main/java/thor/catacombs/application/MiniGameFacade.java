package thor.catacombs.application;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import thor.catacombs.commands.structureHolder.StructureLoaderFacade;
import thor.catacombs.game.GameConfigurator;
import thor.catacombs.game.MyListener;
import thor.catacombs.game.configurators.AttributesConfigurator;
import thor.catacombs.game.configurators.GameLobbyConfigurator;
import thor.catacombs.game.configurators.GameVariableConfigurator;
import thor.catacombs.generator.map.GeneratorCreator;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.emptyMiniGame.MiniGameLoader;
import thor.lobby.commands.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MiniGameFacade {
    public MiniGameFacade(Plugin plugin, FileConfiguration configuration, GeneratorCreator creator, AttributesConfigurator configurator, StructureLoaderFacade holder, MiniGameEnvironment environment) {
        GameFactory factory = new GameFactory(holder, new GameConfigurator(
                plugin,
                environment,
                configuration,
                configurator,
                new GameLobbyConfigurator(environment, configuration, plugin),
                new GameVariableConfigurator()
        ), environment, creator, plugin, configuration);
        MiniGameLoader loader = factory.getLoader();
        loader.setTeamViewer(player -> {
            Team team = Team.getTeam(player);
            if (team==null) return List.of(player);
            Set<Player> players = team.getPlayers();
            for (Player teammate: players) {
                team.removePlayer(teammate);
            }
            return new ArrayList<>(players);
        });
        plugin.getServer().getPluginManager().registerEvents(new MyListener(loader), plugin);
    }
}
