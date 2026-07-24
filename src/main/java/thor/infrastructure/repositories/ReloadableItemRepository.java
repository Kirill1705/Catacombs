package thor.infrastructure.repositories;

import thor.core.info.part.FillType;
import thor.core.port.mapping.dto.BookInfoDto;
import thor.core.port.mapping.dto.ItemInfoDto;
import thor.core.port.output.repository.ItemRepository;
import thor.usefulUtils.reload.Reloadable;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReloadableItemRepository implements ItemRepository, Reloadable {
    private final ItemRepository itemRepository;

    private List<BookInfoDto> books;
    private Map<String, List<ItemInfoDto>> items;

    public ReloadableItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        reload(null);
    }

    @Override
    public List<BookInfoDto> getBooks() {
        return books;
    }

    @Override
    public List<ItemInfoDto> getItems(List<String> fillTypes) {
        return fillTypes.stream()
                .flatMap(s -> items.get(s).stream())
                .toList();
    }

    @Override
    public void reload(Path file) {
        books = itemRepository.getBooks();
        items = Arrays.stream(FillType.values())
                .map(fillType -> fillType.name().toLowerCase())
                .collect(Collectors.toMap(
                        s -> s,
                        s -> itemRepository.getItems(List.of(s))
                ));
    }
}
