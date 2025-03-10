package com.d410w.update21.ModData;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ModBlockSetTypes {
    public static BlockSetType COPPER_TYPE = BlockSetType.register(new BlockSetType(
            "copper",
            true,
            SoundType.COPPER,
            ModSoundEvents.COPPER_DOOR_OPEN.get(),
            ModSoundEvents.COPPER_DOOR_CLOSE.get(),
            ModSoundEvents.COPPER_TRAPDOOR_OPEN.get(),
            ModSoundEvents.COPPER_TRAPDOOR_OPEN.get(),
            null,
            null,
            null,
            null));
}
