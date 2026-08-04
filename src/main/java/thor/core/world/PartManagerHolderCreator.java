package thor.core.world;

import thor.core.structure.manager.SignalPartManager;
import thor.core.structure.manager.StructurePartsManager;

import java.util.List;
import java.util.Map;

public class PartManagerHolderCreator {
    public PartManagerHolder create(Map<String, List<StructurePartsManager>> managersMap) {
        PartManagerHolder holder = new PartManagerHolder();
        for (Map.Entry<String, List<StructurePartsManager>> entry: managersMap.entrySet()) {
            for (StructurePartsManager manager: entry.getValue()) {
                if (manager instanceof SignalPartManager signalPartManager) {
                    holder.addSignaManager(entry.getKey(), signalPartManager);
                }
            }
        }
        return holder;
    }
}
