package io.github.zilosz.physicsengine.utils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Tool {

    protected Player player;

    public abstract String getName();

    public abstract String getColor();

    public abstract String getDescription();

    public abstract Material getMaterial();

    public abstract void onUse();

    public Player getPlayer() {
        return player;
    }

    public void giveToPlayer(Player player) {
        this.player = player;

        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatUtils.color("&l" + getColor() + getName() + "&r - Left click to get description."));
        itemStack.setItemMeta(meta);
        player.getInventory().addItem(itemStack);

        player.sendMessage(ChatUtils.color(Category.TOOL + "You've been given the " + getName() + " &rtool."));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }

    public void sendDescription() {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        player.sendMessage(ChatUtils.color(getColor() + "-".repeat(40)));
        player.sendMessage("");
        player.sendMessage(ChatUtils.color(Category.TOOL + "- " + getColor() + getName()));
        ChatUtils.insertBreaks(getDescription(), 40).forEach(player::sendMessage);
        player.sendMessage("");
        player.sendMessage(ChatUtils.color("Press 'q' to remove."));
        player.sendMessage(ChatUtils.color("Right click to use."));
        player.sendMessage(ChatUtils.color(getColor() + "-".repeat(40)));
    }
}
