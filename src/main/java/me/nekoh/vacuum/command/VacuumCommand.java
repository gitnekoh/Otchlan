package me.nekoh.vacuum.command;

import me.nekoh.vacuum.Vacuum;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VacuumCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("vacuum") && !command.getName().equalsIgnoreCase("otchlan")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Tylko gracz.");
            return false;
        }

        if (Vacuum.getInstance().getVacuumTask().getTime() > 0) {
            sender.sendMessage(ChatColor.RED + "Otchlan jest aktualnie zamknieta.");
            return false;
        }

        ((Player) sender).openInventory(Vacuum.getInstance().getVacuumTask().getVacuumInventory());
        return true;
    }
}
