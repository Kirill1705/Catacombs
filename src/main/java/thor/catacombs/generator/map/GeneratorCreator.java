package thor.catacombs.generator.map;

import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;

import java.util.List;

public interface GeneratorCreator {
    MapGenerator create(List<RoomInfo> roomInfo, List<TunnelInfo> tunnelInfo);
}
