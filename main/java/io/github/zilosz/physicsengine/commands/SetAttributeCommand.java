package io.github.zilosz.physicsengine.commands;

import io.github.zilosz.physicsengine.utils.Category;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import io.github.zilosz.physicsengine.utils.AttributeManager;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetAttributeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 2) {
            sender.sendMessage(ChatUtils.color(Category.COMMAND + "&cThis command takes two arguments. (1) A valid attribute name. (2) A numerical value representing the new attribute value."));

        } else if (!NumberUtils.isCreatable(args[1])) {
            sender.sendMessage(ChatUtils.color(Category.COMMAND + "&cThe second argument must be a numerical value."));

        } else if (!AttributeManager.containsName(args[0])) {
            sender.sendMessage(ChatUtils.color(Category.ATTRIBUTE + "&c'" + args[0] + "' is not a valid attribute."));

        } else if (!AttributeManager.isValueWithinBounds(args[0], Double.parseDouble(args[1]))) {
            sender.sendMessage(ChatUtils.color(Category.ATTRIBUTE + "The provided value is out of bounds for this attribute. Do /attributehelp to see attribute boundaries."));

        } else {
            sender.sendMessage(ChatUtils.color(Category.ATTRIBUTE + "The &a" + args[0] + " &rattribute now has a value of " + Category.ATTRIBUTE.color + args[1] + "&r."));
            AttributeManager.set(args[0], Double.parseDouble(args[1]));
        }

        return true;
    }
}
