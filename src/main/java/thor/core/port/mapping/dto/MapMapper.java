package thor.core.port.mapping.dto;

import thor.core.generator.complete.GameMap;
import thor.core.generator.complete.MapGraph;
import thor.core.port.mapping.StructuresMapper;
import thor.core.port.mapping.dto.map.MapDto;

public class MapMapper {
    public static MapDto toDto(GameMap map) {
        return new MapDto(
                map.getUuid(),
                map.getGraph().getEdges().stream().map(EdgeMapper::toDto).toList(),
                map.getPlayerSpawnPlaces().stream().map(StructuresMapper::toDto).toList()
        );
    }
}
