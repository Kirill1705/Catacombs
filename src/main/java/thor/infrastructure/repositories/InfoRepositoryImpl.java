package thor.infrastructure.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.vikhrenko.serverUtils.json.ModuleCreator;
import thor.core.port.mapping.dto.IslandInfoDto;
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
    private final Path waterIslands;

    private final ObjectMapper mapper;

    public InfoRepositoryImpl(Path rooms, Path tunnels, Path waterIslands) {
        this.rooms = rooms;
        this.tunnels = tunnels;
        this.waterIslands = waterIslands;
        mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new ModuleCreator().pointModule());
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
    public List<IslandInfoDto> getWaterIslands() {
        return parse(waterIslands, IslandInfoDto.class);
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
    public boolean exportWaterIslandInfo(IslandInfoDto islandInfoDto) {
        if (containsWaterIsland(islandInfoDto.id())) {
            return false;
        }
        exportOrReplace(waterIslands, islandInfoDto, islandInfoDto.id() + ".json");
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

    @Override
    public boolean updateWaterIslandInfo(IslandInfoDto islandInfoDto) {
        if (containsWaterIsland(islandInfoDto.id())) {
            exportOrReplace(waterIslands, islandInfoDto, islandInfoDto.id() + ".json");
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

    private boolean containsWaterIsland(String textId) {
        return parse(waterIslands, IslandInfoDto.class)
                .stream()
                .anyMatch(dto -> dto.id().equals(textId));
    }

    private<T> void exportOrReplace(Path directory, T dto, String fileName) {
        File file = new File(directory.toFile(), fileName);
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
