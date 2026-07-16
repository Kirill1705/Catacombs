package thor.core.info;

import lombok.Getter;
import thor.core.info.part.EnchantmentLevel;
import thor.core.info.part.FillType;
import thor.core.info.part.Weight;

public class BookInfo extends ItemInfo {
    @Getter
    private final EnchantmentLevel level;
    public BookInfo(String textId, FillType fillType, Weight weight, EnchantmentLevel level) {
        super(textId, fillType, weight);
        this.level = level;
    }
}
