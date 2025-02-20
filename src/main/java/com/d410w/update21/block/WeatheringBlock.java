package com.d410w.update21.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Optional;

public class WeatheringBlock extends Block implements ChangeOverTimeBlock<WeatherState> {
    public static final EnumProperty<WeatherState> OXIDATION = EnumProperty.create("oxidation", WeatherState.class);
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    public WeatheringBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(OXIDATION, WeatherState.UNAFFECTED)
                .setValue(WAXED, false));
    }

    @Override
    public Optional<BlockState> getNext(BlockState blockState) {
        return Optional.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WAXED, OXIDATION);
    }

    @Override
    public float getChanceModifier() {
        return 1.0F;
    }

    @Override
    public WeatherState getAge(BlockState state) {
        return state.getValue(OXIDATION);
    }


    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isClientSide) {
            if (pRandom.nextInt(25) <= 1) { //about 4% chance
                try { tryTransition(pState, pLevel, pPos); } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    protected void tryTransition(BlockState pState, ServerLevel level, BlockPos pos) {
        Optional<BlockState> optional = this.getNext(pState);
        if (optional.isPresent()) {
            level.setBlock(pos, optional.get(), 2);
        }
    }
}