package io.github.zilosz.physicsengine.utils;

public enum Category {
    JOIN("Join", "&a"),
    QUIT("Quit", "&4"),
    TELEPORT("Teleport", "&c"),
    TOOL("Tool", "&d"),
    ATTRIBUTE("Attribute", "&b"),
    COMMAND("Command", "&4");

    public final String name;
    public final String color;

    Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "&l" + name.toUpperCase() + ">> &r";
    }
}
