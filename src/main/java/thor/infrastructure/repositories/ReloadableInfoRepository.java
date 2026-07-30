package thor.infrastructure.repositories;

import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.port.output.repository.InfoRepository;
import ru.vikhrenko.serverUtils.reload.Reloadable;

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
        return infoRepository.exportRoomInfo(roomInfo);
    }

    @Override
    public boolean exportTunnelInfo(TunnelInfoDto tunnelInfo) {
        return infoRepository.exportTunnelInfo(tunnelInfo);
    }

    @Override
    public boolean updateRoomInfo(RoomInfoDto roomInfo) {
        return infoRepository.updateRoomInfo(roomInfo);
    }

    @Override
    public boolean updateTunnelInfo(TunnelInfoDto tunnelInfo) {
        return infoRepository.updateTunnelInfo(tunnelInfo);
    }

    @Override
    public void reload(Path file) {
        rooms = infoRepository.getRooms();
        tunnels = infoRepository.getTunnels();
    }
}
