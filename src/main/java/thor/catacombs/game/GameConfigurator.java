package thor.catacombs.game;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import thor.catacombs.game.configurators.AttributesConfigurator;
import thor.catacombs.game.configurators.Configurator;
import thor.catacombs.game.configurators.LobbyConfigurator;
import thor.catacombs.game.configurators.VariablesConfigurator;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public record GameConfigurator(Plugin plugin, MiniGameEnvironment environment, FileConfiguration configuration, AttributesConfigurator attributesConfigurator, LobbyConfigurator lobbyConfigurator, VariablesConfigurator variablesConfigurator) implements Configurator {

    @Override
    public FileConfiguration getConfig() {
        return configuration;
    }

    @Override
    public MiniGameEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public AttributeRegistry getRegistry() {
        return attributesConfigurator.getRegistry();
    }

    @Override
    public BlockPosition getLobbySpawnOffset() {
        return lobbyConfigurator.getLobbySpawnOffset();
    }

    @Override
    public Structure getLobby() {
        return lobbyConfigurator.getLobby();
    }

    @Override
    public int getWaitingTime() {
        return variablesConfigurator().getWaitingTime();
    }

    @Override
    public int getGameTime() {
        return variablesConfigurator.getGameTime();
    }

    @Override
    public int getTotal() {
        return variablesConfigurator.getTotal();
    }
}
