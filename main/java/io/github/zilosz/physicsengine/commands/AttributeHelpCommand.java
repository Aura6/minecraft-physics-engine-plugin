package io.github.zilosz.physicsengine.commands;

import io.github.zilosz.physicsengine.utils.AttributeManager;
import io.github.zilosz.physicsengine.utils.Category;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AttributeHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length > 0) {
            sender.sendMessage(ChatUtils.color(Category.COMMAND + "This command takes no arguments."));
            return true;
        }

        AttributeManager.sendAllAttributeInformation((Player) sender);

        return false;
    }
}
