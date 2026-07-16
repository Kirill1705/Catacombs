package thor.core.port.mapping.dto;

import java.util.List;

public record TunnelInfoDto(
        List<Integer> size,
        String type,
        String id,
        Integer weight,
        List<PartTunnelDescriptionDto> parts,
        Boolean neutral
) {}
