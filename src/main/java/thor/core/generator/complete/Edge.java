package thor.core.generator.complete;

import thor.core.structure.PartTunnel;
import thor.core.structure.Room;

import java.util.Collection;

public record Edge(Room from, Room to, Collection<PartTunnel> tunnels) { }
