package thor.core.port.mapping.dto;

import thor.core.generator.complete.Edge;
import thor.core.port.mapping.StructuresMapper;
import thor.core.port.mapping.dto.map.EdgeDto;

public class EdgeMapper {
    public static EdgeDto toDto(Edge edge) {
        return new EdgeDto(
                StructuresMapper.toDto(edge.from()),
                StructuresMapper.toDto(edge.to()),
                edge.tunnels().stream().map(StructuresMapper::toDto).toList()
        );
    }
}
