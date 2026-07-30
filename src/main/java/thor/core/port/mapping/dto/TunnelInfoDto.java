package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record TunnelInfoDto(
        Point size,
        String type,
        String id,
        Integer weight,
        List<PartTunnelDescriptionDto> parts,
        Boolean neutral
) {}
