package thor.core.port.mapping;

import thor.core.exception.DomainValidationException;
import thor.core.port.mapping.dto.PositionDto;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public final class PositionMapper {
    public static BlockPosition fromDto(List<Integer> position) {
        if (position.size() != 3) {
            throw new DomainValidationException(position);
        }
        return new Point(position.get(0), position.get(1), position.get(2));
    }

    public static BlockPosition fromDto(PositionDto positionDto) {
        return new Point(positionDto.x(), positionDto.y(), positionDto.z());
    }

    public static PositionDto toPositionDto(BlockPosition position) {
        return new PositionDto(position.x(), position.y(), position.z());
    }

    public static List<Integer> toDto(BlockPosition position) {
        return List.of(position.x(), position.y(), position.z());
    }
}
