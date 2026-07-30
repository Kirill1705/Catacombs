package thor.core.structure.create;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.generator.tunnel.convert.ConverterChain;
import thor.core.generator.tunnel.convert.ConverterImpl;
import thor.core.generator.tunnel.convert.Rotator;
import thor.core.info.part.PartTunnelInfo;
import thor.core.structure.PartTunnel;
import thor.core.structure.PartTunnelImpl;
import thor.core.structure.chest.ItemCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record PartTunnelCreatorImpl(ItemCreator generator) implements PartTunnelCreator {
    @Override
    public PartTunnel create(Point attachmentPoint, Point offset, PartTunnelInfo info, Converter converter) {
        Converter localConverter;
        Point infoAttachmentPoint = info.getAttachmentPoint();
        boolean rotated;
        if (offset.equals(new Point(0, 0, 1))) {
            infoAttachmentPoint = new Point(infoAttachmentPoint.z(), infoAttachmentPoint.y(), infoAttachmentPoint.x());
            localConverter = new Rotator(new ConverterImpl(attachmentPoint.subtract(infoAttachmentPoint), attachmentPoint));
            rotated = true;
        }
        else {
            localConverter = new ConverterImpl(attachmentPoint.subtract(infoAttachmentPoint), attachmentPoint);
            rotated = false;
        }
        return new PartTunnelImpl(generator, new ConverterChain(List.of(localConverter, converter)), info, rotated);
    }
}
