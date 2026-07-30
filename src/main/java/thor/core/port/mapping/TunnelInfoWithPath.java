package thor.core.port.mapping;

import thor.core.port.mapping.dto.TunnelInfoDto;

import java.util.List;

public record TunnelInfoWithPath(TunnelInfoDto tunnelInfo, List<String> paths) {
}
