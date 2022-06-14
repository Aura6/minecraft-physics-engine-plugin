package io.github.zilosz.physicsengine.tools;

import io.github.zilosz.physicsengine.utils.AttributeManager;
import io.github.zilosz.physicsengine.utils.CollisionToolTemplate;
import net.minecraft.util.Tuple;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

public class PerfectlyInelasticCollisionTool extends CollisionToolTemplate {

    public PerfectlyInelasticCollisionTool() {
        super(Material.LIGHT_BLUE_WOOL, Material.GRAY_WOOL);
    }

    @Override
    public String getName() {
        return "P. Inelastic";
    }

    @Override
    public String getColor() {
        return "&5";
    }

    @Override
    public String getDescription() {
        return "Launches Block A with a mass of 'block_a_mass' at a speed of 'block_a_speed'. Launches Block B with a mass of 'block_b_mass' at a speed of 'block_b_speed'. The collision is perfectly inelastic.";
    }

    @Override
    public Material getMaterial() {
        return Material.STONE_BRICKS;
    }

    @Override
    public Tuple<Double, Double> getNewBlockSpeeds(FallingBlock blockA, FallingBlock blockB) {
        double massA = AttributeManager.get("block_a_mass");
        double speedA = AttributeManager.get("block_a_speed");
        double massB = AttributeManager.get("block_b_mass");
        double speedB = AttributeManager.get("block_b_speed");

        double newSpeed = (massA*speedA + massB*speedB) / (massA + massB);

        return new Tuple<>(newSpeed, newSpeed);
    }
}
