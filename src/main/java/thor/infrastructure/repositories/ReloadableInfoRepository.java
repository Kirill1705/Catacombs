package thor.infrastructure.repositories;

import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.port.output.repository.InfoRepository;
import thor.usefulUtils.reload.Reloadable;

import java.nio.file.Path;
import java.util.List;

public class ReloadableInfoRepository implements InfoRepository, Reloadable {
    private final InfoRepository infoRepository;

    private List<RoomInfoDto> rooms;
    private List<TunnelInfoDto> tunnels;

    public ReloadableInfoRepository(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
        reload(null);
    }

    @Override
    public List<RoomInfoDto> getRooms() {
        return rooms;
    }

    @Override
    public List<TunnelInfoDto> getTunnels() {
        return tunnels;
    }

    @Override
    public boolean exportRoomInfo(RoomInfoDto roomInfo) {
        return false;
    }

    @Override
    public boolean exportTunnelInfo(TunnelInfoDto tunnelInfo) {
        return false;
    }

    @Override
    public boolean updateRoomInfo(RoomInfoDto roomInfo) {
        return false;
    }

    @Override
    public boolean updateTunnelInfo(TunnelInfoDto tunnelInfo) {
        return false;
    }

    @Override
    public void reload(Path file) {
        rooms = infoRepository.getRooms();
        tunnels = infoRepository.getTunnels();
    }
}
