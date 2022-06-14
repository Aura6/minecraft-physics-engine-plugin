package io.github.zilosz.physicsengine.tools;

import io.github.zilosz.physicsengine.PhysicsEngine;
import io.github.zilosz.physicsengine.utils.*;
import net.minecraft.core.particles.Particles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class BallTool extends Tool implements Listener {

    private Projectile ball;
    private Vector horizontalDirection;
    private boolean isUsed = false;

    public BallTool() {
        Bukkit.getPluginManager().registerEvents(this, PhysicsEngine.getInstance());
    }

    @Override
    public String getName() {
        return "Ball";
    }

    @Override
    public String getColor() {
        return "&e";
    }

    @Override
    public String getDescription() {
        return "Launches a ball with a speed of 'ball_speed'. Upon bouncing, the y component of the ball's velocity is multiplied by 'ball_vertical_COR'. The x and z components combine to make the ball's horizontal velocity, which is multiplied by 'ball_horizontal_COR'.";
    }

    @Override
    public Material getMaterial() {
        return Material.SNOWBALL;
    }

    @Override
    public void onUse() {

        if (isUsed) {
            player.sendMessage(ChatUtils.color(Category.TOOL + "The ball is already active."));
            return;
        }

        isUsed = true;

        Location launchLoc = player.getEyeLocation();
        ball = (Snowball) player.getWorld().spawnEntity(launchLoc, EntityType.SNOWBALL);
        ball.setVelocity(launchLoc.getDirection().multiply(AttributeManager.get("ball_speed")));
        ball.setCustomNameVisible(true);

        launchLoc.setPitch(0);
        horizontalDirection = launchLoc.getDirection();

        ParticleUtils.addParticleTrail(ball, Particles.aA, 0, 0, 0, 2, 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, 1);
    }

    @EventHandler void onProjectileHit(ProjectileHitEvent event) {
        BlockFace face = event.getHitBlockFace();

        if (face == null || event.getEntity() != ball) return;

        Vector velBefore = ball.getVelocity();
        double verticalVelAfter = velBefore.getY() * AttributeManager.get("ball_vertical_COR");
        double horizontalVelAfter = AttributeManager.get("ball_horizontal_COR")
                * Math.sqrt(Math.pow(velBefore.getX(), 2)+Math.pow(velBefore.getZ(), 2));

        if (face == BlockFace.UP || face == BlockFace.DOWN) {
            verticalVelAfter *= -1;

        } else if (face == BlockFace.WEST || face == BlockFace.EAST || face == BlockFace.SOUTH || face == BlockFace.NORTH) {
            horizontalDirection.multiply(-1);
        }

        Vector newVelocity = horizontalDirection.clone().multiply(horizontalVelAfter).add(new Vector(0, verticalVelAfter, 0));

        if (newVelocity.length() < AttributeManager.get("ball_min_vel")) {
            ParticleUtils.displayBoom(Particles.aA, ball.getLocation(), 2, 0.2, 4, 0, 0, 0);
            player.playSound(ball.getLocation(), Sound.ENTITY_SLIME_DEATH, 2, 2);
            ball.remove();
            isUsed = false;
            return;
        }

        ball.remove();
        ball = (Snowball) player.getWorld().spawnEntity(ball.getLocation(), EntityType.SNOWBALL);
        ball.setVelocity(newVelocity);
        player.getWorld().playSound(ball.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, 1);

        player.sendMessage(ChatUtils.color(Category.TOOL + "New Velocity: "
                + "&aX: &f" + MathUtils.formatDecimals(newVelocity.getX())
                + " &aY: &f" + MathUtils.formatDecimals(newVelocity.getY())
                + " &fZ: " + MathUtils.formatDecimals(newVelocity.getZ()) + "."));
    }
}
