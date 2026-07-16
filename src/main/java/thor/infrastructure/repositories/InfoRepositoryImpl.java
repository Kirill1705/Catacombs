package thor.infrastructure.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.port.output.repository.InfoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InfoRepositoryImpl implements InfoRepository {
    private final Path rooms;
    private final Path tunnels;

    private final ObjectMapper mapper;

    public InfoRepositoryImpl(Path rooms, Path tunnels) {
        this.rooms = rooms;
        this.tunnels = tunnels;
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Override
    public List<RoomInfoDto> getRooms() {
        return parse(rooms, RoomInfoDto.class);
    }

    @Override
    public List<TunnelInfoDto> getTunnels() {
        return parse(tunnels, TunnelInfoDto.class);
    }

    @Override
    public boolean exportRoomInfo(RoomInfoDto roomInfo) {
        if (containsRoom(roomInfo.id())) {
            return false;
        }
        exportOrReplace(rooms, roomInfo, roomInfo.id() + ".json");
        return true;
    }

    @Override
    public boolean exportTunnelInfo(TunnelInfoDto tunnelInfo) {
        if (containsTunnel(tunnelInfo.id())) {
            return false;
        }
        exportOrReplace(tunnels, tunnelInfo, tunnelInfo.id() + ".json");
        return true;
    }

    @Override
    public boolean updateRoomInfo(RoomInfoDto roomInfo) {
        if (containsRoom(roomInfo.id())) {
            exportOrReplace(rooms, roomInfo, roomInfo.id() + ".json");
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTunnelInfo(TunnelInfoDto tunnelInfo) {
        if (containsTunnel(tunnelInfo.id())) {
            exportOrReplace(tunnels, tunnelInfo, tunnelInfo.id() + ".json");
            return true;
        }
        return false;
    }

    private boolean containsRoom(String textId) {
        return parse(rooms, RoomInfoDto.class)
                .stream()
                .anyMatch(dto -> dto.id().equals(textId));
    }

    private boolean containsTunnel(String textId) {
        return parse(tunnels, TunnelInfoDto.class)
                .stream()
                .anyMatch(dto -> dto.id().equals(textId));
    }

    private<T> void exportOrReplace(Path directory, T dto, String fileName) {
        File file = new File(directory.toFile(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> parse(Path directory, Class<T> clazz) {
        try {
            List<File> files = Files.list(directory)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toList();
            List<T> result = new ArrayList<>();
            for (File file: files) {
                result.add(mapper.readValue(file, clazz));
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
