package thor.core.service;

import lombok.RequiredArgsConstructor;
import thor.core.port.input.StructureInfoService;
import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.port.output.repository.InfoRepository;

@RequiredArgsConstructor
public class StructureInfoServiceImpl implements StructureInfoService {
    private final InfoRepository infoRepository;

    @Override
    public boolean addRoom(RoomInfoDto dto, boolean force) {
        boolean success = infoRepository.exportRoomInfo(dto);
        if (force && !success) {
            infoRepository.updateRoomInfo(dto);
            return true;
        }
        return false;
    }

    @Override
    public boolean addTunnel(TunnelInfoDto dto, boolean force) {
        boolean success = infoRepository.exportTunnelInfo(dto);
        if (force && !success) {
            infoRepository.updateTunnelInfo(dto);
            return true;
        }
        return false;
    }
}
