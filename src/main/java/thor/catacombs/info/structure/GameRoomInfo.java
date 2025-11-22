package thor.catacombs.info.structure;

import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.ExitInfo;
import thor.catacombs.info.block.ExitPosition;
import thor.catacombs.info.block.MainInfo;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.block.creators.ExitPositionCreator;
import thor.catacombs.info.block.creators.MainCreator;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.extra.ChestLoader;
import thor.catacombs.info.structure.extra.ExitLoader;
import thor.catacombs.info.structure.interfaces.ExitableStructureInfo;
import thor.catacombs.info.structure.interfaces.PlayerSpawnableStructureInfo;
import thor.catacombs.info.structure.interfaces.RoomInfo;

import java.util.*;

public class GameRoomInfo extends GameStructureInfo implements RoomInfo {
    @Override
    public Collection<ExitInfo> getExitsInfo() {
        return exits;
    }
    private final Collection<ExitInfo> exits;
    private final MainInfo mainInfo;
    private final PlayerSpawnableStructureInfo playerInfo;
    public GameRoomInfo(BlockInfoGenerator generator, AttributeRegistry registry, ChestLoader loader, PlayerSpawnableStructureInfo spawnInfo) {
        super(generator, loader);
        mainInfo = generator.getBlockInfo(AttributeHolderType.MAIN.getName(), new MainCreator(registry)).getFirst();
        Collection<ExitPosition> exitPositions = generator.getBlockInfo(AttributeHolderType.EXIT.getName(), new ExitPositionCreator());
        ExitLoader exitLoader = new ExitLoader(generator.getBox(), registry, exitPositions);
        exits = exitLoader.getExitsInfo();
        this.playerInfo = spawnInfo;
    }

    @Override
    public int getWeight() {
        return mainInfo.getWeight();
    }

    @Override
    public String getName() {
        return mainInfo.getName();
    }

    @Override
    public List<PlayerSpawnInfo> getPlayerSpawnPlacesInfo() {
        return playerInfo.getPlayerSpawnPlacesInfo();
    }
}