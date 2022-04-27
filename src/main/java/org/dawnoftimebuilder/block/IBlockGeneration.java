package org.dawnoftimebuilder.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ISeedReader;

import java.util.Random;

public interface IBlockGeneration {

    /**
     * This function is called by the placer during the world generation.
     * It must check whenever this position is suitable for the block.
     * @param world where the block must spawn.
     * @param pos position where to spawn the block.
     * @param state of the block (default state).
     * @param random instance.
     */
    void generateOnPos(Level world, BlockPos pos, BlockState state, Random random);
}
