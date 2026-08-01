package thor.core.port.mapping.dto.map;

import thor.core.structure.manager.SignalPartManager;

public record AllMapInfo(PlacedMapDto mapDto, SignalPartManager signalPartManager) {
}
