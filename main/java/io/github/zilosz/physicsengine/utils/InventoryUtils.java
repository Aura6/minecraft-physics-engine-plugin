package io.github.zilosz.physicsengine.utils;

import org.bukkit.entity.Player;

public class InventoryUtils {

    public static int getNextOpenHotbarSlot(Player player) {

        for (int i = 0; i < 9; i++) {

            if (player.getInventory().getItem(i) == null) {
                return i;
            }
        }

        return -1;
    }
}
