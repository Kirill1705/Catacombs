package thor.core.service;

import lombok.RequiredArgsConstructor;
import thor.core.info.RoomInfo;
import thor.core.info.TunnelInfo;
import thor.core.port.input.StructureInfoService;
import thor.core.port.mapping.RoomInfoWithPath;
import thor.core.port.mapping.StructureInfoMapper;
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
        RoomInfoDto roomInfoDto = validate(dto.roomInfo());
        boolean success = infoRepository.exportRoomInfo(roomInfoDto);
        if (force && !success) {
            success = infoRepository.updateRoomInfo(roomInfoDto);
        }
        if (success) {
            structureManager.addRoomStructure(dto.structure(), roomInfoDto.id());
        }
        return success;
    }

    @Override
    public boolean addTunnel(TunnelInfoWithPath dto, boolean force) {
        TunnelInfoDto tunnelInfoDto = validate(dto.tunnelInfo());
        boolean success = infoRepository.exportTunnelInfo(tunnelInfoDto);
        if (force && !success) {
            success = infoRepository.updateTunnelInfo(tunnelInfoDto);
        }
        if (success) {
            structureManager.addTunnelStructure(dto.paths(), tunnelInfoDto.id());
        }
        return success;
    }

    @Override
    public List<RoomInfoWithPath> allRooms() {
        List<RoomInfoDto> rooms = infoRepository.getRooms();
        return rooms.stream()
                .map(roomInfoDto -> new RoomInfoWithPath(validate(roomInfoDto), structureManager.getRoomStructurePath(roomInfoDto.id())))
                .toList();
    }

    @Override
    public List<TunnelInfoWithPath> allTunnels() {
        List<TunnelInfoDto> tunnels = infoRepository.getTunnels();
        return tunnels.stream()
                .map(tunnelInfoDto -> new TunnelInfoWithPath(validate(tunnelInfoDto), structureManager.getTunnelPartPaths(tunnelInfoDto.id(), tunnelInfoDto.parts().size())))
                .toList();
    }

    private TunnelInfoDto validate(TunnelInfoDto dto) {
        return StructureInfoMapper.toDto(StructureInfoMapper.fromDto(dto));
    }
    private RoomInfoDto validate(RoomInfoDto dto) {
        return StructureInfoMapper.toDto(StructureInfoMapper.fromDto(dto));
    }
}
