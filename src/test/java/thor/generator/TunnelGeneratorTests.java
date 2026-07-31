package thor.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thor.core.generator.TunnelGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import thor.core.structure.RoomImpl;
import thor.core.structure.create.PartTunnelCreatorImpl;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.create.StructurePartsHolder;

import java.util.Collection;

public class TunnelGeneratorTests {
    @Test
    public void simpleGeneratorTest_ShouldGenerateOneHorizontalTunnelZ() {
        // Arrange
        GameMap map = new GameMap(new Point(256, 256, 256), new StructurePartsHolder(null));
        Room from = new RoomImpl(Converter.simple(new Point(0, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(from);
        Room second = new RoomImpl(Converter.simple(new Point(0, 0, 20)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(second);
        TunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        generator.generateTunnels(map);

        // Assert
        Collection<PartTunnel> tunnel = map.getGraph().getEdge(from, second);
        Assertions.assertNotNull(tunnel);
        Assertions.assertTrue(10 <= tunnel.size());
    }

    @Test
    public void simpleGeneratorTest_ShouldGenerateOneHorizontalTunnelX() {
        // Arrange
        GameMap map = new GameMap(new Point(256, 256, 256), new StructurePartsHolder(null));
        Room from = new RoomImpl(Converter.simple(new Point(0, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(from);
        Room second = new RoomImpl(Converter.simple(new Point(15, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(second);
        TunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        generator.generateTunnels(map);

        // Assert
        Collection<PartTunnel> tunnel = map.getGraph().getEdge(from, second);
        Assertions.assertNotNull(tunnel);
        Assertions.assertTrue(10 <= tunnel.size());
    }

    @Test
    public void simpleGeneratorTest_ShouldGenerateOneVerticalTunnel() {
        // Arrange
        GameMap map = new GameMap(new Point(256, 256, 256), new StructurePartsHolder(null));
        Room from = new RoomImpl(Converter.simple(new Point(0, 0, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(from);
        Room second = new RoomImpl(Converter.simple(new Point(0, 15, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(second);
        TunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        generator.generateTunnels(map);

        // Assert
        Collection<PartTunnel> tunnel = map.getGraph().getEdge(from, second);
        Assertions.assertNotNull(tunnel);
        Assertions.assertTrue(10 <= tunnel.size());
    }

    @Test
    public void simpleGeneratorTest_ShouldGenerateOneDiagonalTunnel() {
        // Arrange
        GameMap map = new GameMap(new Point(256, 256, 256), new StructurePartsHolder(null));
        Room from = new RoomImpl(Converter.simple(new Point(0, 0, 15)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(from);
        Room second = new RoomImpl(Converter.simple(new Point(15, 5, 10)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(second);
        TunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        generator.generateTunnels(map);

        // Assert
        Collection<PartTunnel> tunnel = map.getGraph().getEdge(from, second);
        Assertions.assertNotNull(tunnel);
        Assertions.assertTrue(10 <= tunnel.size());
    }

    @Test
    public void simpleGeneratorTest_ShouldGenerateOneComplexTunnel() {
        // Arrange
        GameMap map = new GameMap(new Point(256, 256, 256), new StructurePartsHolder(null));
        Room from = new RoomImpl(Converter.simple(new Point(40, 50, 0)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(from);
        Room second = new RoomImpl(Converter.simple(new Point(25, 0, 20)), SimpleStructuresInfo.VALUE.getRoomInfo());
        map.addRoom(second);
        TunnelGenerator generator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        generator.generateTunnels(map);

        // Assert
        Collection<PartTunnel> tunnel = map.getGraph().getEdge(from, second);
        Assertions.assertNotNull(tunnel);
        Pair pair = new Pair(0, 0);
        int count = 0;
        int max = 0;
        for (PartTunnel partTunnel: tunnel) {
            Pair current = new Pair(partTunnel.getPosition().x(), partTunnel.getPosition().z());
            if (pair.equals(current)) {
                count++;
            }
            else {
                if (count > max) {
                    max = count;
                }
                count = 0;
                pair = current;
            }
        }
        Assertions.assertTrue(10 <= Math.max(count, max));
    }

    private record Pair(int x, int z) {}
}
