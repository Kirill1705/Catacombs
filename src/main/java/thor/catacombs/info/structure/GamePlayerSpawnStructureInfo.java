package thor.catacombs.info.structure;

import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.block.creators.PlayerSpawnInfoCreator;
import thor.catacombs.info.structure.interfaces.PlayerSpawnableStructureInfo;

import java.util.List;

public class GamePlayerSpawnStructureInfo implements PlayerSpawnableStructureInfo {
    private final List<PlayerSpawnInfo> playerSpawnPlaces;
    public GamePlayerSpawnStructureInfo(BlockInfoGenerator generator) {
        playerSpawnPlaces = generator.getBlockInfo(AttributeHolderType.PLAYER_SPAWN_PLACE.getName(), new PlayerSpawnInfoCreator());
    }
    @Override
    public List<PlayerSpawnInfo> getPlayerSpawnPlacesInfo() {
        return playerSpawnPlaces;
    }
}
