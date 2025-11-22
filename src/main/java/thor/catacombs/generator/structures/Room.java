package thor.catacombs.generator.structures;

import thor.catacombs.generator.Exit;
import thor.catacombs.info.structure.interfaces.RoomInfo;

import java.util.List;

public interface Room extends Structure, PlayerSpawnable {
    List<Exit> getExits();
    @Override
    RoomInfo getInfo();
}
