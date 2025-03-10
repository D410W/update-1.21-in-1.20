package com.d410w.update21.ModData;

import com.d410w.update21.Update21;
import com.d410w.update21.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final List<String> OXID_STAGES = Arrays.asList(
            "unaffected", "exposed", "weathered", "oxidized"
    );

    public ModBlockStateProvider(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, Update21.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.BLOCKS.getEntries().forEach(regObj -> {
            Block block = regObj.get();
            ResourceLocation blockId = regObj.getId();
            String path = blockId.getPath();

            // Check if block ID matches "<oxid_stage>_<material>_door"
            if (block instanceof DoorBlock && isOxidDoorBlock(path)) {
                registerOxidDoor(block, blockId);
            }
        });
    }

    private boolean isOxidDoorBlock(String path) {
        String[] parts = path.split("_");
        // Ensure format is "<oxid_stage>_<material>_door"
        return parts.length >= 3
                && OXID_STAGES.contains(parts[0])
                && parts[parts.length - 1].equals("door");
    }

    private void registerOxidDoor(Block doorBlock, ResourceLocation blockId) {
        String[] parts = blockId.getPath().split("_");
        String oxidStage = parts[0]; // "weathered"
        String material = parts[1];   // "copper"

        // Build texture paths
        String textureBase = oxidStage + "_" + material + "_door";
        ResourceLocation bottomTex = modLoc("block/" + textureBase + "_bottom");
        ResourceLocation topTex = modLoc("block/" + textureBase + "_top");

        // Generate models
        ModelFile bottomModel = createDoorModel(textureBase + "_bottom", bottomTex, "door_bottom");
        ModelFile topModel = createDoorModel(textureBase + "_top", topTex, "door_top");

        // Assign blockstate variants
        getVariantBuilder(doorBlock).forAllStates(state -> {
            boolean isLower = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
            int yRot = (int) state.getValue(DoorBlock.FACING).toYRot();
            return ConfiguredModel.builder()
                    .modelFile(isLower ? bottomModel : topModel)
                    .rotationY(yRot)
                    .build();
        });
    }

    // Helper to generate door blockstates
    private void doorBlock(Block block, ModelFile bottom, ModelFile top) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? bottom : top)
                        .rotationY((int) state.getValue(DoorBlock.FACING).toYRot())
                        .build()
                );
    }

    private ModelFile createDoorModel(String name, ResourceLocation texture, String parentType) {
        return models().getBuilder(name)
                .parent(models().getExistingFile(mcLoc("block/" + parentType)))
                .texture("bottom", texture)
                .texture("top", texture);
    }
}