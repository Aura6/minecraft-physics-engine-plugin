package io.github.zilosz.physicsengine.handlers;

import io.github.zilosz.physicsengine.utils.Tool;
import io.github.zilosz.physicsengine.utils.Category;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import io.github.zilosz.physicsengine.utils.ToolManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ToolHandler implements Listener {

    @EventHandler void onInteract(PlayerInteractEvent event) {

        if (event.hasItem() && ToolManager.doesPlayerHaveTool(event.getMaterial(), event.getPlayer())) {
            Tool tool = ToolManager.getToolByMaterial(event.getPlayer(), event.getMaterial());

            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                tool.sendDescription();

            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                event.getPlayer().sendMessage(ChatUtils.color(Category.TOOL + "You have used the " + tool.getColor() + tool.getName() + "&f tool."));
                tool.onUse();
            }
        }
    }
}
