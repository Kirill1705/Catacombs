package thor.catacombs.info.structure;

import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.extra.GameChestLoader;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.OffsetBox;

public record GamePartTunnelCreator(ImmutableBox box, AttributeRegistry registry, BlockPosition attachmentBlockPosition, String name) implements PartTunnelInfoCreator {
    @Override
    public PartTunnelInfo create(OffsetBox box, TunnelType type) {
        ImmutableBox partBox = new ImmutableBox(this.box.begin().add(box.begin()), this.box.begin().add(box.end()));
        BlockInfoGenerator generator = new BlockInfoGenerator(partBox);
        return new GamePartTunnelInfo(generator, new GameChestLoader(generator, registry), new GamePlayerSpawnStructureInfo(generator), attachmentBlockPosition, type, name);
    }
}
