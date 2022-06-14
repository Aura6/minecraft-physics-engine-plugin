package io.github.zilosz.physicsengine;

import io.github.zilosz.physicsengine.commands.*;
import io.github.zilosz.physicsengine.handlers.ArenaHandler;
import io.github.zilosz.physicsengine.handlers.JoinHandler;
import io.github.zilosz.physicsengine.handlers.NPCInteractHandler;
import io.github.zilosz.physicsengine.handlers.ToolHandler;
import io.github.zilosz.physicsengine.utils.NPCManager;
import io.github.zilosz.physicsengine.utils.WorldUtils;
import io.github.zilosz.physicsengine.utils.AttributeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class PhysicsEngine extends JavaPlugin {

    public static PhysicsEngine instance;
    private ArenaHandler arenaHandler;

    @Override
    public void onEnable() {
        instance = this;

        registerListeners();
        registerCommands();

        WorldUtils.setupWorlds();
        NPCManager.setupNPCs();
        AttributeManager.init();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinHandler(), this);
        arenaHandler = new ArenaHandler();
        Bukkit.getPluginManager().registerEvents(arenaHandler, this);
        Bukkit.getPluginManager().registerEvents(new NPCInteractHandler(), this);
        Bukkit.getPluginManager().registerEvents(new ToolHandler(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("setarenarestrictions")).setExecutor(new ToggleArenaCommand());
        Objects.requireNonNull(getCommand("location")).setExecutor(new LocationCommand());
        Objects.requireNonNull(getCommand("setattribute")).setExecutor(new SetAttributeCommand());
        Objects.requireNonNull(getCommand("resetattributes")).setExecutor(new ResetAttributesCommand());
        Objects.requireNonNull(getCommand("attributehelp")).setExecutor(new AttributeHelpCommand());
    }

    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public static PhysicsEngine getInstance() {
        return instance;
    }
}
