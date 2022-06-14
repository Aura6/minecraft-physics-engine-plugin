package io.github.zilosz.physicsengine.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.Random;

public class MathUtils {

    private static final Random random = new Random(System.nanoTime());
    private static final DecimalFormat format = new DecimalFormat("#.##");

    public static Vector getRandomUpwardVector() {
        double x = random.nextDouble() * 2 - 1;
        double y = random.nextDouble();
        double z = random.nextDouble() * 2 - 1;

        return new Vector(x, y, z).normalize();
    }

    public static Location getAverageLocation(Location loc1, Location loc2) {
        double x = (loc1.getX()+loc2.getX()) / 2;
        double y = (loc1.getY()+loc2.getY()) / 2;
        double z = (loc1.getZ()+loc2.getZ()) / 2;

        return new Location(loc1.getWorld(), x, y, z);
    }

    public static double formatDecimals(double number) {
        return Double.parseDouble(format.format(number));
    }
}
