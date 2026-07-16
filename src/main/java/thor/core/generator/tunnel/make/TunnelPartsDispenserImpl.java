package thor.core.generator.tunnel.make;

import thor.core.exception.DomainValidationException;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class TunnelPartsDispenserImpl implements TunnelPartsDispenser {
    private final double startNeutralPart;

    public TunnelPartsDispenserImpl(double startNeutralPart) {
        if (startNeutralPart < 0 || startNeutralPart > 0.5) {
            throw new DomainValidationException("Start neutral tunnel part should be between 0 and 0.5");
        }
        this.startNeutralPart = startNeutralPart;
    }

    @Override
    public TunnelProgress getProgress(BlockPosition size, BlockPosition position) {
        double completeDistance = position.size();
        if (completeDistance / size.size() < startNeutralPart) {
            return TunnelProgress.START;
        }
        else if (completeDistance / size.size() >= (1 - startNeutralPart)) {
            return TunnelProgress.END;
        }
        else {
            return TunnelProgress.NEUTRAL;
        }
    }
}
