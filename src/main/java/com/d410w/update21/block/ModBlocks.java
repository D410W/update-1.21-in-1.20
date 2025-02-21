package com.d410w.update21.block;

import com.d410w.update21.BlockDataManager;
import com.d410w.update21.Update21;
import com.d410w.update21.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Update21.MODID);
    //waxed chiseled copper
    public static final RegistryObject<Block> WAXED_CHISELED_COPPER = registerWeatheredBlock("waxed_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.UNAFFECTED, true);
    public static final RegistryObject<Block> WAXED_EXPOSED_CHISELED_COPPER = registerWeatheredBlock("waxed_exposed_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.EXPOSED, true);
    public static final RegistryObject<Block> WAXED_WEATHERED_CHISELED_COPPER = registerWeatheredBlock("waxed_weathered_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.WEATHERED, true);
    public static final RegistryObject<Block> WAXED_OXIDIZED_CHISELED_COPPER = registerWeatheredBlock("waxed_oxidized_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.OXIDIZED, true);
    //chiseled copper
    public static final RegistryObject<Block> CHISELED_COPPER = registerWeatheredBlock("chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).randomTicks(), WeatherState.UNAFFECTED, false);
    public static final RegistryObject<Block> EXPOSED_CHISELED_COPPER = registerWeatheredBlock("exposed_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).randomTicks(), WeatherState.EXPOSED, false);
    public static final RegistryObject<Block> WEATHERED_CHISELED_COPPER = registerWeatheredBlock("weathered_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).randomTicks(), WeatherState.WEATHERED, false);
    public static final RegistryObject<Block> OXIDIZED_CHISELED_COPPER = registerWeatheredBlock("oxidized_chiseled_copper",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER).randomTicks(), WeatherState.OXIDIZED, false);
    //waxed copper bulb
    public static final RegistryObject<Block> WAXED_COPPER_BULB = registerWeatheredBlock("waxed_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.UNAFFECTED, true, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_BULB = registerWeatheredBlock("waxed_exposed_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.EXPOSED, true, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_BULB = registerWeatheredBlock("waxed_weathered_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.WEATHERED, true, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_BULB = registerWeatheredBlock("waxed_oxidized_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), WeatherState.OXIDIZED, true, BlockDataManager.weatheringBulb);
    //copper bulb
    public static final RegistryObject<Block> COPPER_BULB = registerWeatheredBlock("copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).randomTicks(), WeatherState.UNAFFECTED, false, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> EXPOSED_COPPER_BULB = registerWeatheredBlock("exposed_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER).randomTicks(), WeatherState.EXPOSED, false, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> WEATHERED_COPPER_BULB = registerWeatheredBlock("weathered_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER).randomTicks(), WeatherState.WEATHERED, false, BlockDataManager.weatheringBulb);
    public static final RegistryObject<Block> OXIDIZED_COPPER_BULB= registerWeatheredBlock("oxidized_copper_bulb",
            BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER).randomTicks(), WeatherState.OXIDIZED, false, BlockDataManager.weatheringBulb);

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
    public static RegistryObject<Block> registerWeatheredBlock(String name, BlockBehaviour.Properties props,
                                             WeatherState weatherState, boolean waxState) {
        return registerWeatheredBlock(name, props, weatherState, waxState, BlockDataManager.weatheringChiseled);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
