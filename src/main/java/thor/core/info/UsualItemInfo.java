package thor.core.info;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.part.FillType;
import thor.core.info.part.Weight;

import java.util.List;

public class UsualItemInfo extends ItemInfo {
    @Getter
    private final int min;
    @Getter
    private final int max;

    public UsualItemInfo(String textId, FillType fillType, Weight weight, int min, int max) {
        super(textId, fillType, weight);
        if (min <= 0 || min > max)
            throw new DomainValidationException(List.of(min, max));
        this.max = max;
        this.min = min;
    }

    public UsualItemInfo(String textId, FillType fillType, Weight weight) {
        this(textId, fillType, weight, 1, 1);
    }
}
