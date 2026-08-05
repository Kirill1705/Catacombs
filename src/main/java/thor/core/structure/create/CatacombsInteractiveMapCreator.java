package thor.core.structure.create;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.generator.complete.GameMap;
import thor.core.generator.complete.MapType;
import thor.core.info.ItemInfo;
import thor.core.info.part.FillType;
import thor.core.port.mapping.ItemMapper;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.repository.ItemRepository;
import thor.core.structure.AddInfoStructureVisitor;
import thor.core.structure.CatacombsPlaceStructureVisitor;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.create.builder.InteractiveMapBuilder;
import thor.core.structure.create.builder.InteractivePartStage;
import thor.core.structure.create.builder.MapBuilder;
import thor.core.structure.manager.*;
import thor.core.structure.manager.config.ArenaConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CatacombsInteractiveMapCreator {
    private final ItemRepository itemRepository;
    private final ArenaConfig arenaConfig;
    private final StructureManager structureManager;

    public InteractiveGameMapWithBoxes create(Map<MapType, GameMap> maps, ImmutableLocation location, WorldAccessor accessor, MapPlaceOptions options) {
        GameMap mainMap = maps.get(MapType.MAIN);
        GameMap waterMap = maps.getOrDefault(MapType.WATER, null);

        ItemCreator itemCreator = new ItemCreator(getItems());
        ChestManager chestManager = new ChestManager(itemCreator);
        PlayerSpawnManager playerSpawnManager = new PlayerSpawnManager(location);
        TeleportManager teleportManager = new TeleportManager();
        ArenaButtonManager arenaButtonManager = new ArenaButtonManager(arenaConfig, mainMap.getSize(), location, accessor, options);
        EffectNodeManager effectNodeManager = new EffectNodeManager();
        PortalManager portalManager = new PortalManager(location.worldName(), MapType.WATER.getWorldName(location.worldName()));

        CatacombsPlaceStructureVisitor mainVisitor = new CatacombsPlaceStructureVisitor(structureManager, mainMap.getSize(), options, location, Material.STONE);

        InteractivePartStage builder = InteractiveMapBuilder.builder()
                .withWorldAccessor(accessor)
                .withPlayerTeleportator(playerSpawnManager)
                .withArenaTeleportator(arenaButtonManager)
                .withPlace(chestManager)
                .withPortalHandler(portalManager)
                .withStructurePartManager(chestManager)
                .withStructurePartManager(playerSpawnManager)
                .withStructurePartManager(teleportManager)
                .withStructurePartManager(arenaButtonManager)
                .withStructurePartManager(effectNodeManager)
                .withStructurePartManager(portalManager)
                .withSignalManager(teleportManager)
                .withSignalManager(arenaButtonManager)
                .withSignalManager(effectNodeManager);
        MapBuilder mapBuilder = directMainWorld(builder, mainMap, mainVisitor, location);
        if (waterMap != null) {
            mapBuilder = directWater(mapBuilder, waterMap, options, location);
        }
        InteractiveGameMapWithBoxes result = mapBuilder.build();
        playerSpawnManager.addTunnels(mainVisitor.getHorizontalTunnels());
        return result;
    }

    private MapBuilder directMainWorld(InteractivePartStage builder, GameMap mainMap, CatacombsPlaceStructureVisitor visitor, ImmutableLocation mainLocation) {
        return builder.addDimension()
                .withLocation(mainLocation)
                .withPlaceManager(visitor)
                .withBox(Boxes.fromBeginAndSize(mainLocation.position(), mainMap.getSize()))
                .withStructures(mainMap.getAllStructures())
                .withVisitor(visitor);
    }

    private MapBuilder directWater(MapBuilder builder, GameMap waterMap, MapPlaceOptions options, ImmutableLocation mainLocation) {
        ImmutableLocation waterLocation = getWaterLocation(mainLocation);
        CatacombsPlaceStructureVisitor waterVisitor = new CatacombsPlaceStructureVisitor(structureManager, waterMap.getSize(), options, waterLocation, Material.WATER);
        return builder.addDimension()
                .withLocation(waterLocation)
                .withPlaceManager(waterVisitor)
                .withBox(Boxes.fromBeginAndSize(mainLocation.position(), waterMap.getSize()))
                .withStructures(waterMap.getAllStructures())
                .withVisitor(waterVisitor);
    }

    private List<ItemInfo> getItems() {
        List<ItemInfo> items = new ArrayList<>();
        List<String> fillTypes = Arrays.stream(FillType.values())
                .map(fillType -> fillType.name().toLowerCase())
                .toList();
        items.addAll(itemRepository.getItems(fillTypes).stream()
                .map(ItemMapper::fromDto)
                .toList());
        items.addAll(itemRepository.getBooks().stream()
                .map(ItemMapper::fromDto)
                .toList());
        return items;
    }

    private ImmutableLocation getWaterLocation(ImmutableLocation location) {
        return new ImmutableLocation(location.position(), MapType.WATER.getWorldName(location.worldName()));
    }
}
