package thor.infrastructure.repositories;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import thor.core.port.mapping.dto.BookInfoDto;
import thor.core.port.mapping.dto.ItemInfoDto;
import thor.core.port.output.repository.ItemRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemRepositoryImpl implements ItemRepository {
    private final Path bookPath;
    private final Map<String, Path> sources;
    private final YAMLMapper mapper;

    public ItemRepositoryImpl(Path bookPath, Map<String, Path> sources) {
        this.bookPath = bookPath;
        this.sources = sources;
        mapper = new YAMLMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Override
    public List<BookInfoDto> getBooks() {
        try {
            ConfigBooks configBooks = mapper.readValue(bookPath.toFile(), ConfigBooks.class);
            return configBooks.books();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemInfoDto> getItems(List<String> fillTypes) {
        List<ItemInfoDto> items = new ArrayList<>();
        for (Map.Entry<String, Path> entry: sources.entrySet()) {
            if (fillTypes.contains(entry.getKey())) {
                items.addAll(parseItems(entry.getValue().toFile(), entry.getKey()));
            }
        }
        return items;
    }

    private List<ItemInfoDto> parseItems(File file, String fillType) {
        try {
            ConfigItems configItems = mapper.readValue(file, ConfigItems.class);
            return configItems.items.stream()
                    .map(configItem -> new ItemInfoDto(configItem.id, configItem.weight, fillType, configItem.min, configItem.max))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record ConfigItem(String id, Integer weight, Integer min, Integer max) {}

    private record ConfigItems(List<ConfigItem> items) {}

    private record ConfigBooks(List<BookInfoDto> books) {}
}
