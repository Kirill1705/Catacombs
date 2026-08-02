package thor.core.world;

import thor.core.structure.manager.SignalPartManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartManagerHolder {
    private final Map<String, List<SignalPartManager>> signalManagers = new HashMap<>();

    public void addSignaManager(String worldName, SignalPartManager manager) {
        addManager(worldName, signalManagers, manager);
    }

    public List<SignalPartManager> getManagers(String worldName) {
        return getManagers(worldName, signalManagers);
    }

    private <T> void addManager(String worldName, Map<String, List<T>> managersMap, T manager) {
        if (!managersMap.containsKey(worldName)) {
            managersMap.put(worldName, new ArrayList<>());
        }
        List<T> managerList = managersMap.get(worldName);
        managerList.add(manager);
    }

    private<T> List<T> getManagers(String worldName, Map<String, List<T>> managersMap) {
        return managersMap.getOrDefault(worldName, List.of());
    }
}
