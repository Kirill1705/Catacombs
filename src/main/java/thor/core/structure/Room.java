package thor.core.structure;

import java.util.Collection;

public interface Room extends Structure {
    Collection<Exit> getExits();
    Collection<String> possibleTunnelsNames();
}
