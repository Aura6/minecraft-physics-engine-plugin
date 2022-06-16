package io.github.zilosz.physicsengine.utils;

import org.bukkit.entity.Player;

import java.util.*;

public class AttributeManager {

    private static final HashMap<String, Attribute> attributeMap = new HashMap<>();

    private static final List<Attribute> attributes = Arrays.asList(
            new Attribute("ball_speed", "blocks/s", 30, 0, 50),
            new Attribute("ball_min_vel", "blocks/s", 5, 0, 10),
            new Attribute("ball_vertical_COR", "constant", 0.9, 0, 1),
            new Attribute("ball_horizontal_COR", "constant", 0.8, 0, 1),
            new Attribute("block_a_speed", "blocks/s", 10, -30, 30),
            new Attribute("block_b_speed", "blocks/s", -15, -30, 30),
            new Attribute("block_b_distance", "blocks", 20, -30, 30),
            new Attribute("block_a_mass", "kg", 20, 0, 50),
            new Attribute("block_b_mass", "kg", 20, 0, 50),
            new Attribute("inelastic_COR", "constant", 0.8, 0, 1));

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
        player.sendMessage(ChatUtils.color(Category.ATTRIBUTE.color + "-".repeat(50)));
        player.sendMessage(ChatUtils.color("&aName   &eUnit   &dValue   &6Min Value   &cMax Value"));
        player.sendMessage(ChatUtils.color(Category.ATTRIBUTE.color + "-".repeat(50)));
        attributeMap.values().forEach(att -> player.sendMessage(ChatUtils.color("&a" + att.getName() + "   &e" + att.getUnit() + "   &d" + att.getValue() + "   &6" + att.getMinValue() + "   &c" + att.getMaxValue())));
        player.sendMessage(ChatUtils.color(Category.ATTRIBUTE.color + "-".repeat(50)));

    }
}
