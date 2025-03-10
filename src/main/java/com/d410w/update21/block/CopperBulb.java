package com.d410w.update21.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import com.d410w.update21.ModData.ModSoundEvents;

import java.util.HashMap;

public class CopperBulb extends WeatheringBlock {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final EnumProperty<WeatherState> OXIDATION = EnumProperty.create("oxidation", WeatherState.class);
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    private static final SoundType BULB_SOUNDS = new SoundType(
            1.0F, // Volume multiplier
            1.0F, // Pitch multiplier
            ModSoundEvents.COPPER_BULB_BREAK.get(),  // Break sound
            ModSoundEvents.COPPER_BULB_STEP.get(),   // Step sound
            ModSoundEvents.COPPER_BULB_PLACE.get(),  // Place sound
            ModSoundEvents.COPPER_BULB_STEP.get(),    // Hit sound
            ModSoundEvents.COPPER_BULB_STEP.get()    // Fall sound
    );

    public CopperBulb(Properties properties, WeatherState weatherState, boolean waxState, HashMap<WeatherState, Block> passedHash) {
        super(properties.isRedstoneConductor((state, level, pos) -> false)
                        .sound(BULB_SOUNDS),
                weatherState, waxState, passedHash);
        // Initialize child properties AFTER parent's setup
        registerDefaultState(stateDefinition.any()
                .setValue(OXIDATION, weatherState)
                .setValue(WAXED, waxState)
                .setValue(POWERED, false)
                .setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder); // Get parent properties
        builder.add(POWERED, LIT); // Add child properties
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(LIT)) {
            return 0;
        }

        return switch (state.getValue(OXIDATION)) {
            case UNAFFECTED -> 15;
            case EXPOSED -> 12 ;
            case WEATHERED -> 8;
            case OXIDIZED -> 4;

        };
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true; // This enables comparator functionality
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        // Example: Return 15 when powered, 0 otherwise
        return state.getValue(LIT) ? 15 : 0;

        // For inventory-based output (like containers):
        // BlockEntity blockEntity = level.getBlockEntity(pos);
        // return blockEntity instanceof Container ? Container.getRedstoneSignalFromContainer((Container)blockEntity) : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return false; // Still not a normal redstone source
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 0; // Prevent strong powering
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean hasPower = level.hasNeighborSignal(pos);
            if (hasPower != state.getValue(POWERED)) {
                boolean isLighted = state.getValue(LIT);
                if (!state.getValue(POWERED) && hasPower) {
                    isLighted = !isLighted;
                    if (state.getValue(LIT)) {
                        level.playSound(null, pos, ModSoundEvents.COPPER_BULB_TOGGLE.get(), SoundSource.BLOCKS, 1.0F, 0.75F);
                    } else {
                        level.playSound(null, pos, ModSoundEvents.COPPER_BULB_TOGGLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                }
                level.setBlock(pos, state.setValue(POWERED, hasPower).setValue(LIT, isLighted), 3);
            }
        }
    }
}
