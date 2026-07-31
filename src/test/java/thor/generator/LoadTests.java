package thor.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thor.core.generator.GroundRoomGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.GroundTunnelGenerator;
import thor.core.structure.create.PartTunnelCreatorImpl;
import thor.core.structure.create.SimpleRoomCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.create.StructurePartsHolder;

import java.util.List;

public class LoadTests {
    @Test
    public void loadTest_ShouldGenerateManyTunnels() {
        // Arrange
        StructurePartsHolder partsHolder = new StructurePartsHolder(null);
        GroundRoomGenerator generator = new GroundRoomGenerator(List.of(SimpleStructuresInfo.VALUE.getRoomInfo()), 200, new SimpleRoomCreator());
        GameMap map = new GameMap(new Point(256, 64, 256), partsHolder);
        generator.generate(map);
        GroundTunnelGenerator tunnelGenerator = new GroundTunnelGenerator(
                SimpleStructuresInfo.VALUE.getTunnelInfos(),
                new PartTunnelCreatorImpl()
        );

        // Act
        tunnelGenerator.generateTunnels(map);

        // Assert
        Assertions.assertTrue(map.getGraph().getAllTunnels().size() > 100);
    }
}
