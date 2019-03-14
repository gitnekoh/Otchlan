package me.nekoh.vacuum.task;

import me.nekoh.vacuum.Vacuum;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class VacuumTask extends BukkitRunnable {

    private int time = 180;
    private final Inventory vacuumInventory = Bukkit.createInventory(null, 54, ChatColor.GRAY + "Otchlan");

    @Override
    public void run() {
        if (time == 0) {
            TextComponent message = new TextComponent(ChatColor.DARK_AQUA + "Otwarto otchlan na 30 sekund.");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vacuum"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Kliknij aby otworzyc otchlan.").create()));

            Bukkit.getServer().getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message));
            Bukkit.getServer().getWorlds().get(0).getEntities().stream().filter(entity -> entity instanceof Item).forEach(entity -> {
                vacuumInventory.addItem(((Item) entity).getItemStack());
                entity.remove();
            });

            Bukkit.getScheduler().runTaskLater(Vacuum.getProvidingPlugin(Vacuum.class), () -> {
                Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getOpenInventory().getTitle().equalsIgnoreCase(vacuumInventory.getTitle())).forEach(HumanEntity::closeInventory);
                Bukkit.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.RED + "Otchlan zostala zamknieta."));
                vacuumInventory.clear();
                time = 180;
            }, 20 * 30);

        } else if ((time % 10 == 0 || time <= 5) & time > 0) {
            Bukkit.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + "Otwarcie otchlani za " + time + " sekund."));
        } else if (time < 0) {
            return;
        }
        time--;
    }

    public int getTime() {
        return time;
    }

    public Inventory getVacuumInventory() {
        return vacuumInventory;
    }
}
