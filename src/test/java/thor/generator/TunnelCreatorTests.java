package thor.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thor.core.generator.TunnelGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.generator.tunnel.make.TunnelPartsDispenserImpl;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import thor.core.structure.RoomImpl;
import thor.core.structure.create.PartTunnelCreatorImpl;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TunnelCreatorTests {
    @Test
    public void contouringTest_ShouldContorOneRoom() {
        // Arrange
        GameMap map = new GameMap(new Point(100, 100, 100));
        Room from = new RoomImpl(null, Converter.simple(new Point(0, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Room to = new RoomImpl(null, Converter.simple(new Point(70, 0, 30)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Room barrier1 = new RoomImpl(null, Converter.simple(new Point(35, 0, 15)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Room barrier2 = new RoomImpl(null, Converter.simple(new Point(20, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Room barrier3 = new RoomImpl(null, Converter.simple(new Point(50, 0, 30)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(from);
        map.addRoom(to);
        map.addRoom(barrier1);
        map.addRoom(barrier2);
        map.addRoom(barrier3);
        GroundTunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl(null)
        );

        // Act
        Collection<PartTunnel> partTunnels = generator.bind(from, to, map);

        // Assert
        Assertions.assertNotNull(partTunnels);
    }

    @Test
    public void formTest_ShouldGenerateVerticalFromHorizontalExits() {
        // Arrange
        GameMap map = new GameMap(new Point(100, 100, 100));
        Room from = new RoomImpl(null, Converter.simple(new Point(0, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Room to = new RoomImpl(null, Converter.simple(new Point(15, 80, 20)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Stream.concat(from.getExits().stream(), to.getExits().stream())
                        .filter(exit -> exit.getOffset().abs().equals(new Point(0, 1, 0)))
                        .forEach(exit -> exit.setClosed(true));
        map.addRoom(from);
        map.addRoom(to);
        GroundTunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl(null)
        );

        // Act
        Collection<PartTunnel> partTunnels = generator.bind(from, to, map);

        // Assert
        Assertions.assertNotNull(partTunnels);
    }

    @Test
    public void formTest_ShouldGenerateVerticalFromVerticalExits() {
        // Arrange
        GameMap map = new GameMap(new Point(100, 100, 100));
        Room from = new RoomImpl(null, Converter.simple(new Point(0, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        Room to = new RoomImpl(null, Converter.simple(new Point(15, 6, 25)), SimpleStructuresInfo.VALUE.getRoomInfo());
        from.getExits().stream()
                .filter(exit -> !exit.getOffset().equals(new Point(0, 1, 0)))
                .forEach(exit -> exit.setClosed(true));
        to.getExits().stream()
                .filter(exit -> !exit.getOffset().equals(new Point(0, 0, -1)))
                .forEach(exit -> exit.setClosed(true));
        map.addRoom(from);
        map.addRoom(to);
        GroundTunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl(null)
        );

        // Act
        Collection<PartTunnel> partTunnels = generator.bind(from, to, map);

        // Assert
        Assertions.assertNotNull(partTunnels);
    }
}
