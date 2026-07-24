package thor.core.structure.chest;

import lombok.Getter;
import org.bukkit.Material;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.BookInfo;
import thor.core.info.ItemInfo;
import thor.core.info.UsualItemInfo;
import thor.core.info.part.ChestInfo;
import thor.core.info.part.Quality;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.AbstractStructurePart;

import java.util.ArrayList;
import java.util.List;

public class Chest extends AbstractStructurePart {
    @Getter
    private final Material material;
    @Getter
    private final Quality quality;
    @Getter
    private final List<Item> items = new ArrayList<>();
    @Getter
    private final List<Book> books = new ArrayList<>();

    public Chest(Converter converter, ChestInfo info, ItemCreator itemCreator) {
        super(converter, info.getPosition());
        this.material = info.getMaterial();
        this.quality = info.getQuality();
        List<ItemInfo> itemInfos = itemCreator.getItems(info.getFillType(), info.getSize().getValue(), info.getQuality().getValue());
        for (ItemInfo itemInfo: itemInfos) {
            if (itemInfo instanceof UsualItemInfo usualItemInfo) {
                items.add(new Item(usualItemInfo.getTextId(), usualItemInfo.getMin() + (int) (Math.random() * (usualItemInfo.getMax() - usualItemInfo.getMin() + 1))));
            }
            else if (itemInfo instanceof BookInfo bookInfo) {
                books.add(new Book(bookInfo.getTextId(), bookInfo.getLevel().getValue()));
            }
        }
    }

    public void place(WorldAccessor accessor) {
        accessor.placeChest(getPosition(), items, books, material);
    }
}
