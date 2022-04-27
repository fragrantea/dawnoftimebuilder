package org.dawnoftimebuilder.generation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import org.dawnoftimebuilder.block.IBlockGeneration;

import java.util.Random;

public class DoTBBlockPlacer extends BlockPlacer {

    public DoTBBlockPlacer() {}

    protected BlockPlacerType<?> type() {
        return BlockPlacerType.COLUMN_PLACER;
    }

    public void place(Level world, BlockPos pos, BlockState state, Random random) {
        if(state.getBlock() instanceof IBlockGeneration){
            IBlockGeneration block = ((IBlockGeneration) state.getBlock());
            block.generateOnPos(world, pos, state, random);
        }
    }
}
