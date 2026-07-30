package thor.core.port.input;

import thor.core.port.mapping.RoomInfoWithPath;
import thor.core.port.mapping.TunnelInfoWithPath;

import java.util.List;

public interface StructureInfoService {
    /**
     * @param roomInfo room to add
     * @param force    replace if exists
     * @return true if room was replaced
     */
    boolean addRoom(RoomInfoWithPath roomInfo, boolean force);

    /**
     * @param tunnelInfo tunnel to add
     * @param force      replace if exists
     * @return true if tunnel was replaced
     */
    boolean addTunnel(TunnelInfoWithPath tunnelInfo, boolean force);

    List<RoomInfoWithPath> allRooms();

    List<TunnelInfoWithPath> allTunnels();
}
