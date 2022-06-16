package io.github.zilosz.physicsengine.handlers;

import io.github.zilosz.physicsengine.utils.ToolManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class ArenaHandler implements Listener {

    private boolean isActive = true;

    public void setActivation(boolean activation) {
        isActive = activation;
    }

    @EventHandler void onBlockBreak(BlockBreakEvent event) {
        if (isActive) event.setCancelled(true);
    }

    @EventHandler void onToggleFly(PlayerToggleFlightEvent event) {
        if (isActive) event.setCancelled(true);
    }

    @EventHandler void onEntityDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) event.setCancelled(true);
    }

    @EventHandler void onBlockPlace(BlockPlaceEvent event) {
        if (isActive) event.setCancelled(true);
    }

    @EventHandler void onItemPickup(EntityPickupItemEvent event) {
        if (isActive) event.setCancelled(true);
    }

    @EventHandler void onDropItem(PlayerDropItemEvent event) {

        if (isActive) {
            ToolManager.attemptToolRemoval(event.getItemDrop(), event.getPlayer());
            event.getItemDrop().remove();
        }
    }

    @EventHandler void onEntityDrop(EntityDropItemEvent event) {
        event.setCancelled(true);
    }
}
