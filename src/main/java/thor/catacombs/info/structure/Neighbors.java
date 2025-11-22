package thor.catacombs.info.structure;

import org.bukkit.block.Block;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import org.jetbrains.annotations.NotNull;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

import java.util.List;

interface Neighbors {
    List<Block> getNeighbors(Block block);

    Block getNext(Block block);

    boolean hasNextInLine(Block block);

    boolean hasNext(Block block);

    static @NotNull Neighbors of(ImmutableBox box, @NotNull TunnelType type) {
        switch (type) {
            case X -> {
                return new Neighbors() {
                    @Override
                    public List<Block> getNeighbors(Block block) {
                        return List.of(block.getRelative(0, 0, 1), block.getRelative(0, 0, -1), block.getRelative(0, 1, 0), block.getRelative(0, -1, 0));
                    }

                    @Override
                    public Block getNext(Block block) {
                        if (hasNextInLine(block)) {
                            return block.getRelative(0, 0, 1);
                        }
                        return block.getRelative(0, 1, -box.size().z()+1);
                    }

                    @Override
                    public boolean hasNextInLine(Block block) {
                        return block.getZ() < box.end().z();
                    }

                    @Override
                    public boolean hasNext(Block block) {
                        return block.getY() < box.end().y();
                    }
                };
            }
            case Z -> {
                return new Neighbors() {
                    @Override
                    public List<Block> getNeighbors(Block block) {
                        return List.of(block.getRelative(1, 0, 0), block.getRelative(-1, 0, 0), block.getRelative(0, 1, 0), block.getRelative(0, -1, 0));
                    }

                    @Override
                    public Block getNext(Block block) {
                        if (hasNextInLine(block)) {
                            return block.getRelative(1, 0, 0);
                        }
                        return block.getRelative(-box.size().x() + 1, 1, 0);
                    }

                    @Override
                    public boolean hasNextInLine(Block block) {
                        return block.getX() < box.end().x();
                    }

                    @Override
                    public boolean hasNext(Block block) {
                        return block.getY() < box.end().y();
                    }
                };
            }
            case VERTICAL -> {
                return new Neighbors() {
                    @Override
                    public List<Block> getNeighbors(Block block) {
                        return List.of(block.getRelative(1, 0, 0), block.getRelative(-1, 0, 0), block.getRelative(0, 0, 1), block.getRelative(0, 0, -1));
                    }

                    @Override
                    public Block getNext(Block block) {
                        if (hasNextInLine(block)) {
                            return block.getRelative(1, 0, 0);
                        }
                        return block.getRelative(-box.size().x() + 1, 0, 1);
                    }

                    @Override
                    public boolean hasNextInLine(Block block) {
                        return block.getX() < box.end().x();
                    }

                    @Override
                    public boolean hasNext(Block block) {
                        return block.getZ() < box.end().z();
                    }
                };
            }
        }
        throw new RuntimeException("TunnelType must not be null!");
    }
}
