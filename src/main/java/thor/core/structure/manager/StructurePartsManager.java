package thor.core.structure.manager;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.info.part.ChestInfo;
import thor.core.info.part.PartTunnelInfo;

import java.util.Collection;

public interface StructurePartsManager {
    void addFromRoomInfo(RoomInfo roomInfo, Converter converter);

    void addFromPartTunnelInfo(PartTunnelInfo partTunnelInfo, Converter converter);
}
