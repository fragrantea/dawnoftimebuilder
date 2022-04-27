package org.dawnoftimebuilder.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.CHAIR_ENTITY;

public class ChairEntity extends Entity {

    private BlockPos pos;

    public ChairEntity(Level world){
        super(CHAIR_ENTITY.get(), world);
        this.noPhysics = true;
    }

    private ChairEntity(Level world, BlockPos pos, float pixelsXOffset, float pixelsYOffset, float pixelsZOffset) {
        this(world);
        this.pos = pos;
        //Strangely, the default position (with 0 vertical offset) is 3 pixels above the floor.
        this.setPos(pos.getX() + pixelsXOffset / 16.0D, pos.getY() + (pixelsYOffset - 3.0D) / 16.0D, pos.getZ() + pixelsZOffset / 16.0D);
    }

    public static InteractionResult createEntity(Level world, BlockPos pos, Player player, float pixelsXOffset, float pixelsYOffset, float pixelsZOffset) {
        if(!world.isClientSide()) {
            List<ChairEntity> seats = world.getEntitiesOfClass(ChairEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
            if(seats.isEmpty()) {
                ChairEntity seat = new ChairEntity(world, pos, pixelsXOffset, pixelsYOffset, pixelsZOffset);
                world.addFreshEntity(seat);
                player.startRiding(seat, false);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    public static InteractionResult createEntity(Level world, BlockPos pos, Player player, float pixelsYOffset) {
        return createEntity(world, pos, player, 8.0F, pixelsYOffset, 8.0F);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick(){
        super.tick();
        if(this.pos == null){
            this.pos = this.blockPosition();
        }
        if(!this.level.isClientSide()) {
            if(this.getPassengers().isEmpty() || this.level.isEmptyBlock(this.pos)) {
                this.remove();
                this.level.updateNeighbourForOutputSignal(this.blockPosition(), this.level.getBlockState(this.blockPosition()).getBlock());
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.0D;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return true;
    }
}
