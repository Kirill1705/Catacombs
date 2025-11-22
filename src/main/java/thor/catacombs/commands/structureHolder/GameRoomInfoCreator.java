package thor.catacombs.commands.structureHolder;

import org.bukkit.Location;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.GamePlayerSpawnStructureInfo;
import thor.catacombs.info.structure.GameRoomInfo;
import thor.catacombs.info.structure.extra.*;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

public class GameRoomInfoCreator implements InfoCreator<RoomInfo> {
    @Override
    public RoomInfo create(ImmutableBox box, AttributeRegistry registry) {
        BlockInfoGenerator generator = new BlockInfoGenerator(box);
        ChestLoader loader = new GameChestLoader(generator, registry);
        return new GameRoomInfo(generator, registry, loader, new GamePlayerSpawnStructureInfo(generator));
    }
}
