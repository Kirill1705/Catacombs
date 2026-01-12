package thor.catacombs.application;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import thor.catacombs.commands.structureHolder.StructureLoaderFacade;
import thor.catacombs.game.Game;
import thor.catacombs.game.configurators.Configurator;
import thor.catacombs.generator.map.GeneratorCreator;
import thor.emptyMiniGame.MiniGame;
import thor.emptyMiniGame.MiniGameCreator;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.emptyMiniGame.MiniGameLoader;

public class GameFactory implements MiniGameCreator {
    private final StructureLoaderFacade holder;
    private final Configurator configurator;
    private final MiniGameLoader loader;
    private final GeneratorCreator creator;
    private final Plugin plugin;
    private final FileConfiguration config;

    public MiniGameLoader getLoader() {
        return loader;
    }

    public GameFactory(StructureLoaderFacade holder, Configurator configurator, MiniGameEnvironment environment, GeneratorCreator creator, Plugin plugin, FileConfiguration config) {
        this.holder = holder;
        this.configurator = configurator;
        loader = environment.buildLoader(this);
        this.creator = creator;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public MiniGame create(Location loc) {
        return new Game(loc, creator.create(holder.getRooms(), holder.getTunnels()), configurator, plugin, loader, config);
    }
}
