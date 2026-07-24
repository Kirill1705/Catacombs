package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public record TunnelInfoDto(
        Point size,
        String type,
        String id,
        Integer weight,
        List<PartTunnelDescriptionDto> parts,
        Boolean neutral
) {}
