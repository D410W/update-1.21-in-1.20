package com.d410w.update21.block;

import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import com.d410w.update21.block.WeatherState;

import java.util.HashMap;

public class CopperBulb extends WeatheringBlock {


    public CopperBulb(Properties properties, WeatherState weatherState, boolean waxState, HashMap passedHash) {
        super(properties, weatherState, waxState, passedHash);
    }
}
