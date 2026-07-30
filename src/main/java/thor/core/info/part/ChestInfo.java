package thor.core.info.part;

import lombok.Getter;
import org.bukkit.Material;
import thor.core.exception.DomainValidationException;
import thor.core.util.ConfUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class ChestInfo {
    @Getter
    private final ChestSize size;
    @Getter
    private final Quality quality;
    @Getter
    private final Probability probability;
    @Getter
    private final Material material;
    @Getter
    private final FillType fillType;
    @Getter
    private final Point position;

    public ChestInfo(ChestSize size, Quality quality, Probability probability, Material material, FillType fillType, Point position) {
        if (!position.moreOrEquals(new Point(0, 0, 0))) {
            throw new DomainValidationException(position);
        }
        this.size = size;
        this.quality = quality;
        this.probability = probability;
        this.material = ConfUtils.takeOrDefault(material, Material.CHEST);
        this.fillType = ConfUtils.takeOrDefault(fillType, FillType.CHEST);
        this.position = position;
    }
}
