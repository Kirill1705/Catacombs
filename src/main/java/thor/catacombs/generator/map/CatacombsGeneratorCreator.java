package thor.catacombs.generator.map;

import org.bukkit.configuration.file.FileConfiguration;
import thor.catacombs.events.creators.PartTunnelCreator;
import thor.catacombs.events.creators.RoomCreator;
import thor.catacombs.generator.map.bind.GameTunnelGenerator;
import thor.catacombs.generator.map.bind.GameTunnelMaker;
import thor.catacombs.generator.map.bind.TunnelFixedMaker;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;

import java.util.List;

public record CatacombsGeneratorCreator(RoomCreator roomCreator, PartTunnelCreator tunnelCreator, FileConfiguration configuration) implements GeneratorCreator{
    @Override
    public MapGenerator create(List<RoomInfo> roomInfo, List<TunnelInfo> tunnelInfo) {
        return new CatacombsGenerator(new GameTunnelGenerator(new TunnelFixedMaker(new GameTunnelMaker(tunnelInfo, tunnelCreator), tunnelCreator)), new GameRoomGenerator(new GeneratorConfigurator(configuration), roomInfo, roomCreator));
    }
}
