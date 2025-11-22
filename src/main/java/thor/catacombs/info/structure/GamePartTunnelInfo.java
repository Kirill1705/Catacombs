package thor.catacombs.info.structure;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.extra.ChestLoader;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.PlayerSpawnableStructureInfo;

import java.util.List;

public class GamePartTunnelInfo extends GameStructureInfo implements PartTunnelInfo {
    private final PlayerSpawnableStructureInfo playerInfo;
    private final String name;
    @Override
    public BlockPosition getAttachmentPoint() {
        return attachmentBlockPosition;
    }
    @Override
    public TunnelType getType() {
        return type;
    }

    private final TunnelType type;
    private final BlockPosition attachmentBlockPosition;
    public GamePartTunnelInfo(BlockInfoGenerator generator, ChestLoader loader, PlayerSpawnableStructureInfo playerInfo, BlockPosition attachmentBlockPosition, TunnelType type, String name) {
        super(generator, loader);
        this.attachmentBlockPosition=attachmentBlockPosition;
        this.type=type;
        this.playerInfo = playerInfo;
        this.name = name;
    }

    @Override
    public List<PlayerSpawnInfo> getPlayerSpawnPlacesInfo() {
        return playerInfo.getPlayerSpawnPlacesInfo();
    }

    @Override
    public String getName() {
        return name;
    }
}
