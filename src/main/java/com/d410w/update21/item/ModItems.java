package com.d410w.update21.item;

import com.d410w.update21.Update21;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Update21.MODID);

    public static final RegistryObject<Item> TRIAL_KEY = ITEMS.register("trial_key",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OMINOUS_TRIAL_KEY = ITEMS.register("ominous_trial_key",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BREEZE_ROD = ITEMS.register("breeze_rod",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
