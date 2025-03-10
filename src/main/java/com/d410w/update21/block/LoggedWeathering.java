package com.d410w.update21.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;

import static com.d410w.update21.ModData.ModSoundEvents.*;

public class LoggedWeathering extends WeatheringBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<WeatherState> OXIDATION = EnumProperty.create("oxidation", WeatherState.class);
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    private static final SoundType GRATE_SOUNDS = new SoundType(
            1.0F, // Volume multiplier
            1.0F, // Pitch multiplier
            COPPER_GRATE_BREAK.get(),  // Break sound
            COPPER_GRATE_STEP.get(),   // Step sound
            COPPER_GRATE_PLACE.get(),  // Place sound
            COPPER_GRATE_STEP.get(),    // Hit sound
            COPPER_GRATE_STEP.get()    // Fall sound
    );

    public LoggedWeathering(Properties properties, WeatherState weatherState, boolean waxState, HashMap passedHash) {
        super(properties.sound(GRATE_SOUNDS).noOcclusion().isRedstoneConductor((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false), weatherState, waxState, passedHash);
        registerDefaultState(stateDefinition.any()
                .setValue(OXIDATION, weatherState)
                .setValue(WAXED, waxState)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder); // Get parent properties
        builder.add(WATERLOGGED); // Add child properties
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.getBlock() == this;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F; // Full brightness
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.WATER_BUCKET) && !state.getValue(WATERLOGGED)) {
            level.setBlock(pos, state.setValue(WATERLOGGED, !state.getValue(WATERLOGGED)), 3);
            level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
            }
            return InteractionResult.SUCCESS;
        } else if (stack.is(Items.BUCKET) && state.getValue(WATERLOGGED)) {
            level.setBlock(pos, state.setValue(WATERLOGGED, !state.getValue(WATERLOGGED)), 3);
            level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
