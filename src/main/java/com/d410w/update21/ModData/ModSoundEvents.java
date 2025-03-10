package com.d410w.update21.ModData;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.d410w.update21.Update21.MODID;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> COPPER_BULB_PLACE =
            registerSoundEvents("block.copper_bulb.place");
    public static final RegistryObject<SoundEvent> COPPER_BULB_BREAK =
            registerSoundEvents("block.copper_bulb.break");
    public static final RegistryObject<SoundEvent> COPPER_BULB_STEP =
            registerSoundEvents("block.copper_bulb.step");

    public static final RegistryObject<SoundEvent> COPPER_BULB_TOGGLE =
            registerSoundEvents("block.copper_bulb.toggle");

    public static final RegistryObject<SoundEvent> COPPER_GRATE_PLACE =
            registerSoundEvents("block.copper_grate.place");
    public static final RegistryObject<SoundEvent> COPPER_GRATE_BREAK =
            registerSoundEvents("block.copper_grate.break");
    public static final RegistryObject<SoundEvent> COPPER_GRATE_STEP =
            registerSoundEvents("block.copper_grate.step");

    public static final RegistryObject<SoundEvent> COPPER_DOOR_OPEN =
            registerSoundEvents("block.copper_door.open");
    public static final RegistryObject<SoundEvent> COPPER_DOOR_CLOSE =
            registerSoundEvents("block.copper_door.close");

    public static final RegistryObject<SoundEvent> COPPER_TRAPDOOR_OPEN =
            registerSoundEvents("block.copper_trapdoor.toggle");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () ->
                SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
