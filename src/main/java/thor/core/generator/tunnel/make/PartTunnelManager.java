package thor.core.generator.tunnel.make;

import thor.core.structure.PartTunnel;

public interface PartTunnelManager {
    boolean canPlace(TunnelCreatorNode node);
    PartTunnel create(TunnelCreatorNode node);
}
