package io.github.zilosz.physicsengine.commands;

import io.github.zilosz.physicsengine.PhysicsEngine;
import io.github.zilosz.physicsengine.handlers.ArenaHandler;
import io.github.zilosz.physicsengine.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleArenaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) return false;

        ArenaHandler arenaHandler = PhysicsEngine.getInstance().getArenaHandler();

        if (args[0].equalsIgnoreCase("true")) {
            Bukkit.broadcastMessage(ChatUtils.color("&l&cAll arena restrictions are ON."));
            arenaHandler.setActivation(true);

        } else if (args[0].equalsIgnoreCase("false")) {
            Bukkit.broadcastMessage(ChatUtils.color("&l&aAll arena restrictions are OFF."));
            arenaHandler.setActivation(false);
        }

        return true;
    }
}
