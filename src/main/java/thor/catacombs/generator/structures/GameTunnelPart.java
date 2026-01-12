package thor.catacombs.generator.structures;

import org.bukkit.Material;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.generator.map.AfterPlacing;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.structure.TunnelType;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;

import java.util.Collection;

public class GameTunnelPart extends GameStructure implements TunnelPart, AfterPlacing {
    private final PlayerSpawnPlacesLoader loader;
    private final PartTunnelInfo info;
    public GameTunnelPart(PartTunnelInfo info, StructureLocation position, ItemGeneratorHolder generator) {
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
    public void afterPlace(GameWorldAccessor accessor) {
        if (info.getType() == TunnelType.VERTICAL) {
            accessor.getBlockAt(position, info.getAttachmentPoint()).setType(Material.LADDER);
        }
    }
}
