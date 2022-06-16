package io.github.zilosz.physicsengine.tools;

import io.github.zilosz.physicsengine.PhysicsEngine;
import io.github.zilosz.physicsengine.utils.*;
import net.minecraft.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

public class PerfectlyElasticCollisionTool extends CollisionToolTemplate{

    public PerfectlyElasticCollisionTool() {
        super(Material.GREEN_WOOL, Material.BLUE_WOOL);
        Bukkit.getPluginManager().registerEvents(this, PhysicsEngine.getInstance());
    }

    @Override
    public String getName() {
        return "P. Elastic";
    }

    @Override
    public String getColor() {
        return "&b";
    }

    @Override
    public String getDescription() {
        return "Launches Block A with a mass of 'block_a_mass' at a speed of 'block_a_speed'. Launches Block B with a mass of 'block_b_mass' at a speed of 'block_b_speed'. The collision is perfectly elastic.";
    }

    @Override
    public Material getMaterial() {
        return Material.GREEN_WOOL;
    }

    @Override
    public Tuple<Double, Double> getNewBlockSpeeds(FallingBlock blockA, FallingBlock blockB) {
        double massA = AttributeManager.get("block_a_mass");
        double speedA = AttributeManager.get("block_a_speed");
        double massB = AttributeManager.get("block_b_mass");
        double speedB = AttributeManager.get("block_b_speed");

        double newSpeedA = speedA*(massA-massB)/(massA+massB) + speedB*2*massB/(massA+massB);
        double newSpeedB = speedA*2*massA/(massA+massB) - speedB*(massA-massB)/(massA+massB);

        return new Tuple<>(newSpeedA, newSpeedB);
    }
}
