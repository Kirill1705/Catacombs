package thor.core.port.output.repository;

import thor.core.port.mapping.dto.BookInfoDto;
import thor.core.port.mapping.dto.ItemInfoDto;

import java.util.List;

public interface ItemRepository {
    List<BookInfoDto> getBooks();

    List<ItemInfoDto> getItems(List<String> fillTypes);
}
