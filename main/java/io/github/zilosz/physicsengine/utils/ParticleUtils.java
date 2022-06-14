package io.github.zilosz.physicsengine.utils;

import io.github.zilosz.physicsengine.PhysicsEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleUtils {

    public static void sendParticlePacket(Player player, ParticleType particle, Location loc, float offsetX, float offsetY, float offsetZ, int amount) {
        ((CraftPlayer) player).getHandle().b.a(new PacketPlayOutWorldParticles(particle, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), offsetX, offsetY, offsetZ, amount, 0));
    }

    public static void sendParticlePacketToAll(ParticleType particle, Location loc, float offsetX, float offsetY, float offsetZ, int amount) {
        Bukkit.getOnlinePlayers().forEach(player -> sendParticlePacket(player, particle, loc, offsetX, offsetY, offsetZ, amount));
    }

    public static void addParticleTrail(Entity entity, ParticleType particle, float offsetX, float offsetY, float offsetZ, int amount, int tickRefreshRate) {

        new BukkitRunnable() {

            @Override
            public void run() {

                if (entity.isValid()) {
                    sendParticlePacketToAll(particle, entity.getLocation(), offsetX, offsetY, offsetZ, amount);

                } else {
                    cancel();
                }
            }

        }.runTaskTimer(PhysicsEngine.getInstance(), 0, tickRefreshRate);
    }

    public static void displayBoom(ParticleType particle, Location centerLoc, double radius, double radiusStep, int streakCount, float offsetX, float offsetY, float offsetZ) {

        for (int i = 0; i < streakCount; i++) {
            Vector vector = MathUtils.getRandomUpwardVector().multiply(radiusStep);
            Location stepLoc = centerLoc.clone();

            new BukkitRunnable() {

                @Override
                public void run() {

                    if (stepLoc.distance(centerLoc) > radius) {
                        cancel();
                        return;
                    }

                    sendParticlePacketToAll(particle, stepLoc, offsetX, offsetY, offsetZ, 1);
                    stepLoc.add(vector);
                }

            }.runTaskTimer(PhysicsEngine.getInstance(), 0, 1);
        }
    }
}
