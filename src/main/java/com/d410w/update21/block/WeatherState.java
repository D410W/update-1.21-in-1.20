package com.d410w.update21.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public enum WeatherState implements StringRepresentable { // Must implement StringRepresentable
    UNAFFECTED("unaffected"),
    EXPOSED("exposed"),
    WEATHERED("weathered"),
    OXIDIZED("oxidized");

    private final String name;

    WeatherState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public WeatherState next() {
        return switch (this) {
            case UNAFFECTED -> EXPOSED;
            case EXPOSED -> WEATHERED;
            case WEATHERED -> OXIDIZED;
            default -> this;
        };
    }

    public WeatherState prev() {
        return switch (this) {
            case EXPOSED -> UNAFFECTED;
            case WEATHERED -> EXPOSED;
            case OXIDIZED -> WEATHERED;
            default -> this;
        };
    }
}
