package com.d410w.update21;

import com.d410w.update21.block.ModBlocks;
import com.d410w.update21.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.d410w.update21.block.ModBlocks.*;
import static com.d410w.update21.block.ModBlocks.WAXED_OXIDIZED_COPPER_GRATE;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Update21.MODID);

    public static final RegistryObject<CreativeModeTab> UPDATE21_TAB = CREATIVE_MODE_TABS.register("update21_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TRIAL_KEY.get()))
                    .title(Component.translatable("creativetab.update21_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.CHISELED_COPPER.get());
                        output.accept(ModBlocks.EXPOSED_CHISELED_COPPER.get());
                        output.accept(ModBlocks.WEATHERED_CHISELED_COPPER.get());
                        output.accept(ModBlocks.OXIDIZED_CHISELED_COPPER.get());
                        output.accept(ModBlocks.WAXED_CHISELED_COPPER.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_CHISELED_COPPER.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_CHISELED_COPPER.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_CHISELED_COPPER.get());

                        output.accept(ModBlocks.COPPER_BULB.get());
                        output.accept(ModBlocks.EXPOSED_COPPER_BULB.get());
                        output.accept(ModBlocks.WEATHERED_COPPER_BULB.get());
                        output.accept(ModBlocks.OXIDIZED_COPPER_BULB.get());
                        output.accept(ModBlocks.WAXED_COPPER_BULB.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_BULB.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_BULB.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_BULB.get());

                        output.accept(ModBlocks.COPPER_GRATE.get());
                        output.accept(ModBlocks.EXPOSED_COPPER_GRATE.get());
                        output.accept(ModBlocks.WEATHERED_COPPER_GRATE.get());
                        output.accept(ModBlocks.OXIDIZED_COPPER_GRATE.get());
                        output.accept(ModBlocks.WAXED_COPPER_GRATE.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_GRATE.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_GRATE.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_GRATE.get());

                        output.accept(ModItems.TRIAL_KEY.get());
                        output.accept(ModItems.OMINOUS_TRIAL_KEY.get());
                        output.accept(ModItems.BREEZE_ROD.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
