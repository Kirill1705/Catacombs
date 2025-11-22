package thor.catacombs.game.configurators;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.emptyMiniGame.MiniGameLoader;

public interface Configurator extends LobbyConfigurator, AttributesConfigurator, VariablesConfigurator {
    FileConfiguration getConfig();
    MiniGameEnvironment getEnvironment();
    Plugin getPlugin();
}
