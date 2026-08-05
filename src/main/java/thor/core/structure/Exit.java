package thor.core.structure;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.ExitInfo;
import thor.core.port.output.WorldAccessor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Exit {
    @Getter
    private final Point position;
    @Getter
    private final Material material;
    @Getter
    private final Point offset;
    @Getter
    private final ExitInfo.ExitType type;
    @Getter
    private final List<Point> blocks = new ArrayList<>();
    @Getter
    private final Collection<String> tunnels;

    @Getter
    @Setter
    private boolean closed = false;

    public Exit(Converter converter, ExitInfo info, Collection<String> tunnels, Point roomSize) {
        this.position = converter.toOld(info.getPosition());
        this.material = info.getMaterial();
        this.offset = info.getOffset(roomSize);
        this.type = info.getType(roomSize);
        this.tunnels = tunnels;
        blocks.add(getPosition());
        for (Point block : info.getBlocks()) {
            blocks.add(converter.toOld(block));
        }
    }

    public void place(WorldAccessor accessor, ImmutableLocation location) {
        if (!closed) {
            for (Point position: blocks) {
                accessor.getBlockAt(position.add(location.position()), location.worldName()).setType(material);
            }
        }
    }
}
