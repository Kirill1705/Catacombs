package thor.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thor.core.port.input.MapService;
import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.repository.MapRepository;
import thor.core.structure.create.CatacombsGameMapCreator;

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
