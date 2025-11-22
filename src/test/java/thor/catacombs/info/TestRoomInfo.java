package thor.catacombs.info;

import org.bukkit.structure.Structure;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.block.ExitInfo;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.structure.interfaces.RoomInfo;

import java.util.Collection;
import java.util.List;

public record TestRoomInfo(BlockPosition size, List<ChestInfo> chests, List<PlayerSpawnInfo> playerSpawnPlaces, Collection<ExitInfo> exits) implements RoomInfo {

    @Override
    public int getWeight() {
        return 20;
    }

    @Override
    public Collection<ExitInfo> getExitsInfo() {
        return exits;
    }

    @Override
    public String getName() {
        return "test_room";
    }

    @Override
    public List<PlayerSpawnInfo> getPlayerSpawnPlacesInfo() {
        return playerSpawnPlaces;
    }

    @Override
    public List<ChestInfo> getChestsInfo() {
        return chests;
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
