package com.d410w.update21.block;

import com.d410w.update21.BlockDataManager;
import com.d410w.update21.Update21;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeatheringBlock extends Block implements ChangeOverTimeBlock<WeatherState> {// Store RegistryObjects
    private Map<Boolean, Map<WeatherState, BlockState>> hashType;
    private Map<Boolean, Map<WeatherState, BlockState>> weatheringStates;
    private WeatherState weatherState = WeatherState.UNAFFECTED;
    public static final EnumProperty<WeatherState> OXIDATION = EnumProperty.create("oxidation", WeatherState.class);
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    public WeatheringBlock(Properties properties, WeatherState weatherState, boolean waxState, HashMap passedHash) {
        super(properties);
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
        builder.add(WAXED, OXIDATION);
    }

    @Override
    public float getChanceModifier() {
        return 1.0F;
    }

    @Override
    public WeatherState getAge() { // No BlockState parameter
        return weatherState; // Return the value from the field
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            if (!state.getValue(WAXED)) { // Only oxidize if not waxed
                int chance = getOxidationChance(state, level, pos, random);
                if (random.nextInt(1125) < chance) {
                    tryTransition(state, level, pos);
                }
            }
        }
    }

    protected int getOxidationChance(BlockState state, LevelAccessor level, BlockPos pos, RandomSource random) {
        final int BASE_NUMERATOR = 64;
        final int BASE_DENOMINATOR = 1125;

        // Count nearby copper blocks (within a taxicab distance of 4) that are not waxed.
        int totalNearby = 0;
        int countHigher = 0;
        // Loop over positions in a 9x9x9 cube centered on our block
        for (BlockPos neighbor : BlockPos.betweenClosed(pos.offset(-4, -4, -4), pos.offset(4, 4, 4))) {
            // Skip self
            if (neighbor.equals(pos)) continue;
            BlockState neighborState = level.getBlockState(neighbor);
            // Check that the neighbor is an instance of a copper block and is not waxed.
            // (Replace 'this::isCopperBlock' with whatever predicate you use.)
            Optional<Integer> neighborOxidation = getOxidationNumber(neighborState);
            Optional<Integer> currentOxidation = getOxidationNumber(state);

            if (neighborOxidation.isPresent() && currentOxidation.isPresent()) {
                totalNearby++;
                if (neighborOxidation.get() > currentOxidation.get()) {
                    countHigher++;
                }
            }
        }

        // Compute the modifier c = (b + 1)/(a + 1)
        float c = (countHigher + 1f) / (totalNearby + 1f);
        // m is 1.0 if unoxidized (stage 0) and 0.75 otherwise
        float m = (state.getValue(OXIDATION) == WeatherState.UNAFFECTED ? 1.0f : 0.75f);
        // Final chance (as a fraction of BASE_NUMERATOR/BASE_DENOMINATOR)
        float chanceFraction = m * c * c;

        // Multiply the base numerator by chanceFraction to get an effective numerator.
        return (int) (BASE_NUMERATOR * chanceFraction);
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
                level.setBlock(pos, nextBlockState, 2); // The '2' flag prevents block update notifications that could cause infinite loops.
            } else {
                level.setBlock(pos, state.setValue(OXIDATION, nextState), 2); // Update the state if no block change is needed
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
            }

            if (nextBlockState != blockState) {

                if (nextBlockState != null) { // CRUCIAL NULL CHECK
                    level.setBlock(pos, nextBlockState, 11); // Now this is safe
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

                level.setBlock(pos, nextBlockState, 11);

                level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);

                level.levelEvent(player, 3003, pos, 0);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                return InteractionResult.SUCCESS;

            }

        }

        return InteractionResult.PASS;

    }
}