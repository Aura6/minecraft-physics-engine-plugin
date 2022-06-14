package io.github.zilosz.physicsengine.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCManager {

    private static final HashMap<UUID, List<EntityPlayer>> npcsByWorld = new HashMap<>();

    public static void setupNPCs() {
        createNPC(ChatUtils.color("&a&lMr. Braden"), "FatTadPole", new Location(Bukkit.getWorld("lobby"), 0.5, 0, 12.5), 0, 180);
        createNPC(ChatUtils.color("&a&lMr. Braden"), "FatTadPole", new Location(Bukkit.getWorld("main"), 0.5, 0, 25), 0, 180);
        createNPC(ChatUtils.color("&e&lBall"), "valkyh", new Location(Bukkit.getWorld("main"), 5, 0, 24.5), 0, 167.5f);
        createNPC(ChatUtils.color("&b&lP. Elastic"), "UselessLucky", new Location(Bukkit.getWorld("main"), -9.5, 0, 23.5), 0, 211);
        createNPC(ChatUtils.color("&5&lP. Inelastic"), "interchanges", new Location(Bukkit.getWorld("main"), 8.5, 0, 23.5), 0, 159);
        createNPC(ChatUtils.color("&8&lInelastic"), "Daarkbladeee", new Location(Bukkit.getWorld("main"), -6, 0, 24.5), 0, 167.5f);
    }

    public static List<EntityPlayer> getNPCsInWorld(World world) {
        return npcsByWorld.get(world.getUID());
    }

    public static void createNPCList(World world) {
        npcsByWorld.put(world.getUID(), new ArrayList<>());
    }

    public static void addNPC(EntityPlayer npc, World world) {
        npcsByWorld.get(world.getUID()).add(npc);
    }

    public static void createNPC(String name, String nameOfPlayerWithSkin, Location location, float pitch, float yaw) {
        WorldServer worldServer = ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle();
        MinecraftServer mcServer = ((CraftServer) Bukkit.getServer()).getServer();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        EntityPlayer npc = new EntityPlayer(mcServer, worldServer, gameProfile);
        npc.b(location.getX(), location.getY(), location.getZ(), yaw, pitch);
        setNPCSkin(npc, nameOfPlayerWithSkin);
        addNPC(npc, location.getWorld());
    }

    public static void showWorldNPCsToPlayer(World world, Player player) {
        getNPCsInWorld(world).forEach(npc -> showNPCToPlayer(npc, player));
    }

    private static void showNPCToPlayer(EntityPlayer npc, Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
        connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
        connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.getBukkitEntity().getLocation().getYaw() * 256 / 360)));
    }

    private static void setNPCSkin(EntityPlayer npc, String nameOfPlayerWithSkin) {
        GameProfile profile = npc.fq();
        profile.getProperties().removeAll("textures");
        String[] textures = SkinUtils.getSkin(nameOfPlayerWithSkin);
        if (textures == null) return;
        profile.getProperties().put("textures", new Property("textures", textures[0], textures[1]));
    }
}
