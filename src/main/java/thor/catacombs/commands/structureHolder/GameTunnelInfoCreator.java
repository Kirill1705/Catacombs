package thor.catacombs.commands.structureHolder;

import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.GameTunnelInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

public class GameTunnelInfoCreator implements InfoCreator<TunnelInfo> {
    @Override
    public TunnelInfo create(ImmutableBox box, AttributeRegistry registry) {
        BlockInfoGenerator generator = new BlockInfoGenerator(box);
        return new GameTunnelInfo(box, registry, generator);
    }
}
