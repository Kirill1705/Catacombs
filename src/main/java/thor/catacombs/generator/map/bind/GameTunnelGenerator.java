package thor.catacombs.generator.map.bind;

import org.jetbrains.annotations.NotNull;
import thor.catacombs.generator.Exit;
import thor.catacombs.generator.Tunnel;
import thor.catacombs.generator.map.RoomGraph;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.generator.structures.TunnelPart;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameTunnelGenerator implements TunnelGenerator {
    private final TunnelMaker tunnelMaker;

    public GameTunnelGenerator(TunnelMaker tunnelMaker) {

        this.tunnelMaker = tunnelMaker;
    }

    private @Nullable Tunnel bind(Room first, Room second, RoomGraph rooms) {
        List<Exit> firstExits = new ArrayList<>(first.getExits());
        List<Exit> secondExits = new ArrayList<>(second.getExits());
        Collections.shuffle(firstExits);
        Collections.shuffle(secondExits);
        for (Exit firstExit : firstExits) {
            for (Exit secondExit : secondExits) {
                if (firstExit.isClosed() || secondExit.isClosed())
                    continue;
                List<TunnelPart> parts = tunnelMaker.tryToMakeTunnel(firstExit, secondExit, rooms);
                if (parts != null) {
                    firstExit.setClosed(true);
                    secondExit.setClosed(true);
                    return new Tunnel(parts);
                }
            }
        }
        return null;
    }

    private Collection<RoomPair> createSortedPairs(List<Room> rooms) {
        List<RoomPair> result = new ArrayList<>();
        for (int i = 1; i < rooms.size(); i++) {
            for (int j = 0; j < i; j++) {
                result.add(new RoomPair(rooms.get(j), rooms.get(i)));
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public void generateTunnels(RoomGraph rooms) {
        List<Room> roomList = new ArrayList<>(rooms.getRooms());
        var roomPairs = createSortedPairs(roomList);
        int persents = 10;
        int counter = 0;
        System.out.println("MAKING TUNNELS! Total rooms: "+roomList.size()+ ". Possible tunnels: "+roomPairs.size());
        for (RoomPair current : roomPairs) {
            counter++;
            Tunnel tunnel = bind(current.first, current.second, rooms);
            if (tunnel != null) {
                rooms.addEdge(current.first, current.second, tunnel);
            }
            double progress = (double)counter/roomPairs.size();
            if (progress >= (double)persents/100) {
                System.out.println("progress "+persents + "%");
                persents += 10;
            }
        }
        System.out.println("Tunnels making finished successful!");
    }

    private record RoomPair(Room first, Room second) implements Comparable<RoomPair> {
        private double distance() {
            return first.getPosition().distanceSquared(second.getPosition());
        }

        @Override
        public int compareTo(@NotNull GameTunnelGenerator.RoomPair o) {
            return Double.compare(distance(), o.distance());
        }
    }
}
