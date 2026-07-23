package thor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import thor.core.port.input.MapEngineService;
import thor.core.port.input.MapService;
import thor.core.port.output.ArenaManager;
import thor.core.port.output.repository.*;
import thor.core.service.MapEngineServiceImpl;
import thor.core.service.MapServiceImpl;
import thor.core.port.output.WorldAccessor;
import thor.infrastructure.ArenaManagerImpl;
import thor.infrastructure.WorldAccessorImpl;
import thor.infrastructure.repositories.*;
import thor.presentation.CustomCommand;
import thor.presentation.GeneratorCommand;
import thor.presentation.MainCommand;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class MainPluginClass extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResources();
        FileConfiguration config = getConfig();
        InfoRepository infoRepository = infoRepository(config);
        ItemRepository itemRepository = itemRepository(config);
        MapConfigHolder mapConfigHolder = mapConfigHolder(config);
        WorldAccessor gameWorldAccessor = gameWorldAccessor(config);
        MapRepository mapRepository = mapRepository(config);
        PlacedMapRepository placedMapRepository = placedMapRepository(config);
        ArenaManager arenaManager = arenaManager(config);
        MapServiceImpl mapService = new MapServiceImpl(infoRepository, mapConfigHolder, itemRepository, mapRepository);
        MapEngineService mapEngineService = new MapEngineServiceImpl(mapRepository, gameWorldAccessor, arenaManager, placedMapRepository);
        MainCommand mainCommand = new MainCommand(commands(config, mapService, mapEngineService), this);
        mainCommand.registerCommands(this);
        registerServices(MapService.class, mapService);
        registerServices(MapEngineService.class, mapEngineService);
    }

    private void saveResources() {
        saveResource("barrel.yml", false);
        saveResource("books.yml", false);
        saveResource("chest.yml", false);
        saveResource("arena.nbt", false);
        File file = new File(getDataFolder(), "rooms");
        if (!file.exists()) {
            saveResource("rooms/example.json", false);
            saveResource("tunnels/horizontal_example.json", false);
            saveResource("tunnels/vertical_example.json", false);
            saveResource("structures/example.nbt", false);
            saveResource("structures/horizontal_example/0.nbt", false);
            saveResource("structures/vertical_example/0.nbt", false);
        }
    }

    private Path toPath(String path) {
        return this.getDataFolder().toPath().resolve(path);
    }

    private InfoRepository infoRepository(FileConfiguration config) {
        return new InfoRepositoryImpl(toPath(config.getString("rooms_path", "rooms")), toPath(config.getString("tunnels_path", "tunnels")));
    }

    private ItemRepository itemRepository(FileConfiguration config) {
        Path chestsPath = toPath(config.getString("chest_path", "chest.yml"));
        Path barrelsPath = toPath(config.getString("barrel_path", "barrel.yml"));
        Path booksPath = toPath(config.getString("books_path", "books.yml"));
        return new ItemRepositoryImpl(booksPath, Map.of("chest", chestsPath, "barrel", barrelsPath));
    }

    private PlacedMapRepository placedMapRepository(FileConfiguration config) {
        return new PlacedMapRepositoryImpl();
    }

    private ArenaManager arenaManager(FileConfiguration config) {
        return new ArenaManagerImpl(toPath(config.getString("arena", "arena.nbt")));
    }

    private MapRepository mapRepository(FileConfiguration config) {
        return new MapRepositoryImpl();
    }

    private MapConfigHolder mapConfigHolder(FileConfiguration config) {
        return new MapConfigHolderImpl(toPath(config.getString("map_config_path", "map.yml")));
    }

    private WorldAccessor gameWorldAccessor(FileConfiguration config) {
        return new WorldAccessorImpl(this, toPath(config.getString("structures_path", "structures")));
    }

    private List<CustomCommand> commands(FileConfiguration config, MapService mapService, MapEngineService engineService) {
        return List.of(
                new GeneratorCommand(mapService, engineService)
        );
    }

    private <T> void registerServices(Class<T> clazz, T service) {
        getServer().getServicesManager().register(
                clazz,
                service,
                this,
                ServicePriority.Normal
        );
    }
}
