package com.twihick.bunlyu.common.blocks.parts;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum LaunchPadPart implements IStringSerializable {

    MIDDLE("middle", Direction.DOWN, Direction.UP),
    NORTH("north", Direction.NORTH),
    SOUTH("south", Direction.SOUTH),
    EAST("east", Direction.EAST),
    WEST("west", Direction.WEST),
    NORTH_EAST("north_east", Direction.NORTH, Direction.EAST),
    NORTH_WEST("north_west", Direction.NORTH, Direction.WEST),
    SOUTH_EAST("south_east", Direction.SOUTH, Direction.EAST),
    SOUTH_WEST("south_west", Direction.SOUTH, Direction.WEST);

    private final String name;
    private final Direction[] directions;

    LaunchPadPart(String name, Direction... directions) {
        this.name = name;
        this.directions = directions;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Direction[] getDirections() {
        return this.directions;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
