package thor.infrastructure.repositories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.Data;
import thor.core.port.mapping.dto.MapConfig;
import thor.core.port.output.repository.MapConfigHolder;
import thor.usefulUtils.utils.dataStructures.Point;

import java.io.IOException;
import java.nio.file.Path;

public class MapConfigHolderImpl implements MapConfigHolder {
    private final Path path;

    public MapConfigHolderImpl(Path path) {
        this.path = path;
        if (!path.toFile().exists()) {
            addDefaultConfig();
        }
    }


    @Override
    public MapConfig getConfig() {
        YAMLMapper mapper = new YAMLMapper();
        try {
            YamlConfig config = mapper.readValue(path.toFile(), YamlConfig.class);
            return fromJson(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MapConfig fromJson(YamlConfig config) {
        return new MapConfig(
                new Point(config.mapSize[0], config.mapSize[1], config.mapSize[2]),
                config.roomsQuantity
        );
    }

    private void addDefaultConfig() {
        YAMLMapper mapper = new YAMLMapper();
        try {
            mapper.writeValue(path.toFile(), new YamlConfig());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    private static class YamlConfig {
        @JsonProperty("map_size")
        private int[] mapSize = new int[]{256, 64, 256};

        @JsonProperty("rooms_quantity")
        private Integer roomsQuantity = 100;
    }
}
