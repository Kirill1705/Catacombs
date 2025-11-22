package thor.catacombs.info.structure;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.MainInfo;
import thor.catacombs.info.block.creators.MainCreator;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.dataStructures.*;

import java.util.*;

public class GameTunnelInfo implements TunnelInfo {
    private final MainInfo mainInfo;
    private final List<PartTunnelInfo> tunnels = new ArrayList<>();

    @Override
    public int getWeight() {
        return mainInfo.getWeight();
    }

    @Override
    public String getName() {
        return mainInfo.getName();
    }

    private final TunnelType type;
    @Override
    public TunnelType getType() {
        return type;
    }
    private @NotNull Set<Block> getAttachmentBlockPosition(Block begin, Neighbors neighbors) {
        Queue<Block> queue = new LinkedList<>();
        Set<Block> visited = new HashSet<>();
        queue.add(begin);
        while (!queue.isEmpty()) {
            Block current = queue.remove();
            visited.add(begin);
            for (Block block: neighbors.getNeighbors(current)) {
                if (!isCollidable(block)&&!visited.contains(block)) {
                    queue.add(block);
                    visited.add(block);
                }
            }
        }
        return visited;
    }
    private Block getMinimumOf(Collection<Block> blocks) {
        int minY = Integer.MAX_VALUE;
        Block minBlock = null;
        for (Block block: blocks) {
            if (minBlock==null||block.getY()<minY) {
                minBlock = block;
                minY = block.getY();
            }
        }
        TreeSet<Block> blockSet = new TreeSet<>(
                Comparator.comparingInt(block ->
                        block.getX() + block.getY() + block.getZ()
                )
        );
        final int minYConst = minY;
        blockSet.addAll(blocks.stream().filter(block -> block.getY() == minYConst).toList());
        return (Block) blockSet.toArray()[blockSet.size()/2];
    }
    private boolean isCollidable(Block block) {
        return !block.isPassable()&&block.isSolid();
    }
    private Block findFirstAir(Block block, Neighbors neighbors) {
        boolean isSecondAir = false;
        Block result = null;
        int windowSize = Integer.MIN_VALUE;
        do {
            do {
                if (isCollidable(block)) {
                    isSecondAir = true;
                } else if (!isCollidable(block) && isSecondAir) {
                    Set<Block> blocks = getAttachmentBlockPosition(block, neighbors);
                    if (result==null||blocks.size()>windowSize) {
                        result = getMinimumOf(blocks);
                        windowSize = blocks.size();
                    }
                }
                block = neighbors.getNext(block);
            } while (neighbors.hasNextInLine(block));
            isSecondAir = false;
            block = neighbors.getNext(block);
        }
        while (neighbors.hasNext(block));
        return result;
    }
    @Override
    public PartTunnelInfo getByIdx(int idx) {
        idx = idx % tunnels.size();
        return tunnels.get(idx);
    }
    private TunnelType fillParticularStructures(ImmutableBox box, AttributeRegistry registry) {
        Block first = box.begin().toLocation().getBlock();
        for (TunnelType tunnelType: TunnelType.values()) {
            Block atBlockPosition = findFirstAir(first, Neighbors.of(box, tunnelType));
            if (atBlockPosition!=null) {
                TunnelDivider divider = new TunnelDivider(box.size(), tunnelType);
                PartTunnelInfoCreator creator = new GamePartTunnelCreator(box, registry, new BlockLocation(atBlockPosition.getLocation()).subtract(box.begin()), getName());
                List<OffsetBox> partBoxes = divider.getParts();
                for (OffsetBox offsetBox: partBoxes) {
                    tunnels.add(creator.create(offsetBox, tunnelType));
                }
                return tunnelType;
            }
        }
        throw new RuntimeException("Can`t parse tunnel!");
    }
    public GameTunnelInfo(ImmutableBox box, AttributeRegistry registry, BlockInfoGenerator generator) {
        mainInfo = generator.getBlockInfo(AttributeHolderType.MAIN.getName(), new MainCreator(registry)).getFirst();
        type = fillParticularStructures(box, registry);
    }
}

