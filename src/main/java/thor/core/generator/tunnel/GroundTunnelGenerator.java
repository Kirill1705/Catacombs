package thor.core.generator.tunnel;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import thor.core.generator.TunnelGenerator;
import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.generator.tunnel.make.PartTunnelManagerImpl;
import thor.core.generator.tunnel.make.TunnelCreator;
import thor.core.generator.tunnel.make.TunnelPartsDispenser;
import thor.core.info.TunnelInfo;
import thor.core.info.part.TunnelType;
import thor.core.structure.Exit;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import thor.core.structure.create.PartTunnelCreator;
import thor.core.structure.create.PartTunnelCreatorImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GroundTunnelGenerator implements TunnelGenerator {

    private final List<TunnelInfo> horizontal;
    private final List<TunnelInfo> vertical;
    private final TunnelPartsDispenser dispenser;
    private final PartTunnelCreator creator;

    public GroundTunnelGenerator(List<TunnelInfo> tunnelInfos, TunnelPartsDispenser dispenser, PartTunnelCreator creator) {
        horizontal = tunnelInfos.stream()
                .filter(tunnelInfo -> !tunnelInfo.getType().equals(TunnelType.VERTICAL))
                .toList();
        vertical = tunnelInfos.stream()
                .filter(tunnelInfo -> tunnelInfo.getType().equals(TunnelType.VERTICAL))
                .toList();
        this.dispenser = dispenser;
        this.creator = creator;
    }

    @Override
    public void generateTunnels(GameMap map) {
        List<Room> roomList = new ArrayList<>(map.getGraph().getRooms());
        var roomPairs = createSortedPairs(roomList);
        int persents = 10;
        int counter = 0;
        int tunnelsCount = 0;
        log.info("MAKING TUNNELS! Total rooms: {}. Possible tunnels: {}", roomList.size(), (roomList.size() * (roomList.size() - 1)) / 2);
        for (RoomPair current : roomPairs) {
            counter++;
            double progress = (double)counter/roomPairs.size();
            if (progress >= (double)persents/100) {
                log.info("progress {}%", persents);
                persents += 10;
            }
            Collection<PartTunnel> tunnel = bind(current.first, current.second, map);
            if (tunnel == null) {
                continue;
            }
            tunnelsCount++;
            map.addEdge(current.first, current.second, tunnel);
        }
        log.info("Generated successfully {} tunnels", tunnelsCount);
    }

    public Collection<PartTunnel> bind(Room first, Room second, GameMap rooms) {
        List<Exit> firstExits = new ArrayList<>(first.getExits());
        List<Exit> secondExits = new ArrayList<>(second.getExits());
        Collections.shuffle(firstExits);
        Collections.shuffle(secondExits);
        for (Exit firstExit : firstExits) {
            for (Exit secondExit : secondExits) {
                if (firstExit.isClosed() || secondExit.isClosed())
                    continue;
                PartTunnelManagerImpl manager = new PartTunnelManagerImpl(firstExit, secondExit, horizontal, vertical, dispenser, creator, rooms.getField());
                if (manager.isUpsideDown()) {
                    continue;
                }
                Converter converter = manager.getConverter();
                List<PartTunnel> parts = TunnelCreator.VALUE.tryCreateTunnel(manager, manager.getSize(), converter.convertVector(firstExit.getOffset()), converter.convertVector(secondExit.getOffset()));
                if (parts != null) {
                    firstExit.setClosed(true);
                    secondExit.setClosed(true);
                    return parts;
                }
            }
        }
        return null;
    }

    private List<RoomPair> createSortedPairs(List<Room> rooms) {
        List<RoomPair> result = new ArrayList<>();
        final int maxTunnelLength = 100;
        for (int i = 0; i < rooms.size(); i++) {
            for (Room room : rooms) {
                RoomPair pair = new RoomPair(room, rooms.get(i));
                if (pair.distance() <= maxTunnelLength * maxTunnelLength) {
                    result.add(new RoomPair(room, rooms.get(i)));
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    private record RoomPair(Room first, Room second) implements Comparable<RoomPair> {
        public double distance() {
            return first.getPosition().distanceSquared(second.getPosition());
        }

        @Override
        public int compareTo(@NonNull RoomPair o) {
            return Double.compare(distance(), o.distance());
        }
    }
}
