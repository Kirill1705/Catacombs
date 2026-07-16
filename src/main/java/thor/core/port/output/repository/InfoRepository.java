package thor.core.port.output.repository;

import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;

import java.util.List;

public interface InfoRepository {
    List<RoomInfoDto> getRooms();
    List<TunnelInfoDto> getTunnels();
    boolean exportRoomInfo(RoomInfoDto roomInfo);
    boolean exportTunnelInfo(TunnelInfoDto tunnelInfo);
    boolean updateRoomInfo(RoomInfoDto roomInfo);
    boolean updateTunnelInfo(TunnelInfoDto tunnelInfo);
}
