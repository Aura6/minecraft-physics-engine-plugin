package io.github.zilosz.physicsengine.utils;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ToolManager {

    private static final HashMap<UUID, HashMap<Material, Tool>> toolsByMaterial = new HashMap<>();

    public static void createToolList(Player player) {
        toolsByMaterial.put(player.getUniqueId(), new HashMap<>());
    }

    public static void addTool(Tool tool, Player player) {

        if (doesPlayerHaveTool(tool.getMaterial(), player)) {
            player.sendMessage(ChatUtils.color(Category.TOOL + "You already have this tool!"));

        } else {
            toolsByMaterial.get(player.getUniqueId()).put(tool.getMaterial(), tool);
            tool.giveToPlayer(player);
        }
    }

    public static void removeTool(Material material, Player player) {
        toolsByMaterial.get(player.getUniqueId()).remove(material);
        player.getInventory().remove(material);
    }

    public static void removeTools(Player player) {
        toolsByMaterial.get(player.getUniqueId()).clear();
        player.getInventory().clear();
    }

    public static void attemptToolRemoval(Item item, Player player) {
        Material droppedMaterial = item.getItemStack().getType();

        if (doesPlayerHaveTool(droppedMaterial, player)) {
            removeTool(droppedMaterial, player);
        }
    }

    public static boolean doesPlayerHaveTool(Material material, Player player) {
        return toolsByMaterial.get(player.getUniqueId()).containsKey(material);
    }

    public static Tool getToolByMaterial(Player player, Material material) {
        return toolsByMaterial.get(player.getUniqueId()).get(material);
    }
}
