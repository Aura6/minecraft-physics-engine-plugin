package io.github.zilosz.physicsengine.utils;

import com.sk89q.worldedit.MaxChangedBlocksException;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.io.IOException;
import java.util.Random;

public class WorldUtils {

    public static void setupWorlds() {
        createEmptyWorldWithSchematic("lobby", 0, 0, 0, "lobby");
        createEmptyWorldWithSchematic("main", 0, 0, 0, "spiritarena");
    }

    public static World createEmptyWorldWithSchematic(String worldName, int spawnX, int spawnY, int spawnZ, String schematicName) {
        World world = createEmptyWorld(worldName);
        world.setSpawnLocation(spawnX, spawnY, spawnZ);
        NPCManager.createNPCList(world);

        try {
            SchematicUtils.pasteSchematic(schematicName, world.getSpawnLocation());

        } catch (IOException | MaxChangedBlocksException e) {
            e.printStackTrace();
        }

        return world;
    }

    private static World createEmptyWorld(String name) {

        return new WorldCreator(name).generator(new ChunkGenerator() {

            public byte[] generate(World world, Random random, int x, int z) {
                return new byte[32768];

            }
        }).createWorld();
    }
}
