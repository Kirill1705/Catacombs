package thor.catacombs.info.structure.interfaces;

import thor.catacombs.generator.Weightable;
import thor.catacombs.info.structure.*;

public interface TunnelInfo extends Nameable, Weightable {
    TunnelType getType();
    PartTunnelInfo getByIdx(int idx);
}