package me.nekoh.vacuum;

import me.nekoh.vacuum.command.VacuumCommand;
import me.nekoh.vacuum.task.VacuumTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class Vacuum extends JavaPlugin {

    private static Vacuum instance;
    private VacuumTask vacuumTask;

    @Override
    public void onEnable() {
        instance = this;
        vacuumTask = new VacuumTask();
        vacuumTask.runTaskTimer(this, 20, 20);
        this.getCommand("vacuum").setExecutor(new VacuumCommand());
    }

    public static Vacuum getInstance() {
        return instance;
    }

    public VacuumTask getVacuumTask() {
        return vacuumTask;
    }
}
