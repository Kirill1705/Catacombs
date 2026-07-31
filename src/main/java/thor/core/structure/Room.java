package thor.core.structure;

import thor.core.info.RoomInfo;

import java.util.Collection;

public interface Room extends Structure {
    Collection<Exit> getExits();
    Collection<String> possibleTunnelsNames();
    RoomInfo getRoomInfo();
}
