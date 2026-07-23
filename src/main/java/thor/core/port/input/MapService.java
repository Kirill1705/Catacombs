package thor.core.port.input;

import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;

import java.util.UUID;

public interface MapService {
    void removeMap(UUID mapId);
    /**
     * Generates new random game map
     * @return map UUID
     */
    UUID generateMap();

    /**
     * @param dto room to add
     * @param force replace if exists
     * @return true if room was replaced
     */
    boolean addRoom(RoomInfoDto dto, boolean force);

    /**
     * @param dto tunnel to add
     * @param force replace if exists
     * @return true if tunnel was replaced
     */
    boolean addTunnel(TunnelInfoDto dto, boolean force);
}
