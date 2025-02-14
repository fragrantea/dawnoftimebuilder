package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.IBlockChain;
import org.dawnoftimebuilder.block.templates.CandleLampBlock;

import java.util.Random;

public class WaxedOakChandelierBlock extends CandleLampBlock implements IBlockChain {

    private static final VoxelShape VS = VoxelShapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(6.0D, 8.0D, 6.0D, 10.0D, 16.0D, 10.0D));

    public WaxedOakChandelierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        this.animateLitCandle(stateIn, worldIn, pos, 0.12D, 0.5D, 0.5D);
        this.animateLitCandle(stateIn, worldIn, pos, 0.5D, 0.5D, 0.12D);
        this.animateLitCandle(stateIn, worldIn, pos, 0.5D, 0.5D, 0.88D);
        this.animateLitCandle(stateIn, worldIn, pos, 0.88D, 0.5D, 0.5D);
    }

    @Override
    public boolean canConnectToChainUnder(BlockState state) {
        return false;
    }
}
