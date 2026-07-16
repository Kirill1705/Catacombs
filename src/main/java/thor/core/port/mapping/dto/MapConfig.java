package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

public record MapConfig(BlockPosition mapSize, int roomsQuantity) {
}
