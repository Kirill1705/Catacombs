package thor.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.complete.GameMapImpl;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.info.ItemInfo;
import thor.core.info.part.FillType;
import thor.core.port.input.MapService;
import thor.core.port.mapping.ItemMapper;
import thor.core.port.mapping.StructureInfoMapper;
import thor.core.port.mapping.dto.MapConfig;
import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.repository.InfoRepository;
import thor.core.port.output.repository.ItemRepository;
import thor.core.port.output.repository.MapConfigHolder;
import thor.core.port.output.repository.MapRepository;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.create.CatacombsGameMapCreator;
import thor.core.structure.create.PartTunnelCreatorImpl;
import thor.core.structure.create.SimpleRoomCreator;
import thor.core.structure.manager.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;
    private final CatacombsGameMapCreator catacombsGameMapCreator;

    @Override
    public void removeMap(UUID mapId) {
        mapRepository.delete(mapId);
    }

    @Override
    public UUID generateMap() {
        InteractiveGameMap map = catacombsGameMapCreator.create();
        mapRepository.addMap(map);
        return map.id();
    }
}
