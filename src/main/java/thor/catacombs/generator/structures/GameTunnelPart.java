package thor.catacombs.generator.structures;

import org.bukkit.Material;
import thor.catacombs.generator.map.AfterPlacing;
import thor.catacombs.info.structure.TunnelType;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;

import java.util.Collection;

public class GameTunnelPart extends GameStructure implements TunnelPart, AfterPlacing {
    private final PlayerSpawnPlacesLoader loader;
    private final PartTunnelInfo info;
    public GameTunnelPart(PartTunnelInfo info, BlockPosition position, ItemGeneratorHolder generator) {
        super(info, position, generator);
        this.info = info;
        loader = new PlayerSpawnPlacesLoader(info.getPlayerSpawnPlacesInfo(), position);
    }

    @Override
    public Collection<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return loader.getPlayerSpawnPlaces();
    }

    @Override
    public PartTunnelInfo getInfo() {
        return info;
    }

    @Override
    public void afterPlace(BlockLocation location) {
        if (info.getType() == TunnelType.VERTICAL) {
            getLocation(location).add(info.getAttachmentPoint()).toLocation().getBlock().setType(Material.LADDER);
        }
    }
}
