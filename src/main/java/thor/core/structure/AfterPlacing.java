package thor.core.structure;

import thor.core.port.output.WorldAccessor;

public interface AfterPlacing {
    void afterPlace(WorldAccessor accessor);
}
