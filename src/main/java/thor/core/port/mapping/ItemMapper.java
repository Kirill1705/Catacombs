package thor.core.port.mapping;

import thor.core.info.BookInfo;
import thor.core.info.ItemInfo;
import thor.core.info.UsualItemInfo;
import thor.core.info.part.EnchantmentLevel;
import thor.core.info.part.FillType;
import thor.core.info.part.Weight;
import thor.core.port.mapping.dto.BookInfoDto;
import thor.core.port.mapping.dto.ItemInfoDto;
import thor.core.util.ConfUtils;

public class ItemMapper {
    public static ItemInfo fromDto(ItemInfoDto dto) {
        if (dto.min() == null) {
            return new UsualItemInfo(dto.id(), FillType.valueOf(dto.fillType().toUpperCase()), new Weight(dto.weight()));
        }
        return new UsualItemInfo(dto.id(), FillType.valueOf(dto.fillType().toUpperCase()), new Weight(dto.weight()), dto.min(), dto.max());
    }

    public static ItemInfo fromDto(BookInfoDto dto) {
        return new BookInfo(dto.id(), FillType.CHEST, new Weight(dto.weight()), new EnchantmentLevel(dto.level()));
    }
}
