package thor.core.structure.create;

import lombok.RequiredArgsConstructor;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.complete.GameMapImpl;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.info.ItemInfo;
import thor.core.info.part.FillType;
import thor.core.port.mapping.ItemMapper;
import thor.core.port.mapping.StructureInfoMapper;
import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.StructureManager;
import thor.core.port.output.repository.InfoRepository;
import thor.core.port.output.repository.ItemRepository;
import thor.core.port.output.repository.MapConfigHolder;
import thor.core.structure.AddInfoStructureVisitor;
import thor.core.structure.CatacombsPlaceStructureVisitor;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.manager.*;
import thor.core.structure.manager.config.ArenaConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CatacombsGameMapCreator {
    private final MapConfigHolder mapConfigHolder;
    private final InfoRepository infoRepository;
    private final ItemRepository itemRepository;
    private final ArenaConfig arenaConfig;
    private final StructureManager structureManager;

    public InteractiveGameMap create() {
        GameMap gameMap = new GameMapImpl(mapConfigHolder.getConfig().mapSize());
        GroundRoomGenerator roomGenerator = new GroundRoomGenerator(infoRepository.getRooms().stream().map(StructureInfoMapper::fromDto).toList(), mapConfigHolder.getConfig().roomsQuantity(), new SimpleRoomCreator());
        GroundTunnelGenerator tunnelGenerator = new GroundTunnelGenerator(infoRepository.getTunnels().stream().map(StructureInfoMapper::fromDto).toList(), new PartTunnelCreatorImpl());
        roomGenerator.generate(gameMap);
        tunnelGenerator.generate(gameMap);

        ItemCreator itemCreator = new ItemCreator(getItems());
        ChestManager chestManager = new ChestManager(itemCreator);
        PlayerSpawnManager playerSpawnManager = new PlayerSpawnManager();
        TeleportManager teleportManager = new TeleportManager();
        ArenaButtonManager arenaButtonManager = new ArenaButtonManager(arenaConfig, mapConfigHolder.getConfig().mapSize());
        EffectNodeManager effectNodeManager = new EffectNodeManager();

        AddInfoStructureVisitor structureVisitor = new AddInfoStructureVisitor(List.of(chestManager, playerSpawnManager, teleportManager, arenaButtonManager, effectNodeManager));
        CatacombsPlaceStructureVisitor placeVisitor = new CatacombsPlaceStructureVisitor(structureManager, mapConfigHolder.getConfig().mapSize());
        gameMap.getAllStructures().forEach(structure -> structure.accept(structureVisitor));
        gameMap.getAllStructures().forEach(structure -> structure.accept(placeVisitor));

        playerSpawnManager.addTunnels(placeVisitor.getHorizontalTunnels());

        List<SignalPartManager> signalPartManagers = List.of(teleportManager, arenaButtonManager, effectNodeManager);
        List<PlacePartManager> placePartManagers = List.of(placeVisitor, arenaButtonManager, chestManager);

        return new InteractiveGameMap(
                UUID.randomUUID(),
                signalPartManagers,
                playerSpawnManager,
                arenaButtonManager,
                placePartManagers
        );
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
}
