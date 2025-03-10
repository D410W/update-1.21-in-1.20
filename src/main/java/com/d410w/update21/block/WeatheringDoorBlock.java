package com.d410w.update21.block;

import com.d410w.update21.ModData.DelayedBlockHandler;
import com.d410w.update21.ModData.ModBlockSetTypes;
import com.d410w.update21.Update21;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WeatheringDoorBlock extends DoorBlock implements ChangeOverTimeBlock<WeatherState> {
    private Map<Boolean, Map<WeatherState, BlockState>> hashType;
    private Map<Boolean, Map<WeatherState, BlockState>> weatheringStates;
    private WeatherState weatherState = WeatherState.UNAFFECTED;
    public static final EnumProperty<WeatherState> OXIDATION = EnumProperty.create("oxidation", WeatherState.class);
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    public WeatheringDoorBlock(Properties properties, WeatherState weatherState, boolean waxState, HashMap passedHash) {
        super(properties, ModBlockSetTypes.COPPER_TYPE);
        registerDefaultState(defaultBlockState()
                .setValue(OXIDATION, weatherState)
                .setValue(WAXED, waxState));
        this.weatheringStates = new HashMap<>();
        this.hashType = passedHash;
    }

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<BlockState> getNext(BlockState blockState) {
        WeatherState currentState = blockState.getValue(OXIDATION);
        WeatherState nextState = currentState.next();

        if (nextState != currentState) {
            boolean isWaxed = blockState.getValue(WAXED);

            // Resolve BlockStates if not already resolved:
            resolveBlockStates();

            BlockState nextBlockState = weatheringStates.get(isWaxed).get(nextState);
            return Optional.ofNullable(nextBlockState);
        }

        return Optional.empty();
    }

    @Override
    public float getChanceModifier() {
        return 0;
    }

    private void resolveBlockStates() {
        if (!weatheringStates.isEmpty()) {
            return;
        }

        Map<Boolean, Map<WeatherState, BlockState>> Data = this.hashType;

        weatheringStates.put(false, Data.get(false));
        weatheringStates.put(true, Data.get(true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WAXED, OXIDATION);
    }

    protected float getChanceModifier(BlockState state) {
        // Adjust these values based on your block type
        return state.getValue(OXIDATION) == WeatherState.UNAFFECTED ? 0.05688889F : 0.04266667F;
    }

    @Override
    public WeatherState getAge() { // No BlockState parameter
        return weatherState; // Return the value from the field
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide && !state.getValue(WAXED) && state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            float chance = getOxidationChance(state, level, pos, random);
            if (random.nextFloat() < chance) {
                tryTransition(state, level, pos);
            }
        }
    }

    protected float getOxidationChance(BlockState state, LevelAccessor level, BlockPos pos, RandomSource random) {
        int currentAge = state.getValue(OXIDATION).ordinal();
        int olderNeighbors = 0;
        int totalRelevant = 0;

        // Scan in 4-block Manhattan distance
        for (BlockPos neighborPos : BlockPos.withinManhattan(pos, 4, 4, 4)) {
            int distance = neighborPos.distManhattan(pos);
            if (distance > 4) break;

            if (!neighborPos.equals(pos)) {
                BlockState neighborState = level.getBlockState(neighborPos);
                Optional<Integer> neighborAge = getOxidationNumber(neighborState);

                if (neighborState.hasProperty(WAXED) && neighborState.getValue(WAXED)){
                    continue;
                }

                if (neighborAge.isPresent()) {
                    // Found less oxidized block - abort immediately
                    if (neighborAge.get() < currentAge) {
                        return 0.0f;
                    }

                    // Count relevant neighbors
                    totalRelevant++;
                    if (neighborAge.get() > currentAge) {
                        olderNeighbors++;
                    }
                }
            }
        }

        // Calculate vanilla-style probability
        float ratio = (float)(olderNeighbors + 1) / (float)(totalRelevant + 1);
        return ratio * ratio * getChanceModifier(state);
    }

    private Optional<Integer> getOxidationNumber(BlockState blockState) {
        ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(blockState.getBlock());
        if (blockId == null) return Optional.empty(); // Skip unknown blocks

        // Handle vanilla copper blocks
        if (blockId.getNamespace().equals("minecraft")) {
            return switch (blockId.getPath()) {
                case "copper_block" -> Optional.of(0);
                case "exposed_copper" -> Optional.of(1);
                case "weathered_copper" -> Optional.of(2);
                case "oxidized_copper" -> Optional.of(3);
                default -> Optional.empty();
            };
        }

        // Handle your mod's copper blocks
        if (blockId.getNamespace().equals(Update21.MODID)) {
            if (blockState.hasProperty(OXIDATION)) {
                return Optional.of(blockState.getValue(OXIDATION).ordinal());
            }
            return switch (blockId.getPath()) {
                case "chiseled_copper" -> Optional.of(0);
                case "exposed_chiseled_copper" -> Optional.of(1);
                case "weathered_chiseled_copper" -> Optional.of(2);
                case "oxidized_chiseled_copper" -> Optional.of(3);
                default -> Optional.empty();
            };
        }

        return Optional.empty(); // All other blocks ignored
    }

    private void tryTransition(BlockState state, ServerLevel level, BlockPos pos) {
        WeatherState currentState = state.getValue(OXIDATION);
        WeatherState nextState = currentState.next();

        resolveBlockStates();

        if (nextState != currentState) {
            BlockState nextBlockState = weatheringStates.get(false).get(nextState);
            if (nextBlockState != null) { // Handle the initial state (null BlockState)
                changeBlock(level, pos, state, nextBlockState, Block.UPDATE_ALL); // The '2' flag prevents block update notifications that could cause infinite loops.
            }
        }
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        ItemStack stack = player.getItemInHand(hand);

        resolveBlockStates();

        if (stack.getItem() instanceof AxeItem) {

            BlockState nextBlockState;

            if (blockState.getValue(WAXED)) {
                nextBlockState = weatheringStates.get(false).get(blockState.getValue(OXIDATION));
            } else {
                nextBlockState = weatheringStates.get(false).get(blockState.getValue(OXIDATION).prev());
                if (blockState.getValue(OXIDATION) == WeatherState.UNAFFECTED) {
                    return  InteractionResult.FAIL;
                }
            }

            if (nextBlockState != blockState) {

                if (nextBlockState != null) { // CRUCIAL NULL CHECK
                    changeBlock(level, pos, blockState, nextBlockState, Block.UPDATE_ALL); // Now this is safe
                    // ... rest of your code
                } else {
                    LOGGER.error("nextBlockState is null! isWaxed: {}, nextState: {}", WAXED, nextBlockState); // Log the error!
                    // Handle the null case appropriately, maybe return InteractionResult.FAIL
                    return InteractionResult.FAIL; // Or another appropriate result
                }

                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (blockState.getValue(WAXED)) {
                    level.levelEvent(player, 3004, pos, 0);
                } else {
                    level.levelEvent(player, 3005, pos, 0);
                }

                if (!player.isCreative()) {
                    stack.setDamageValue(stack.getDamageValue()+1);
                }

                return InteractionResult.SUCCESS;

            }

        }

        if (stack.getItem() == Items.HONEYCOMB) {

            boolean isWaxed = blockState.getValue(WAXED);
            BlockState nextBlockState = weatheringStates.get(true).get(blockState.getValue(OXIDATION));

            if (!isWaxed) {

                changeBlock(level, pos, blockState, nextBlockState, Block.UPDATE_ALL);

                level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);

                level.levelEvent(player, 3003, pos, 0);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                return InteractionResult.SUCCESS;

            }

        }

        super.use(blockState, level, pos, player, hand, hitResult);

        return InteractionResult.SUCCESS;
    }

    public void changeBlock(Level level, BlockPos pos, BlockState prevBlockState, BlockState nextBlockState, int num) {
        // Copy all properties except OXIDATION and WAXED to the new state
        ImmutableMap<Property<?>, Comparable<?>> prevValues = prevBlockState.getValues();
        for (Map.Entry<Property<?>, Comparable<?>> entry : prevValues.entrySet()) {
            Property<?> property = entry.getKey();
            Comparable<?> value = entry.getValue();
            if (property != OXIDATION && property != WAXED) {
                nextBlockState = copyProperty(nextBlockState, property, value);
            }
        }
        BlockPos otherPos = prevBlockState.getValue(HALF) == DoubleBlockHalf.LOWER ?
                pos.above() : pos.below();

        if (!level.isClientSide) {
            // Schedule placement of, e.g., STONE after 20 ticks (1 second)
            DelayedBlockHandler.schedule(
                    (ServerLevel) level,
                    pos,
                    Blocks.STONE.defaultBlockState(), // Replace with your desired block
                    20
            );
        }

        level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), num);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), num);

        // Update the current block
        level.setBlock(pos, nextBlockState, num);

        // Update the other half of the door (upper or lower)

        // Create a new state for the other half with the same oxidation/waxed state
        BlockState otherNextState = nextBlockState
                .setValue(HALF, prevBlockState.getValue(HALF) == DoubleBlockHalf.LOWER ?
                        DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER);
        // Copy properties for the other half
        level.setBlock(otherPos, otherNextState, num);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private BlockState copyProperty(
            BlockState nextBlockState,
            Property<?> property,
            Comparable<?> value
    ) {
        if (nextBlockState.hasProperty(property)) {
            Property rawProperty = (Property) property;
            if (rawProperty.getValueClass().isInstance(value)) {
                Comparable castedValue = (Comparable) rawProperty.getValueClass().cast(value);
                nextBlockState = nextBlockState.setValue(rawProperty, castedValue);
            }
        }
        return nextBlockState;
    }
}
