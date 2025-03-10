package com.d410w.update21.ModData;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DelayedBlockHandler {
    // Stores scheduled block placements
    private static final List<ScheduledBlockPlacement> SCHEDULED_PLACEMENTS = new ArrayList<>();

    // Represents a delayed block placement task
    private static class ScheduledBlockPlacement {
        final ServerLevel level;
        final BlockPos pos;
        final BlockState blockToPlace;
        int ticksRemaining;

        ScheduledBlockPlacement(ServerLevel level, BlockPos pos, BlockState blockToPlace, int delayTicks) {
            this.level = level;
            this.pos = pos;
            this.blockToPlace = blockToPlace;
            this.ticksRemaining = delayTicks;
        }
    }

    // Called every server tick to update scheduled tasks
    public static void tick() {
        Iterator<ScheduledBlockPlacement> iterator = SCHEDULED_PLACEMENTS.iterator();
        while (iterator.hasNext()) {
            ScheduledBlockPlacement task = iterator.next();
            task.ticksRemaining--;

            if (task.ticksRemaining <= 0) {
                // Place the block only if the position is still air (optional)
                if (task.level.getBlockState(task.pos).isAir()) {
                    task.level.setBlock(task.pos, task.blockToPlace, 3);
                }
                iterator.remove();
            }
        }
    }

    // Schedule a new block placement
    public static void schedule(ServerLevel level, BlockPos pos, BlockState blockToPlace, int delayTicks) {
        SCHEDULED_PLACEMENTS.add(new ScheduledBlockPlacement(level, pos, blockToPlace, delayTicks));
    }
}
