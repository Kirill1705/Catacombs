package thor.core.port.output.repository;

import thor.core.port.mapping.dto.IslandInfoDto;
import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;

import java.util.List;

public interface InfoRepository {
    List<RoomInfoDto> getRooms();
    List<TunnelInfoDto> getTunnels();
    List<IslandInfoDto> getWaterIslands();

    boolean exportRoomInfo(RoomInfoDto roomInfo);
    boolean exportTunnelInfo(TunnelInfoDto tunnelInfo);
    boolean exportWaterIslandInfo(IslandInfoDto islandInfoDto);
    boolean updateRoomInfo(RoomInfoDto roomInfo);
    boolean updateTunnelInfo(TunnelInfoDto tunnelInfo);
    boolean updateWaterIslandInfo(IslandInfoDto islandInfoDto);
}
