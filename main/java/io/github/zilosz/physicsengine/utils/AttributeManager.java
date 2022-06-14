package io.github.zilosz.physicsengine.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AttributeManager {

    private static final HashMap<String, Attribute> attributeMap = new HashMap<>();

    private static final List<Attribute> attributes = Arrays.asList(
            new Attribute("ball_speed", 2.1, 0, 10),
            new Attribute("ball_min_vel", 0.31, 0, 10),
            new Attribute("ball_vertical_COR", 0.9, 0, 1),
            new Attribute("ball_horizontal_COR", 0.8, 0, 1),
            new Attribute("block_a_speed", 1, -10, 10),
            new Attribute("block_b_speed", -1, -10, 10),
            new Attribute("block_b_distance", 15, -50, 50),
            new Attribute("block_a_mass", 1, 0, 10),
            new Attribute("block_b_mass", 1, 0, 10),
            new Attribute("inelastic_COR", 0.8, 0, 1));

    public static void init() {
        attributes.forEach(attribute -> attributeMap.put(attribute.getName(), attribute.getClone()));
    }

    public static void set(String name, double newValue) {
        attributeMap.get(name).setValue(newValue);
    }

    public static boolean containsName(String name) {
        return attributeMap.containsKey(name);
    }

    public static double get(String name) {
        return attributeMap.get(name).getValue();
    }

    public static boolean isValueWithinBounds(String name, double value) {
        return attributeMap.get(name).isValueWithinBounds(value);
    }

    public static void sendAllAttributeInformation(Player player) {
        player.sendMessage(ChatUtils.color(Category.ATTRIBUTE.color + "-".repeat(40)));

        List<List<String>> table = new ArrayList<>();
        table.add(Arrays.asList("Name", "Val", "Min Bound", "Max Bound"));

        attributeMap.values().forEach(attribute -> table.add(Arrays.asList(
                attribute.getName(),
                String.valueOf(attribute.getValue()),
                String.valueOf(attribute.getMinValue()),
                String.valueOf(attribute.getMaxValue()))));

        table.forEach(list -> player.sendMessage(String.join("    ", list)));
        player.sendMessage(ChatUtils.color(Category.ATTRIBUTE.color + "-".repeat(40)));

    }
}
