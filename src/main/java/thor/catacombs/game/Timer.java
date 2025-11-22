package thor.catacombs.game;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Timer {
    private final Plugin plugin;
    private final Set<BukkitTask> tasks = new HashSet<>();
    private BukkitTask task;
    public Timer(Plugin plugin) {
        this.plugin=plugin;
    }
    public void cancelAllTasks() {
        if (task!=null) {
            task.cancel();
        }
        for (BukkitTask task: tasks) {
            task.cancel();
        }
    }
    private BukkitTask startTimer(int count, int cooldown, Function<Integer, Boolean> task) {
        int[] counter = new int[]{count};
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (task.apply(counter[0])) {
                    this.cancel();
                }
                counter[0]--;
            }
        }.runTaskTimer(plugin, 0, cooldown);
    }
    public BukkitTask replaceTimer(int count, int cooldown, Function<Integer, Boolean> function) {
        if (task!=null) {
            task.cancel();
        }
        task = startTimer(count, cooldown, function);
        return task;
    }
    public BukkitTask runTimerTask(int count, int cooldown, Function<Integer, Boolean> task) {
        BukkitTask bukkitTask = startTimer(count, cooldown, task);
        tasks.add(bukkitTask);
        return bukkitTask;
    }
}
