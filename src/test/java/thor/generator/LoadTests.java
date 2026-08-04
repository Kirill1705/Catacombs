package thor.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.complete.GameMapImpl;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.structure.create.PartTunnelCreatorImpl;
import thor.core.structure.create.SimpleRoomCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public class LoadTests {
    @Test
    public void loadTest_ShouldGenerateManyTunnels() {
        // Arrange
        GroundRoomGenerator generator = new GroundRoomGenerator(List.of(SimpleStructuresInfo.VALUE.getRoomInfo()), 200, new SimpleRoomCreator());
        GameMapImpl map = new GameMapImpl(new Point(256, 64, 256));
        generator.generate(map);
        GroundTunnelGenerator tunnelGenerator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        tunnelGenerator.generate(map);

        // Assert
        Assertions.assertTrue(map.getAllStructures().size() > 100);
    }
}
