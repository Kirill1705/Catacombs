package thor.generator;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import thor.core.info.RoomInfo;
import thor.core.info.TunnelInfo;
import thor.core.info.part.ExitInfo;
import thor.core.info.part.PartTunnelDescription;
import thor.core.info.part.TunnelType;
import thor.core.info.part.Weight;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

@Getter
public enum SimpleStructuresInfo {
    VALUE;
    private final RoomInfo roomInfo;
    private final List<TunnelInfo> tunnelInfos;

    SimpleStructuresInfo() {
        Point size = new Point(5, 5, 5);
        List<ExitInfo> exits = List.of(
                new ExitInfo(null, new Point(0, 1, 2), List.of()),
                new ExitInfo(null, new Point(2, 1, 0), List.of()),
                new ExitInfo(null, new Point(2, 0, 2), List.of()),
                new ExitInfo(null, new Point(4, 1, 2), List.of()),
                new ExitInfo(null, new Point(2, 1, 4), List.of()),
                new ExitInfo(null, new Point(2, 4, 2), List.of())
        );
        roomInfo = new RoomInfo(
                new Weight(1),
                exits,
                "test",
                List.of(),
                size,
                List.of(),
                List.of(),
                null
        );
        tunnelInfos = List.of(new TunnelInfo(
                new Point(1, 5, 5),
                new Weight(1),
                "test_tunnel",
                TunnelType.HORIZONTAL,
                List.of(new PartTunnelDescription(
                        new Point(1, 5, 5),
                        new Point(0, 1, 2),
                        List.of(), List.of()
                )),
                true
        ), new TunnelInfo(
                new Point(3, 1, 3),
                new Weight(1),
                "test_tunnel_vertical",
                TunnelType.VERTICAL,
                List.of(new PartTunnelDescription(
                        new Point(3, 1, 3),
                        new Point(1, 0, 1),
                        List.of(), List.of()
                )),
                true
        ));
    }
}
