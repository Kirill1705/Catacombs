package thor.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.RoomGenerator;
import thor.core.generator.TunnelGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.generator.tunnel.make.TunnelPartsDispenserImpl;
import thor.core.info.ItemInfo;
import thor.core.info.part.FillType;
import thor.core.port.input.LocationDto;
import thor.core.port.input.MapService;
import thor.core.port.mapping.ItemMapper;
import thor.core.port.mapping.StructureInfoMapper;
import thor.core.port.mapping.dto.MapConfig;
import thor.core.port.mapping.dto.MapMapper;
import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.port.mapping.dto.map.MapDto;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.repository.MapRepository;
import thor.core.port.output.repository.InfoRepository;
import thor.core.port.output.repository.ItemRepository;
import thor.core.port.output.repository.MapConfigHolder;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.create.PartTunnelCreatorImpl;
import thor.core.structure.create.SimpleRoomCreator;
import thor.core.world.MapPlacer;

import java.util.*;

@AllArgsConstructor
@Slf4j
public class MapServiceImpl implements MapService {
    private final InfoRepository infoRepository;
    private final MapConfigHolder mapConfigHolder;
    private final ItemRepository itemRepository;
    private final WorldAccessor accessor;
    private final MapRepository mapRepository;

    @Override
    public void placeMap(LocationDto location, boolean placeBedrock, boolean fillStone, UUID mapId) {
        Optional<GameMap> gameMap = mapRepository.findById(mapId);
        if (gameMap.isEmpty()) {
            throw new IllegalArgumentException();
        }
        MapPlacer mapPlacer = new MapPlacer(gameMap.get(), accessor);
        mapPlacer.place(location, placeBedrock, fillStone);
        log.info("Map generated successfully");
    }

    @Override
    public MapDto generateMap() {
        MapConfig mapConfig = mapConfigHolder.getConfig();
        ItemCreator itemCreator = new ItemCreator(getItems());
        RoomGenerator roomGenerator = new GroundRoomGenerator(infoRepository.getRooms().stream().map(StructureInfoMapper::fromDto).toList(), mapConfig.mapSize(), mapConfig.roomsQuantity(), new SimpleRoomCreator(itemCreator));
        GameMap gameMap = roomGenerator.generate();
        TunnelGenerator tunnelGenerator = new GroundTunnelGenerator(infoRepository.getTunnels().stream().map(StructureInfoMapper::fromDto).toList(), new TunnelPartsDispenserImpl(0.3), new PartTunnelCreatorImpl(itemCreator));
        tunnelGenerator.generateTunnels(gameMap);
        mapRepository.addMap(gameMap);
        return MapMapper.toDto(gameMap);
    }

    public boolean addRoom(RoomInfoDto dto, boolean force) {
        boolean success = infoRepository.exportRoomInfo(dto);
        if (force && !success) {
            infoRepository.updateRoomInfo(dto);
            return true;
        }
        return false;
    }

    public boolean addTunnel(TunnelInfoDto dto, boolean force) {
        boolean success = infoRepository.exportTunnelInfo(dto);
        if (force && !success) {
            infoRepository.updateTunnelInfo(dto);
            return true;
        }
        return false;
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
