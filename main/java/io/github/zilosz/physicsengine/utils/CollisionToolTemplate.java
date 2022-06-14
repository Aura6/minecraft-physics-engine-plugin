package io.github.zilosz.physicsengine.utils;

import io.github.zilosz.physicsengine.PhysicsEngine;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class CollisionToolTemplate extends Tool implements Listener {

    private boolean isUsed = false;
    private final Material materialA;
    private final Material materialB;

    public CollisionToolTemplate(Material materialA, Material materialB) {
        this.materialA = materialA;
        this.materialB = materialB;
        Bukkit.getPluginManager().registerEvents(this, PhysicsEngine.getInstance());
    }

    @Override
    public void onUse() {

        if (isUsed) {
            player.sendMessage(ChatUtils.color(Category.TOOL + "The collision has already started."));
            return;
        }

        isUsed = true;

        Location launchLoc = player.getEyeLocation();
        Vector direction = launchLoc.getDirection();
        launchLoc.add(direction);

        FallingBlock blockA = player.getWorld().spawnFallingBlock(launchLoc, materialA, (byte) 0);
        player.sendMessage(ChatUtils.color(Category.TOOL + "&aBlock A initial velocity: &f" + MathUtils.formatDecimals(AttributeManager.get("block_a_speed"))) + ".");
        blockA.setGravity(false);

        Location blockBLoc = launchLoc.add(direction.clone().multiply(AttributeManager.get("block_b_distance")));
        FallingBlock blockB = player.getWorld().spawnFallingBlock(blockBLoc, materialB, (byte) 0);
        player.sendMessage(ChatUtils.color(Category.TOOL + "&bBlock B initial velocity: &f" + MathUtils.formatDecimals(AttributeManager.get("block_b_speed"))) + ".");
        blockB.setGravity(false);

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        player.sendMessage(ChatUtils.color(Category.TOOL + "The collision starts in 3 seconds."));

        new BukkitRunnable() {

            @Override
            public void run() {
                startCollision(blockA, blockB, direction);
            }

        }.runTaskLater(PhysicsEngine.getInstance(), 60);
    }

    private void startCollision(FallingBlock blockA, FallingBlock blockB, Vector direction) {
        final Vector[] velA = {direction.clone().multiply(AttributeManager.get("block_a_speed"))};
        blockA.setVelocity(velA[0]);

        final Vector[] velB = {direction.clone().multiply(AttributeManager.get("block_b_speed"))};
        blockB.setVelocity(velB[0]);

        new BukkitRunnable() {
            private boolean hasCollided = false;

            @Override
            public void run() {

                if (!blockA.isValid() || !blockB.isValid()) {
                    cancel();
                    return;
                }

                blockA.setVelocity(velA[0]);
                blockB.setVelocity(velB[0]);

                if (isBlockColliding(blockA)) {
                    onHitWall('A', blockA, blockB);
                    cancel();
                    return;
                }

                if (isBlockColliding(blockB)) {
                    onHitWall('B', blockA, blockB);
                    cancel();
                    return;
                }

                if (!hasCollided && areBlocksColliding(blockA, blockB)) {
                    isUsed = false;
                    hasCollided = true;

                    Tuple<Double, Double> newSpeeds = getNewBlockSpeeds(blockA, blockB);

                    velA[0] = direction.clone().multiply(newSpeeds.a());
                    velB[0] = direction.clone().multiply(newSpeeds.b());

                    player.sendMessage(ChatUtils.color(Category.TOOL + "&aBlock A final velocity: &f" + MathUtils.formatDecimals(newSpeeds.a())) + ".");
                    player.sendMessage(ChatUtils.color(Category.TOOL + "&bBlock B final velocity: &f" + MathUtils.formatDecimals(newSpeeds.b())) + ".");

                    Location collisionLoc = MathUtils.getAverageLocation(blockA.getLocation(), blockB.getLocation());
                    ParticleUtils.displayBoom(Particles.af, collisionLoc, 4, 0.25, 8, 0, 0, 0);
                    player.getWorld().playSound(collisionLoc, Sound.BLOCK_ANVIL_LAND, 1, 2);
                }
            }

        }.runTaskTimer(PhysicsEngine.getInstance(), 0, 0);
    }

    public abstract Tuple<Double, Double> getNewBlockSpeeds(FallingBlock blockA, FallingBlock blockB);

    private boolean isBlockColliding(FallingBlock block) {

        for (BlockFace face : BlockFace.values()) {

            if (block.getLocation().getBlock().getRelative(face).getType().isSolid()) {
                return true;
            }
        }

        return false;
    }

    private void onHitWall(char blockId, FallingBlock blockA, FallingBlock blockB) {
        player.sendMessage(ChatUtils.color(Category.TOOL + "Block " + blockId + " hit a wall. The simulation has been canceled."));
        blockA.remove();
        blockB.remove();
    }

    private boolean areBlocksColliding(FallingBlock blockA, FallingBlock blockB) {
        return blockA.getBoundingBox().overlaps(blockB.getBoundingBox());
    }

    @EventHandler
    void onBlockChange(EntityChangeBlockEvent event) {
        if (event.getTo() == materialB || event.getTo() == materialB) event.setCancelled(true);
    }
}
