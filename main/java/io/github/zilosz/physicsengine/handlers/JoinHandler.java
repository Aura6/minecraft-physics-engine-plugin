package io.github.zilosz.physicsengine.handlers;

import io.github.zilosz.physicsengine.utils.Category;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import io.github.zilosz.physicsengine.utils.NPCManager;
import io.github.zilosz.physicsengine.utils.ToolManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinHandler implements Listener {

    @EventHandler void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("");
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatUtils.color(Category.JOIN + player.getName() + " &rhas joined.")));
        player.sendMessage(ChatUtils.color(Category.COMMAND + "/setattribute sets an attribute value."));
        player.sendMessage(ChatUtils.color(Category.COMMAND + "/attributehelp sends all attribute data."));
        player.sendMessage(ChatUtils.color(Category.COMMAND + "/resetattributes resets all attribute values."));

        player.teleport(Objects.requireNonNull(Bukkit.getWorld("lobby")).getSpawnLocation());
        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setWalkSpeed(0.2f);
        player.getInventory().clear();

        NPCManager.showWorldNPCsToPlayer(player.getWorld(), player);
        ToolManager.createToolList(player);
    }

    @EventHandler void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(ChatUtils.color(Category.QUIT + event.getPlayer().getName() + " &rhas quit."));
    }
}
