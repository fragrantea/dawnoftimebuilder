package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Food;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.PlantType;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;

import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

public class MulberryBlock extends DoubleGrowingBushBlock {

    public MulberryBlock(String seedName, PlantType plantType, int cutAge, int growingAge, Food food) {
        super(seedName, plantType, cutAge, growingAge, food);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if(state.getValue(HALF) == Half.TOP){
            if(random.nextInt(DoTBConfig.SILKMOTH_SPAWN_CHANCE.get()) == 0){
                SILKMOTH_ENTITY.get().spawn(worldIn, null, null, pos, SpawnReason.SPAWNER, false, true);
            }
        }
    }
}
