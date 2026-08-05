package thor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import thor.core.port.input.MapEngineService;
import thor.core.port.input.MapService;
import thor.core.port.input.StructureInfoService;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.repository.*;
import thor.core.service.MapEngineServiceImpl;
import thor.core.service.MapServiceImpl;
import thor.core.service.StructureInfoServiceImpl;
import thor.core.structure.create.CatacombsGameMapCreator;
import thor.core.structure.create.CatacombsInteractiveMapCreator;
import thor.core.structure.manager.config.ReloadableArenaConfig;
import thor.infrastructure.StructureManagerImpl;
import thor.infrastructure.WorldAccessorImpl;
import thor.infrastructure.repositories.*;
import thor.presentation.CatacombsListener;
import thor.presentation.CustomCommand;
import thor.presentation.GeneratorCommand;
import thor.presentation.MainCommand;
import ru.vikhrenko.serverUtils.reload.CommandManager;

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
        ReloadableInfoRepository reloadableInfoRepository = new ReloadableInfoRepository(infoRepository);
        ItemRepository itemRepository = itemRepository(config);
        ReloadableItemRepository reloadableItemRepository = new ReloadableItemRepository(itemRepository);
        MapConfigHolderImpl mapConfigHolder = mapConfigHolder(config);
        WorldAccessor gameWorldAccessor = new WorldAccessorImpl();
        MapRepository mapRepository = mapRepository(config);
        MapPlacedRepository mapPlacedRepository = new MapPlacedRepositoryImpl();
        ReloadableArenaConfig arenaConfig = new ReloadableArenaConfig(getDataPath());
        StructureManager structureManager = structureManager(config);

        new CommandManager().registerReloadCommand(this, List.of(reloadableInfoRepository, reloadableItemRepository, arenaConfig, mapConfigHolder));

        CatacombsGameMapCreator catacombsCreator = new CatacombsGameMapCreator(mapConfigHolder, infoRepository);
        CatacombsInteractiveMapCreator interactiveMapCreator = new CatacombsInteractiveMapCreator(itemRepository, arenaConfig, structureManager);

        MapServiceImpl mapService = new MapServiceImpl(mapRepository, catacombsCreator);
        MapEngineService mapEngineService = new MapEngineServiceImpl(mapRepository, mapPlacedRepository, interactiveMapCreator, gameWorldAccessor);
        StructureInfoService structureInfoService = new StructureInfoServiceImpl(reloadableInfoRepository, structureManager);

        MainCommand mainCommand = new MainCommand(commands(config, mapService, mapEngineService), this);
        mainCommand.registerCommands(this);
        CatacombsListener listener = new CatacombsListener(mapEngineService);
        getServer().getPluginManager().registerEvents(listener, this);
        registerServices(MapService.class, mapService);
        registerServices(MapEngineService.class, mapEngineService);
        registerServices(StructureInfoService.class, structureInfoService);
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
            saveResource("water", false);
        }
    }

    private Path toPath(String path) {
        return this.getDataFolder().toPath().resolve(path);
    }

    private InfoRepository infoRepository(FileConfiguration config) {
        return new InfoRepositoryImpl(toPath(config.getString("rooms_path", "rooms")), toPath(config.getString("tunnels_path", "tunnels")), toPath(config.getString("water_islands_path", "water")));
    }

    private ItemRepository itemRepository(FileConfiguration config) {
        Path chestsPath = toPath(config.getString("chest_path", "chest.yml"));
        Path barrelsPath = toPath(config.getString("barrel_path", "barrel.yml"));
        Path booksPath = toPath(config.getString("books_path", "books.yml"));
        return new ItemRepositoryImpl(booksPath, Map.of("chest", chestsPath, "barrel", barrelsPath));
    }

    private MapRepository mapRepository(FileConfiguration config) {
        return new MapRepositoryImpl();
    }

    private MapConfigHolderImpl mapConfigHolder(FileConfiguration config) {
        return new MapConfigHolderImpl();
    }

    private StructureManager structureManager(FileConfiguration config) {
        return new StructureManagerImpl(toPath(config.getString("structures_path", "structures")));
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
