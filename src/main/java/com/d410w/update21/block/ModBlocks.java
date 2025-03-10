package com.d410w.update21.block;

import com.d410w.update21.ModData.BlockDataManager;
import com.d410w.update21.Update21;
import com.d410w.update21.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Update21.MODID);

    //chiseled copper
    public static final RegistryObject<Block> CHISELED_COPPER = registerWeatheredBlock("chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).randomTicks(), WeatherState.UNAFFECTED, false, BlockDataManager.weatheringChiseled);
    public static final RegistryObject<Block> EXPOSED_CHISELED_COPPER = registerWeatheredBlock("exposed_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).randomTicks(), WeatherState.EXPOSED, false, BlockDataManager.weatheringChiseled);
    public static final RegistryObject<Block> WEATHERED_CHISELED_COPPER = registerWeatheredBlock("weathered_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).randomTicks(), WeatherState.WEATHERED, false, BlockDataManager.weatheringChiseled);
    public static final RegistryObject<Block> OXIDIZED_CHISELED_COPPER = registerWeatheredBlock("oxidized_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), WeatherState.OXIDIZED, false, BlockDataManager.weatheringChiseled);
    //waxed chiseled copper
    public static final RegistryObject<Block> WAXED_CHISELED_COPPER = registerWeatheredBlock("waxed_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.UNAFFECTED, true, BlockDataManager.weatheringChiseled);
    public static final RegistryObject<Block> WAXED_EXPOSED_CHISELED_COPPER = registerWeatheredBlock("waxed_exposed_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER), WeatherState.EXPOSED, true, BlockDataManager.weatheringChiseled);
    public static final RegistryObject<Block> WAXED_WEATHERED_CHISELED_COPPER = registerWeatheredBlock("waxed_weathered_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER), WeatherState.WEATHERED, true, BlockDataManager.weatheringChiseled);
    public static final RegistryObject<Block> WAXED_OXIDIZED_CHISELED_COPPER = registerWeatheredBlock("waxed_oxidized_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), WeatherState.OXIDIZED, true, BlockDataManager.weatheringChiseled);
  //copper grate
    public static final RegistryObject<Block> COPPER_GRATE = registerLoggWeather("copper_grate",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).randomTicks().isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.UNAFFECTED, false, BlockDataManager.weatheringGrate);
    public static final RegistryObject<Block> EXPOSED_COPPER_GRATE = registerLoggWeather("exposed_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).randomTicks().isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.EXPOSED, false, BlockDataManager.weatheringGrate);
    public static final RegistryObject<Block> WEATHERED_COPPER_GRATE = registerLoggWeather("weathered_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).randomTicks().isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.WEATHERED, false, BlockDataManager.weatheringGrate);
    public static final RegistryObject<Block> OXIDIZED_COPPER_GRATE = registerLoggWeather("oxidized_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER).isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.OXIDIZED, false, BlockDataManager.weatheringGrate);
    //waxed copper grate
    public static final RegistryObject<Block> WAXED_COPPER_GRATE = registerLoggWeather("waxed_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.UNAFFECTED, true, BlockDataManager.weatheringGrate);
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_GRATE = registerLoggWeather("waxed_exposed_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.EXPOSED, true, BlockDataManager.weatheringGrate);
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_GRATE = registerLoggWeather("waxed_weathered_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.WEATHERED, true, BlockDataManager.weatheringGrate);
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_GRATE = registerLoggWeather("waxed_oxidized_copper_grate",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER).isValidSpawn((state, level, pos, entityType) ->
                    false), WeatherState.OXIDIZED, true, BlockDataManager.weatheringGrate);
    //copper bulb
    public static final RegistryObject<Block> COPPER_BULB = registerCopperBulb("copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).randomTicks(), WeatherState.UNAFFECTED, false, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> EXPOSED_COPPER_BULB = registerCopperBulb("exposed_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).randomTicks(), WeatherState.EXPOSED, false, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WEATHERED_COPPER_BULB = registerCopperBulb("weathered_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).randomTicks(), WeatherState.WEATHERED, false, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> OXIDIZED_COPPER_BULB = registerCopperBulb("oxidized_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), WeatherState.OXIDIZED, false, BlockDataManager.weatheringBulb);
    //waxed copper bulb
    public static final RegistryObject<Block> WAXED_COPPER_BULB = registerCopperBulb("waxed_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.UNAFFECTED, true, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_BULB = registerCopperBulb("waxed_exposed_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER), WeatherState.EXPOSED, true, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_BULB = registerCopperBulb("waxed_weathered_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER), WeatherState.WEATHERED, true, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_BULB = registerCopperBulb("waxed_oxidized_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), WeatherState.OXIDIZED, true, BlockDataManager.weatheringBulb);
    //copper door
    public static final RegistryObject<WeatheringDoorBlock> COPPER_DOOR = registerBlock("copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).randomTicks(), WeatherState.UNAFFECTED, false, BlockDataManager.weatheringDoor));
    public static final RegistryObject<WeatheringDoorBlock> EXPOSED_COPPER_DOOR = registerBlock("exposed_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).randomTicks(), WeatherState.EXPOSED, false, BlockDataManager.weatheringDoor));
    public static final RegistryObject<WeatheringDoorBlock> WEATHERED_COPPER_DOOR = registerBlock("weathered_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).randomTicks(), WeatherState.WEATHERED, false, BlockDataManager.weatheringDoor));
    public static final RegistryObject<WeatheringDoorBlock> OXIDIZED_COPPER_DOOR = registerBlock("oxidized_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), WeatherState.OXIDIZED, false, BlockDataManager.weatheringDoor));
    //waxed copper door
    public static final RegistryObject<WeatheringDoorBlock> WAXED_COPPER_DOOR = registerBlock("waxed_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).strength(3.0f).sound(SoundType.COPPER).noOcclusion(), WeatherState.UNAFFECTED, true, BlockDataManager.weatheringDoor));
    public static final RegistryObject<WeatheringDoorBlock> WAXED_EXPOSED_COPPER_DOOR = registerBlock("waxed_exposed_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).strength(3.0f).sound(SoundType.COPPER).noOcclusion(), WeatherState.EXPOSED, true, BlockDataManager.weatheringDoor));
    public static final RegistryObject<WeatheringDoorBlock> WAXED_WEATHERED_COPPER_DOOR = registerBlock("waxed_weathered_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).strength(3.0f).sound(SoundType.COPPER).noOcclusion(), WeatherState.WEATHERED, true, BlockDataManager.weatheringDoor));
    public static final RegistryObject<WeatheringDoorBlock> WAXED_OXIDIZED_COPPER_DOOR = registerBlock("waxed_oxidized_copper_door",
            () -> new WeatheringDoorBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER).strength(3.0f).sound(SoundType.COPPER).noOcclusion(), WeatherState.OXIDIZED, true, BlockDataManager.weatheringDoor));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RegistryObject<Block> registerWeatheredBlock(String name, BlockBehaviour.Properties props,
                                                                WeatherState weatherState, boolean waxState,
                                                                HashMap hashType) {
        // Register the block
        RegistryObject<Block> block = BLOCKS.register(name,
                () -> new WeatheringBlock(props, weatherState, waxState, hashType));
        registerBlockItem(name, block);
        return block;
    }

    public static RegistryObject<Block> registerCopperBulb(String name, BlockBehaviour.Properties props,
                                                           WeatherState weatherState, boolean waxState,
                                                           HashMap hashType) {
        RegistryObject<Block> block = BLOCKS.register(name,
                () -> new CopperBulb(props, weatherState, waxState, hashType));
        registerBlockItem(name, block);
        return block;
    }

    public static RegistryObject<Block> registerLoggWeather(String name, BlockBehaviour.Properties props,
                                                           WeatherState weatherState, boolean waxState,
                                                           HashMap hashType) {
        RegistryObject<Block> block = BLOCKS.register(name,
                () -> new LoggedWeathering(props, weatherState, waxState, hashType));
        registerBlockItem(name, block);
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
