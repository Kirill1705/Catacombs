package thor.catacombs.generator.structures;

import thor.catacombs.info.structure.interfaces.PartTunnelInfo;

public interface TunnelPart extends Structure, PlayerSpawnable {
    @Override
    PartTunnelInfo getInfo();
}
