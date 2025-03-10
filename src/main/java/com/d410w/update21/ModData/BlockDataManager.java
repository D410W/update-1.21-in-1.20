package com.d410w.update21.ModData;

import com.d410w.update21.block.WeatherState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

import static com.d410w.update21.block.ModBlocks.*;

public class BlockDataManager {
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringChiseled = new HashMap<>();
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringBulb = new HashMap<>();
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringGrate = new HashMap<>();
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringDoor = new HashMap<>();
    public static HashMap<Boolean, Map<WeatherState, BlockState>> weatheringTrapdoor = new HashMap<>();

    public static void populate(HashMap<Boolean, Map<WeatherState, BlockState>> map, RegistryObject<? extends Block> first, RegistryObject<? extends Block> second, RegistryObject<? extends Block> third, RegistryObject<? extends Block> fourth,
                                RegistryObject<? extends Block> wfirst, RegistryObject<? extends Block> wsecond, RegistryObject<? extends Block> wthird, RegistryObject<? extends Block> wfourth) {
        map.put(false, new HashMap<>());
        map.put(true, new HashMap<>());

        map.get(false).put(WeatherState.UNAFFECTED,
                first.get().defaultBlockState());
        map.get(false).put(WeatherState.EXPOSED,
                second.get().defaultBlockState());
        map.get(false).put(WeatherState.WEATHERED,
                third.get().defaultBlockState());
        map.get(false).put(WeatherState.OXIDIZED,
                fourth.get().defaultBlockState());

        // Populate waxed states
        map.get(true).put(WeatherState.UNAFFECTED,
                wfirst.get().defaultBlockState());
        map.get(true).put(WeatherState.EXPOSED,
                wsecond.get().defaultBlockState());
        map.get(true).put(WeatherState.WEATHERED,
                wthird.get().defaultBlockState());
        map.get(true).put(WeatherState.OXIDIZED,
                wfourth.get().defaultBlockState());
    }

    public static void populateData() {
        populate(weatheringChiseled, CHISELED_COPPER, EXPOSED_CHISELED_COPPER, WEATHERED_CHISELED_COPPER, OXIDIZED_CHISELED_COPPER,
                WAXED_CHISELED_COPPER, WAXED_EXPOSED_CHISELED_COPPER, WAXED_WEATHERED_CHISELED_COPPER, WAXED_OXIDIZED_CHISELED_COPPER);
        populate(weatheringBulb, COPPER_BULB, EXPOSED_COPPER_BULB, WEATHERED_COPPER_BULB, OXIDIZED_COPPER_BULB,
                WAXED_COPPER_BULB, WAXED_EXPOSED_COPPER_BULB, WAXED_WEATHERED_COPPER_BULB, WAXED_OXIDIZED_COPPER_BULB);
        populate(weatheringGrate, COPPER_GRATE, EXPOSED_COPPER_GRATE, WEATHERED_COPPER_GRATE, OXIDIZED_COPPER_GRATE,
                WAXED_COPPER_GRATE, WAXED_EXPOSED_COPPER_GRATE, WAXED_WEATHERED_COPPER_GRATE, WAXED_OXIDIZED_COPPER_GRATE);
        populate(weatheringDoor, COPPER_DOOR, EXPOSED_COPPER_DOOR, WEATHERED_COPPER_DOOR, OXIDIZED_COPPER_DOOR,
                WAXED_COPPER_DOOR, WAXED_EXPOSED_COPPER_DOOR, WAXED_WEATHERED_COPPER_DOOR, WAXED_OXIDIZED_COPPER_DOOR);
    }
}
