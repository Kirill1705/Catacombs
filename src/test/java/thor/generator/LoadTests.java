package thor.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.generator.tunnel.make.TunnelPartsDispenserImpl;
import thor.core.structure.create.PartTunnelCreatorImpl;
import thor.core.structure.create.SimpleRoomCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.List;

public class LoadTests {
    @Test
    public void loadTest_ShouldGenerateManyTunnels() {
        // Arrange
        GroundRoomGenerator generator = new GroundRoomGenerator(List.of(SimpleStructuresInfo.VALUE.getRoomInfo()), new Point(256, 64, 256), 200, new SimpleRoomCreator(null));
        GameMap map = generator.generate();
        GroundTunnelGenerator tunnelGenerator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl(null)
        );

        // Act
        tunnelGenerator.generateTunnels(map);

        // Assert
        Assertions.assertTrue(map.getGraph().getAllTunnels().size() > 100);
    }
}
