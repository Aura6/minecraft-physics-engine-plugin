package io.github.zilosz.physicsengine.handlers;

import io.github.zilosz.physicsengine.tools.BallTool;
import io.github.zilosz.physicsengine.tools.InelasticCollisionTool;
import io.github.zilosz.physicsengine.tools.PerfectlyElasticCollisionTool;
import io.github.zilosz.physicsengine.tools.PerfectlyInelasticCollisionTool;
import io.github.zilosz.physicsengine.utils.Category;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import io.github.zilosz.physicsengine.utils.NPCManager;
import io.github.zilosz.physicsengine.utils.ToolManager;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

public class NPCInteractHandler implements Listener {

    private static final double SELECT_RANGE = 3;
    private static final double CHECK_RANGE = 0.9;

    @EventHandler void onPlayerAnimation(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        Location eyeLoc = player.getEyeLocation();
        Location checkLoc = eyeLoc.clone();

        while (checkLoc.distance(eyeLoc) <= SELECT_RANGE) {

            for (EntityPlayer npc : NPCManager.getNPCsInWorld(player.getWorld())) {
                CraftEntity npcEntity = npc.getBukkitEntity();

                if (npcEntity.getLocation().add(0, 0.9, 0).distance(checkLoc) <= CHECK_RANGE) {
                    onNPCInteraction(npcEntity, player);
                    return;
                }
            }

            checkLoc.add(eyeLoc.getDirection().clone().multiply(0.05));
        }
    }

    private void onNPCInteraction(CraftEntity npcEntity, Player player) {

        if (npcEntity.getName().contains("Mr. Braden")) {
            World world = Bukkit.getWorld("main");

            if (player.getWorld().getName().equals("main")) {
                world = Bukkit.getWorld("lobby");
                player.getInventory().clear();
                ToolManager.removeTools(player);
                player.setWalkSpeed(0.2f);

            } else {
                player.setWalkSpeed(0.5f);
            }

            player.teleport(world.getSpawnLocation());
            NPCManager.showWorldNPCsToPlayer(world, player);
            player.sendMessage(ChatUtils.color(Category.TELEPORT + "Mr. Braden teleported you!"));

        } else {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5f);

            if (npcEntity.getName().contains("Ball")) {
                ToolManager.addTool(new BallTool(), player);

            } else if (npcEntity.getName().contains("P. Elastic")) {
                ToolManager.addTool(new PerfectlyElasticCollisionTool(), player);

            } else if (npcEntity.getName().contains("P. Inelastic")) {
                ToolManager.addTool(new PerfectlyInelasticCollisionTool(), player);

            } else if (npcEntity.getName().contains("Inelastic")) {
                ToolManager.addTool(new InelasticCollisionTool(), player);
            }
        }
    }
}
