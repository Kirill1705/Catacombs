package thor.catacombs.info;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.structure.TunnelDivider;
import thor.catacombs.info.structure.TunnelType;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.dataStructures.OffsetBox;

import java.util.ArrayList;
import java.util.List;

public class TestTunnelInfo implements TunnelInfo {
    private final TunnelType type;
    private final List<PartTunnelInfo> parts = new ArrayList<>();

    public TestTunnelInfo(TunnelType type, BlockPosition size, BlockPosition atBlockPosition, List<ChestInfo> chests, List<PlayerSpawnInfo> playerSpawnPlaces) {
        this.type = type;
        BlockPosition mask = type.getOneMask();
        atBlockPosition = atBlockPosition.multiply(mask);
        TunnelDivider divider = new TunnelDivider(size, type);
        List<OffsetBox> boxes = divider.getParts();
        for (OffsetBox box : boxes) {
            parts.add(new TestPartTunnelInfo(getName(), box.size(), chests, playerSpawnPlaces, type, atBlockPosition));
        }
    }

    @Override
    public TunnelType getType() {
        return type;
    }

    @Override
    public PartTunnelInfo getByIdx(int idx) {
        return parts.get(idx%parts.size());
    }

    @Override
    public int getWeight() {
        return 20;
    }

    @Override
    public String getName() {
        return "test_tunnel";
    }
}
