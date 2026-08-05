package thor.core.structure.create;

import lombok.RequiredArgsConstructor;
import thor.core.generator.IslandGenerator;
import thor.core.generator.complete.*;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.info.IslandInfo;
import thor.core.port.mapping.StructureInfoMapper;
import thor.core.port.output.repository.InfoRepository;
import thor.core.port.output.repository.MapConfigHolder;
import thor.core.structure.PortalCountVisitor;
import thor.core.structure.Structure;
import thor.core.util.RandomGeneratorImpl;

import java.util.*;

@RequiredArgsConstructor
public class CatacombsGameMapCreator {
    private final MapConfigHolder mapConfigHolder;
    private final InfoRepository infoRepository;

    public GeneratedGameMap create() {
        GameMap gameMap = new GameMapImpl(mapConfigHolder.catacombsMapSize());
        IslandGenerator roomGenerator = new IslandGenerator(mapConfigHolder.roomsQuantity(), new RoomCreator(new RandomGeneratorImpl<>(infoRepository.getRooms().stream().map(StructureInfoMapper::fromDto).toList())));
        GroundTunnelGenerator tunnelGenerator = new GroundTunnelGenerator(infoRepository.getTunnels().stream().map(StructureInfoMapper::fromDto).toList(), new PartTunnelCreatorImpl());
        roomGenerator.generate(gameMap);
        tunnelGenerator.generate(gameMap);

        Map<MapType, GameMap> maps = new HashMap<>();
        maps.put(MapType.MAIN, gameMap);

        ConstraintsGameMap waterMap = createConstraintMap(gameMap.getAllStructures());
        if (waterMap == null) {
            return new GeneratedGameMap(maps);
        }
        IslandGenerator islandsGenerator = new IslandGenerator(mapConfigHolder.getWaterIslandsQuantity(), waterMap);
        islandsGenerator.generate(waterMap);
        maps.put(MapType.WATER, waterMap);
        return new GeneratedGameMap(maps);
    }

    private ConstraintsGameMap createConstraintMap(List<Structure> structures) {
        PortalCountVisitor countVisitor = new PortalCountVisitor();
        structures.forEach(structure -> structure.accept(countVisitor));
        int portalCount = countVisitor.getCount();
        List<IslandInfo> islandInfos = infoRepository.getWaterIslands().stream().map(StructureInfoMapper::fromDto).toList();
        if (islandInfos.isEmpty()) return null;
        List<IslandInfo> portals = islandInfos.stream()
                .filter(islandInfo -> !islandInfo.getPortals().isEmpty())
                .toList();
        List<IslandInfo> noPortals = islandInfos.stream()
                .filter(islandInfo -> islandInfo.getPortals().isEmpty())
                .toList();
        return new ConstraintsGameMap(new GameMapImpl(mapConfigHolder.waterWorldSize()), Map.of(new IslandsCreator(new RandomGeneratorImpl<>(portals)), portalCount), new IslandsCreator(new RandomGeneratorImpl<>(noPortals)));
    }
}
