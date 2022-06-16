package io.github.zilosz.physicsengine.tools;

import io.github.zilosz.physicsengine.utils.AttributeManager;
import io.github.zilosz.physicsengine.utils.CollisionToolTemplate;
import net.minecraft.util.Tuple;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

public class InelasticCollisionTool extends CollisionToolTemplate {

    public InelasticCollisionTool() {
        super(Material.RED_WOOL, Material.ORANGE_WOOL);
    }

    @Override
    public String getName() {
        return "Inelastic";
    }

    @Override
    public String getColor() {
        return "&8";
    }

    @Override
    public String getDescription() {
        return "Launches Block A with a mass of 'block_a_mass' at a speed of 'block_a_speed'. Launches Block B with a mass of 'block_b_mass' at a speed of 'block_b_speed'. The collision is inelastic, so the kinetic energy is multiplied by 'inelastic_COR'.";
    }

    @Override
    public Material getMaterial() {
        return Material.RED_WOOL;
    }

    @Override
    public Tuple<Double, Double> getNewBlockSpeeds(FallingBlock blockA, FallingBlock blockB) {
        double massA = AttributeManager.get("block_a_mass");
        double speedA = AttributeManager.get("block_a_speed");
        double massB = AttributeManager.get("block_b_mass");
        double speedB = AttributeManager.get("block_b_speed");
        double e = AttributeManager.get("inelastic_COR");

        double newSpeedA = speedA*(massA-e*massB)/(massA+massB) + speedB*(massB+e*massB)/(massA+massB);
        double newSpeedB = speedB*(massB-e*massA)/(massA+massB) + speedA*(massA+e*massA)/(massA+massB);

        return new Tuple<>(newSpeedA, newSpeedB);
    }
}
