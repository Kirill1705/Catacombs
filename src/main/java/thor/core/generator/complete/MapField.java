package thor.core.generator.complete;

import lombok.Getter;
import org.bukkit.util.BoundingBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.exception.DomainValidationException;

public class MapField {
    private final boolean[][][] map;
    @Getter
    private final Point size;

    public MapField(Point size) {
        if (!size.more(new Point(0, 0, 0))) {
            throw new DomainValidationException(size);
        }

        this.size = size;
        map = new boolean[size.x()][size.y()][size.z()];
    }

    public boolean canPlaceByMap(Iterable<Point> structure) {
        BoundingBox boundingBox = getBox();
        for (Point vector: structure) {
            if (!boundingBox.contains(vector.toBlockVector())||map[vector.x()][vector.y()][vector.z()]) {
                return false;
            }
        }
        return true;
    }

    public void feelMap(Iterable<Point> box) {
        BoundingBox boundingBox = getBox();
        for (Point vector: box) {
            if (boundingBox.contains(vector.toBlockVector())) {
                map[vector.x()][vector.y()][vector.z()] = true;
            }
        }
    }

    private BoundingBox getBox() {
        return new BoundingBox(0, 0, 0, size.x()-1, size.y()-1, size.z()-1);
    }
}
