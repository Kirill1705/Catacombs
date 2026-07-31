package thor.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.RoomGenerator;
import thor.core.generator.TunnelGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.info.ItemInfo;
import thor.core.info.part.FillType;
import thor.core.port.input.MapService;
import thor.core.port.mapping.ItemMapper;
import thor.core.port.mapping.StructureInfoMapper;
import thor.core.port.mapping.dto.MapConfig;
import thor.core.port.output.repository.InfoRepository;
import thor.core.port.output.repository.ItemRepository;
import thor.core.port.output.repository.MapConfigHolder;
import thor.core.port.output.repository.MapRepository;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.create.PartTunnelCreatorImpl;
import thor.core.structure.create.SimpleRoomCreator;
import thor.core.structure.create.StructurePartsHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class MapServiceImpl implements MapService {
    private final InfoRepository infoRepository;
    private final MapConfigHolder mapConfigHolder;
    private final ItemRepository itemRepository;
    private final MapRepository mapRepository;

    @Override
    public void removeMap(UUID mapId) {
        mapRepository.delete(mapId);
    }

    @Override
    public UUID generateMap() {
        MapConfig mapConfig = mapConfigHolder.getConfig();
        StructurePartsHolder partsHolder = new StructurePartsHolder(new ItemCreator(getItems()));
        GameMap gameMap = new GameMap(mapConfig.mapSize(), partsHolder);
        RoomGenerator roomGenerator = new GroundRoomGenerator(infoRepository.getRooms().stream().map(StructureInfoMapper::fromDto).toList(), mapConfig.roomsQuantity(), new SimpleRoomCreator());
        roomGenerator.generate(gameMap);
        TunnelGenerator tunnelGenerator = new GroundTunnelGenerator(infoRepository.getTunnels().stream().map(StructureInfoMapper::fromDto).toList(), new PartTunnelCreatorImpl());
        tunnelGenerator.generateTunnels(gameMap);
        mapRepository.addMap(gameMap);
        return gameMap.getUuid();
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
