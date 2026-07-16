package thor.core.port.input;

import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;

public interface MapService {
    /**
     * @param location location to place map
     * @param placeBedrock should place bedrock box
     * @param fillStone should fill bedrock box stone
     */
    void generateMap(LocationDto location, boolean placeBedrock, boolean fillStone);

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
