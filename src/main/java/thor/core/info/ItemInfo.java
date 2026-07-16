package thor.core.info;

import lombok.Getter;
import thor.core.info.part.FillType;
import thor.core.info.part.Weight;

public abstract class ItemInfo implements Weightable {
    @Getter
    private final String textId;
    @Getter
    private final FillType fillType;
    @Getter
    private final Weight weight;

    public ItemInfo(String textId, FillType fillType, Weight weight) {
        this.textId = textId;
        this.fillType = fillType;
        this.weight = weight;
    }
}
