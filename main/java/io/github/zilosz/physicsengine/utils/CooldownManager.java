package io.github.zilosz.physicsengine.utils;

import io.github.zilosz.physicsengine.PhysicsEngine;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class CooldownManager {

    private static final HashMap<Tool, Integer> cooldownMap = new HashMap<>();

    public static boolean checkIfOnCooldown(Tool tool) {

        if (cooldownMap.containsKey(tool) && cooldownMap.get(tool) > 0) {
            tool.getPlayer().sendMessage(ChatUtils.color(Category.TOOL + tool.getColor() + "&l" + tool.getName() + "&7 has &5" + MathUtils.format.format(cooldownMap.get(tool)/20.0) + "&7 seconds left."));
            return true;
        }

        return false;
    }

    public static void startCooldown(Tool tool, double cooldown) {
        cooldownMap.put(tool, (int) (cooldown * 20));

        new BukkitRunnable() {

            @Override
            public void run() {
                cooldownMap.put(tool, cooldownMap.get(tool) - 1);

                if (cooldownMap.get(tool) == 0) {
                    tool.getPlayer().sendMessage(ChatUtils.color(Category.TOOL + "&7You can now use " + tool.getColor() + "&l" + tool.getName() + "."));
                    cancel();
                }
            }

        }.runTaskTimer(PhysicsEngine.getInstance(), 0, 1);
    }
}
