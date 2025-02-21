package com.d410w.update21;

import com.d410w.update21.block.ModBlocks;
import com.d410w.update21.block.WeatherState;
import com.d410w.update21.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.HashMap;

import static com.d410w.update21.block.ModBlocks.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Update21.MODID)
public class Update21 {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "update21";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Update21() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTab.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        modEventBus.addListener(this::onLoadComplete);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Add more states and BlockStates as needed
        // Some common setup code
        LOGGER.info("HELLO FROM UPDATE 1.21");
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {
        // Populate the HashMap after all blocks are registered
        BlockDataManager.populateData();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.TRIAL_KEY);
            event.accept(ModItems.OMINOUS_TRIAL_KEY);
            event.accept(ModItems.BREEZE_ROD);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(CHISELED_COPPER);
            event.accept(EXPOSED_CHISELED_COPPER);
            event.accept(WEATHERED_CHISELED_COPPER);
            event.accept(OXIDIZED_CHISELED_COPPER);
            event.accept(WAXED_CHISELED_COPPER);
            event.accept(WAXED_EXPOSED_CHISELED_COPPER);
            event.accept(WAXED_WEATHERED_CHISELED_COPPER);
            event.accept(WAXED_OXIDIZED_CHISELED_COPPER);
        }
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(COPPER_BULB);
            event.accept(EXPOSED_COPPER_BULB);
            event.accept(WEATHERED_COPPER_BULB);
            event.accept(OXIDIZED_COPPER_BULB);
            event.accept(WAXED_COPPER_BULB);
            event.accept(WAXED_EXPOSED_COPPER_BULB);
            event.accept(WAXED_WEATHERED_COPPER_BULB);
            event.accept(WAXED_OXIDIZED_COPPER_BULB);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM UPDATE 1.21 CLIENT SETUP");
        }
    }
}
