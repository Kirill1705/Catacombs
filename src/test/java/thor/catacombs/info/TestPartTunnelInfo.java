package thor.catacombs.info;

import org.bukkit.structure.Structure;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.structure.TunnelType;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;

import java.util.List;

public record TestPartTunnelInfo(String name, BlockPosition size, List<ChestInfo> chestInfos, List<PlayerSpawnInfo> playerSpawnPlaces, TunnelType type, BlockPosition atBlockPosition) implements PartTunnelInfo {

    @Override
    public BlockPosition getAttachmentPoint() {
        return atBlockPosition;
    }

    @Override
    public TunnelType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PlayerSpawnInfo> getPlayerSpawnPlacesInfo() {
        return playerSpawnPlaces;
    }

    @Override
    public List<ChestInfo> getChestsInfo() {
        return chestInfos;
    }

    @Override
    public Structure getStructure() {
        return null;
    }

    @Override
    public BlockPosition getSize() {
        return size;
    }
}
