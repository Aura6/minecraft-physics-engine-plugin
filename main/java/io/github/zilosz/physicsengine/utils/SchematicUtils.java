package io.github.zilosz.physicsengine.utils;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import io.github.zilosz.physicsengine.PhysicsEngine;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class SchematicUtils {

    public static void pasteSchematic(String name, Location location) throws IOException, MaxChangedBlocksException {
        File schematicFile = null;

        for (File file : Objects.requireNonNull(PhysicsEngine.getInstance().getDataFolder().listFiles())) {

            if (FileUtils.getFileExtension(file).equals(".schem") && FilenameUtils.removeExtension(file.getName()).equals(name)) {
                schematicFile = file;
                break;
            }
        }

        if (schematicFile == null) {
            Bukkit.getLogger().log(Level.SEVERE, "The schematic file does not exist.");
            return;
        }

        try (ClipboardReader reader = Objects.requireNonNull(ClipboardFormats.findByFile(schematicFile)).getReader(new FileInputStream(schematicFile))) {

            try (EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1)) {
                Operation operation = new ClipboardHolder(reader.read())
                        .createPaste(session)
                        .ignoreAirBlocks(false)
                        .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                        .build();
                Operations.complete(operation);
            }

        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }
}
