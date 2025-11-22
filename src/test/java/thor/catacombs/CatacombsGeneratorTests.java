package thor.catacombs;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.generator.map.*;
import thor.catacombs.generator.map.bind.GameTunnelGenerator;
import thor.catacombs.generator.map.bind.GameTunnelMaker;
import thor.catacombs.generator.map.bind.TunnelFixedMaker;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import org.junit.jupiter.api.Test;
import thor.catacombs.events.creators.GameRoomCreator;
import thor.catacombs.generator.TestRoomGenerator;
import thor.catacombs.generator.Tunnel;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.info.TestRoomInfo;
import thor.catacombs.info.TestTunnelInfo;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.ExitInfo;
import thor.catacombs.info.structure.TunnelType;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.dataStructures.Point;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatacombsGeneratorTests {
    public static File getTestResourcesFolder() {
        ClassLoader classLoader = CatacombsGeneratorTests.class.getClassLoader();
        URL resource = classLoader.getResource("");
        if (resource == null) {
            throw new RuntimeException("Не удалось найти папку test resources");
        }
        return new File(resource.getFile());
    }
    private List<TunnelInfo> createTunnels() {
        return List.of(
                new TestTunnelInfo(
                        TunnelType.X, new Point(5, 5, 5), new Point(0, 1, 2), List.of(), List.of()
                ),
                new TestTunnelInfo(
                        TunnelType.VERTICAL, new Point(3, 1, 3), new Point(1, 0, 1), List.of(), List.of()
                ),
                new TestTunnelInfo(
                        TunnelType.Z, new Point(5, 5, 5), new Point(2, 1, 0), List.of(), List.of()
                )
        );
    }

    private ImmutableGraph usualConfiguration(List<BlockPosition> roomPositions) {
        BlockPosition size = new Point(9, 9, 9);
        AttributeRegistry registry = new AttributeRegistry.Builder().build();
        RoomInfo roomInfo = new TestRoomInfo(size, List.of(), List.of(), List.of(
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, 1, 0), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(0, 1, 4), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, 1, size.z() - 1), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(size.x() - 1, 1, 4), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, 0, 4), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, size.y() - 1, 4), size, registry)
        ));
        //GameRoomCreator creator = new GameRoomCreator(new ItemInfoGenerator(getTestResourcesFolder(), Map.of(FeelType.CHEST, "chests.yml", FeelType.BARREL, "barrels.yml")));
        GameRoomCreator creator = new GameRoomCreator(null);
        YamlConfiguration mapConfig = new YamlConfiguration();
        MapGenerator generator = new CatacombsGenerator(new GameTunnelGenerator(new TunnelFixedMaker(new GameTunnelMaker(createTunnels(), creator), creator)), new TestRoomGenerator(
                roomPositions,
                new GeneratorConfigurator(mapConfig),
                List.of(roomInfo),
                creator
        ));
        return generator.getStructuresGraph();
    }

    @Test
    void tunnelTest_WhenTunnelDirect_ShouldBeBindByTunnel() {
        ImmutableGraph graph = usualConfiguration(List.of(new Point(100, 1, 100), new Point(120, 1, 100)));
        assertEquals(2, graph.getSize());
        Room first = graph.getRooms().iterator().next();
        Tunnel tunnel = null;
        for (var edge : graph.getEdges(first)) {
            tunnel = edge.edge();
        }
        assertNotNull(tunnel);
        assertEquals(11, tunnel.data().size());
    }

    @Test
    void tunnelTest_WhenTunnelNotDirect_ShouldBeBindByTunnel() {
        ImmutableGraph graph = usualConfiguration(List.of(new Point(100, 1, 100), new Point(120, 5, 108)));
        assertEquals(2, graph.getSize());
        Room first = graph.getRooms().iterator().next();
        Tunnel tunnel = null;
        for (var edge : graph.getEdges(first)) {
            tunnel = edge.edge();
        }
        assertNotNull(tunnel);
        assertEquals(11, tunnel.data().size());
    }

    @Test
    void tunnelTest_WhenTunnelDirectVertical_ShouldBeBindByTunnel() {
        ImmutableGraph graph = usualConfiguration(List.of(new Point(100, 20, 100), new Point(100, 1, 100)));
        assertEquals(2, graph.getSize());
        Room first = graph.getRooms().iterator().next();
        Tunnel tunnel = null;
        for (var edge : graph.getEdges(first)) {
            tunnel = edge.edge();
        }
        assertNotNull(tunnel);
        assertEquals(10, tunnel.data().size());
    }

    @Test
    void tunnelTest_WhenTunnelNotDirectVertical_ShouldBeBindByTunnel() {
        ImmutableGraph graph = usualConfiguration(List.of(new Point(100, 1, 100), new Point(105, 20, 105)));
        assertEquals(2, graph.getSize());
        Room first = graph.getRooms().iterator().next();
        Tunnel tunnel = null;
        for (var edge : graph.getEdges(first)) {
            tunnel = edge.edge();
        }
        assertNotNull(tunnel);
    }

    @Test
    void generatorTest_WhenManyRooms_ShouldJustGenerateMap() {
        BlockPosition size = new Point(9, 9, 9);
        AttributeRegistry registry = new AttributeRegistry.Builder().build();
        RoomInfo roomInfo = new TestRoomInfo(size, List.of(), List.of(), List.of(
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, 1, 0), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(0, 1, 4), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, 1, size.z() - 1), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(size.x() - 1, 1, 4), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, 0, 4), size, registry),
                new ExitInfo(new YamlConfiguration(), Material.STONE_BRICK_STAIRS, new Point(4, size.y() - 1, 4), size, registry)
        ));
        //GameRoomCreator creator = new GameRoomCreator(new ItemInfoGenerator(getTestResourcesFolder(), Map.of(FeelType.CHEST, "chests.yml", FeelType.BARREL, "barrels.yml")));
        GameRoomCreator creator = new GameRoomCreator(null);
        YamlConfiguration mapConfig = new YamlConfiguration();
        MapGenerator generator = new CatacombsGenerator(new GameTunnelGenerator(new TunnelFixedMaker(new GameTunnelMaker(createTunnels(), creator), creator)), new GameRoomGenerator(
                new GeneratorConfigurator(mapConfig),
                List.of(roomInfo),
                creator
        ));
        ImmutableGraph graph = generator.getStructuresGraph();
        assertNotEquals(0, graph.getSize());
    }
}
