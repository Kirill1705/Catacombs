package thor.core.generator.tunnel.make;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface TunnelPartsDispenser {
    TunnelProgress getProgress(BlockPosition size, BlockPosition position);
}
