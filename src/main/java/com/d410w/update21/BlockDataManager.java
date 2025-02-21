package com.d410w.update21;

import com.d410w.update21.block.ModBlocks;
import com.d410w.update21.block.WeatherState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class BlockDataManager {
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringChiseled = new HashMap<>();
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringBulb = new HashMap<>();

    public static void populateChiseled() {
        weatheringChiseled.put(false, new HashMap<>());
        weatheringChiseled.put(true, new HashMap<>());

        weatheringChiseled.get(false).put(WeatherState.UNAFFECTED,
                ModBlocks.CHISELED_COPPER.get().defaultBlockState());
        weatheringChiseled.get(false).put(WeatherState.EXPOSED,
                ModBlocks.EXPOSED_CHISELED_COPPER.get().defaultBlockState());
        weatheringChiseled.get(false).put(WeatherState.WEATHERED,
                ModBlocks.WEATHERED_CHISELED_COPPER.get().defaultBlockState());
        weatheringChiseled.get(false).put(WeatherState.OXIDIZED,
                ModBlocks.OXIDIZED_CHISELED_COPPER.get().defaultBlockState());

        // Populate waxed states
        weatheringChiseled.get(true).put(WeatherState.UNAFFECTED,
                ModBlocks.WAXED_CHISELED_COPPER.get().defaultBlockState());
        weatheringChiseled.get(true).put(WeatherState.EXPOSED,
                ModBlocks.WAXED_EXPOSED_CHISELED_COPPER.get().defaultBlockState());
        weatheringChiseled.get(true).put(WeatherState.WEATHERED,
                ModBlocks.WAXED_WEATHERED_CHISELED_COPPER.get().defaultBlockState());
        weatheringChiseled.get(true).put(WeatherState.OXIDIZED,
                ModBlocks.WAXED_OXIDIZED_CHISELED_COPPER.get().defaultBlockState());
    }
    public static void populateBulb() {
        weatheringBulb.put(false, new HashMap<>());
        weatheringBulb.put(true, new HashMap<>());

        weatheringBulb.get(false).put(WeatherState.UNAFFECTED,
                ModBlocks.COPPER_BULB.get().defaultBlockState());
        weatheringBulb.get(false).put(WeatherState.EXPOSED,
                ModBlocks.EXPOSED_COPPER_BULB.get().defaultBlockState());
        weatheringBulb.get(false).put(WeatherState.WEATHERED,
                ModBlocks.WEATHERED_COPPER_BULB.get().defaultBlockState());
        weatheringBulb.get(false).put(WeatherState.OXIDIZED,
                ModBlocks.OXIDIZED_COPPER_BULB.get().defaultBlockState());

        // Populate waxed states
        weatheringBulb.get(true).put(WeatherState.UNAFFECTED,
                ModBlocks.WAXED_COPPER_BULB.get().defaultBlockState());
        weatheringBulb.get(true).put(WeatherState.EXPOSED,
                ModBlocks.WAXED_EXPOSED_COPPER_BULB.get().defaultBlockState());
        weatheringBulb.get(true).put(WeatherState.WEATHERED,
                ModBlocks.WAXED_WEATHERED_COPPER_BULB.get().defaultBlockState());
        weatheringBulb.get(true).put(WeatherState.OXIDIZED,
                ModBlocks.WAXED_OXIDIZED_COPPER_BULB.get().defaultBlockState());
    }

    public static void populateData() {
        populateChiseled();
        populateBulb();
    }
}
