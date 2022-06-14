package io.github.zilosz.physicsengine.commands;

import io.github.zilosz.physicsengine.utils.AttributeManager;
import io.github.zilosz.physicsengine.utils.Category;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetAttributesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            sender.sendMessage(ChatUtils.color(Category.COMMAND + "This command takes no arguments."));
            return true;
        }

        sender.sendMessage(ChatUtils.color(Category.ATTRIBUTE + "All attributes have been reset to their default values."));
        AttributeManager.init();

        return true;
    }
}
