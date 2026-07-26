package thor.core.service;

import lombok.RequiredArgsConstructor;
import thor.core.port.input.StructureInfoService;
import thor.core.port.mapping.RoomInfoWithPath;
import thor.core.port.mapping.TunnelInfoWithPath;
import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.port.output.StructureManager;
import thor.core.port.output.repository.InfoRepository;

import java.util.List;

@RequiredArgsConstructor
public class StructureInfoServiceImpl implements StructureInfoService {
    private final InfoRepository infoRepository;
    private final StructureManager structureManager;

    @Override
    public boolean addRoom(RoomInfoWithPath dto, boolean force) {
        boolean success = infoRepository.exportRoomInfo(dto.roomInfo());
        if (force && !success) {
            infoRepository.updateRoomInfo(dto.roomInfo());
            success = true;
        }
        if (success) {
            structureManager.addRoomStructure(dto.path(), dto.roomInfo().id());
        }
        return success;
    }

    @Override
    public boolean addTunnel(TunnelInfoWithPath dto, boolean force) {
        boolean success = infoRepository.exportTunnelInfo(dto.tunnelInfo());
        if (force && !success) {
            infoRepository.updateTunnelInfo(dto.tunnelInfo());
            success = true;
        }
        if (success) {
            structureManager.addTunnelStructure(dto.paths(), dto.tunnelInfo().id());
        }
        return success;
    }

    @Override
    public List<RoomInfoWithPath> allRooms() {
        List<RoomInfoDto> rooms = infoRepository.getRooms();
        return rooms.stream()
                .map(roomInfoDto -> new RoomInfoWithPath(roomInfoDto, structureManager.getRoomStructurePath(roomInfoDto.id())))
                .toList();
    }

    @Override
    public List<TunnelInfoWithPath> allTunnels() {
        List<TunnelInfoDto> tunnels = infoRepository.getTunnels();
        return tunnels.stream()
                .map(tunnelInfoDto -> new TunnelInfoWithPath(tunnelInfoDto, structureManager.getTunnelPartPaths(tunnelInfoDto.id(), tunnelInfoDto.parts().size())))
                .toList();
    }
}
