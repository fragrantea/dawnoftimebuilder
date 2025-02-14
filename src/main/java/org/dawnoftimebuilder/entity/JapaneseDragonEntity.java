package org.dawnoftimebuilder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.DoTBConfig;

import javax.annotation.Nullable;

import java.util.Random;

import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY;

public class JapaneseDragonEntity extends CreatureEntity {

	private static final DataParameter<Float> SIZE = EntityDataManager.defineId(JapaneseDragonEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> ANGLE = EntityDataManager.defineId(JapaneseDragonEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> ANIM_DURATION = EntityDataManager.defineId(JapaneseDragonEntity.class, DataSerializers.FLOAT);
	private float previousTickAge = 0.0F;
	private float animationLoop = 0.0F;

	public JapaneseDragonEntity(World worldIn) {
		super(JAPANESE_DRAGON_ENTITY.get(), worldIn);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, DoTBConfig.JAPANESE_DRAGON_HEALTH.get())
				.add(Attributes.ATTACK_DAMAGE, DoTBConfig.JAPANESE_DRAGON_ATTACK.get());

	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		float size = (float) this.random.nextGaussian();
		if(size < 0) size = -1 / (size - 1);
		else size = (float) (1.0D + Math.min(size * 0.5D, 15.0D));

		this.setDragonSize(size);
		this.xpReward = (int) Math.ceil(100 * size);
		this.setAnimDuration(10.0F);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.6F;
	}

	@Override
	protected boolean isMovementNoisy() {
		return false;
	}

	@Override
	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	@Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState state, BlockPos pos) {
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public boolean removeWhenFarAway(double named) {
		return false;
	}

	@Override
	protected float getVoicePitch() {
		return super.getVoicePitch() * this.getDragonSize();
	}

	@Override
	protected float getSoundVolume() {
		return this.getDragonSize();
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound(){
		return !DoTBConfig.JAPANESE_DRAGON_MUTE.get() && this.random.nextInt(4) == 0 ? SoundEvents.ENDER_DRAGON_AMBIENT : null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENDER_DRAGON_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENDER_DRAGON_DEATH;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SIZE, 1.0F);
		this.getEntityData().define(ANGLE, 0.0F);
		this.getEntityData().define(ANIM_DURATION, 50.0F);
	}

	private void setDragonSize(float size){
		this.getEntityData().set(SIZE, size);
	}

	public float getDragonSize(){
		return this.getEntityData().get(SIZE);
	}

	private float getDragonSpeed(){
		return 0.4F + this.getDragonSize();
	}

	@Override
	public void setYHeadRot(float angle){
		this.getEntityData().set(ANGLE, angle / 2 + this.getHeadTargetAngle() / 2);
	}

	public float getHeadTargetAngle(){
		return this.getEntityData().get(ANGLE);
	}

	/**
	 * @param factor is multiplied with 5 + dragon's size. A value of ten give an average speed of animation
	 */
	private void setAnimDuration(float factor){
		this.getEntityData().set(ANIM_DURATION, factor * (5.0F + this.getDragonSize()));
	}

	private float getAnimDuration(){
		return this.getEntityData().get(ANIM_DURATION);
	}

	public float getAnimationLoop(float tickAge) {
		float f = (tickAge - this.previousTickAge) / this.getAnimDuration();
		this.previousTickAge = tickAge;
		this.animationLoop = (this.animationLoop + f) % 2.0F;
		return this.animationLoop;
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		float size = compound.getFloat("JapaneseDragonSize");
		if(size > 0.0F){
			this.setDragonSize(size);
			this.setAnimDuration(10.0F);
			this.xpReward = (int) Math.ceil(100 * size);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putFloat("JapaneseDragonSize", this.getDragonSize());
	}

	@Override
	protected void registerGoals() {
		//this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.goalSelector.addGoal(3, new FlyUpGoal(this));
		this.goalSelector.addGoal(3, new FlyDownGoal(this));
		this.goalSelector.addGoal(4, new LongFlyGoal(this));
		this.goalSelector.addGoal(5, new WanderFlyGoal(this));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	public static class FlyUpGoal extends Goal {
		protected final JapaneseDragonEntity dragon;

		public FlyUpGoal(JapaneseDragonEntity entityIn) {
			this.dragon = entityIn;
		}

		@Override
		public boolean canUse() {
			if(this.dragon.getNoActionTime() < 100) return false;
			if(this.dragon.blockPosition().getY() > 100.0D) return false;
			if(this.dragon.getRandom().nextInt(200) != 0) return false;
			BlockPos pos = new BlockPos(this.dragon.blockPosition().getX(), this.dragon.blockPosition().getY(), this.dragon.blockPosition().getZ());
			return this.dragon.level.canSeeSky(pos);
		}

		@Override
		public void start(){
			this.dragon.setNoActionTime(0);
			this.dragon.getMoveControl().setWantedPosition(this.dragon.blockPosition().getX(), 100.0D + 30.0D * this.dragon.getRandom().nextDouble(), this.dragon.blockPosition().getY(), this.dragon.getDragonSpeed() * 1.2D);
			this.dragon.setAnimDuration(8.0F);
		}
	}

	static class FlyDownGoal extends Goal {
		private final JapaneseDragonEntity dragon;
		private int goalY = 100;

		FlyDownGoal(JapaneseDragonEntity dragon){
			this.dragon = dragon;
		}

		@Override
		public boolean canUse(){
			if(this.dragon.blockPosition().getY() < 100.0D) return false;
			if(this.dragon.getRandom().nextInt(100) != 0) return false;

			BlockPos pos = new BlockPos(this.dragon.blockPosition().getX(), 90.0D, this.dragon.blockPosition().getZ());
			World world = this.dragon.level;
			if(world.getBlockState(pos).getCollisionShape(world, pos) == VoxelShapes.empty() && !world.getBlockState(pos).getMaterial().isLiquid()) return false;
			int newY = world.getChunk(new BlockPos(this.dragon.blockPosition().getX(), this.dragon.blockPosition().getY(), this.dragon.blockPosition().getZ())).getHighestSectionPosition();
			for(int y = newY + 16; y >= newY; y--){
				pos = new BlockPos(this.dragon.blockPosition().getX(), y, this.dragon.blockPosition().getZ());
				if(world.getBlockState(pos).getCollisionShape(world, pos) != VoxelShapes.empty() || world.getBlockState(pos).getMaterial().isLiquid()){
					break;
				}
			}
			if(newY + 11 >= this.dragon.blockPosition().getY()) return false;
			this.goalY = newY + 1;
			return true;
		}

		@Override
		public void start(){
			this.dragon.setNoActionTime(0);
			this.dragon.getMoveControl().setWantedPosition(this.dragon.blockPosition().getX(), this.goalY + this.dragon.getDragonSize(), this.dragon.blockPosition().getZ(), this.dragon.getDragonSpeed() * 1.2D);
			this.dragon.setAnimDuration(8.0F);
		}
	}

	static class WanderFlyGoal extends Goal {
		private final JapaneseDragonEntity dragon;

		WanderFlyGoal(JapaneseDragonEntity dragon){
			this.dragon = dragon;
		}

		@Override
		public boolean canUse(){
			if(this.dragon.blockPosition().getY() > 100.0D) return false;
			if(this.dragon.getRandom().nextInt(50) != 0) return false;

			MovementController controller = this.dragon.getMoveControl();
			if (!controller.hasWanted()) return true;
			else{
				double distanceX = controller.getWantedX() - this.dragon.blockPosition().getX();
				double distanceY = controller.getWantedY() - this.dragon.blockPosition().getY();
				double distanceZ = controller.getWantedZ() - this.dragon.blockPosition().getZ();
				double diagonalSquare = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
				return diagonalSquare < 1.0D;
			}
		}

		@Override
		public void start(){
			Random random = this.dragon.getRandom();
			double newX = this.dragon.blockPosition().getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double newZ = this.dragon.blockPosition().getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);

			World world = this.dragon.level;
			BlockPos pos;
			this.dragon.setAnimDuration(10.0F);
			for(double newY = this.dragon.blockPosition().getY() - 16.0D; newY <= this.dragon.blockPosition().getY() + 16.0D; newY++){
				pos = new BlockPos(newX, newY, newZ);
				if(world.getBlockState(pos).getCollisionShape(world, pos) == VoxelShapes.empty() && !world.getBlockState(pos).getMaterial().isLiquid()){
					this.dragon.getMoveControl().setWantedPosition(newX, newY + this.dragon.getDragonSize(), newZ, this.dragon.getDragonSpeed());
					return;
				}
			}
			this.dragon.getMoveControl().setWantedPosition(newX, this.dragon.blockPosition().getY(), newZ, this.dragon.getDragonSpeed());
		}
	}

	static class LongFlyGoal extends Goal {
		private final JapaneseDragonEntity dragon;

		LongFlyGoal(JapaneseDragonEntity dragon){
			this.dragon = dragon;
		}

		@Override
		public boolean canUse(){
			if(this.dragon.blockPosition().getY() < 100.0D) return false;
			BlockPos pos = new BlockPos(this.dragon.blockPosition().getX(), this.dragon.blockPosition().getY(), this.dragon.blockPosition().getZ());
			return this.dragon.level.canSeeSky(pos);
		}

		@Override
		public void start(){
			this.dragon.setNoActionTime(0);
			Random random = this.dragon.getRandom();
			double newX = this.dragon.blockPosition().getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 500.0F);
			double newZ = this.dragon.blockPosition().getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 500.0F);

			this.dragon.getMoveControl().setWantedPosition(newX, this.dragon.blockPosition().getY(), newZ, this.dragon.getDragonSpeed() * 2.5F);
			this.dragon.setAnimDuration(5.0F);
		}
	}
	/*
	static class JapaneseDragonMoveHelper extends EntityMoveHelper {
		private final JapaneseDragonEntity parentEntity;

		JapaneseDragonMoveHelper(JapaneseDragonEntity dragon){
			super(dragon);
			this.parentEntity = dragon;
		}

		@Override
		public void onUpdateMoveHelper(){
			if(this.action == EntityMoveHelper.Action.MOVE_TO){

				double distanceX = this.posX - this.parentEntity.posX;
				double distanceY = this.posY - this.parentEntity.posY;
				double distanceZ = this.posZ - this.parentEntity.posZ;
				double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
				if (distance < 1.0D) {
					this.stopMovement();
					return;
				}
				distance = MathHelper.sqrt(distance);

				float rotation = (float)(MathHelper.atan2(distanceZ, distanceX) * (180D / Math.PI)) - 90.0F;
				this.parentEntity.rotationYaw = this.limitAngle(this.parentEntity.rotationYaw, rotation, 90.0F);

				this.parentEntity.motionX = 0.2F * MathHelper.clamp(distanceX / distance, -1.0F, 1.0F) * this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
				this.parentEntity.motionY = 0.2F * MathHelper.clamp(distanceY / distance, -1.0F, 1.0F) * this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
				this.parentEntity.motionZ = 0.2F * MathHelper.clamp(distanceZ / distance, -1.0F, 1.0F) * this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();

				double d = Math.asin(MathHelper.clamp(distanceY/ distance, -1.0D, 1.0D));
				d = d * (1.0F - 1.0F / ((distance + 1.0F) / 2));
				this.parentEntity.setHeadTargetAngle((float) d);
				if (!this.isNotColliding(this.posX, this.posY, this.posZ, distance)) {
					this.stopMovement();
				}
			}
		}

		private void stopMovement(){
			this.action = EntityMoveHelper.Action.WAIT;
			this.parentEntity.setMoveForward(0.0F);
			this.parentEntity.setMoveVertical(0.0F);
			this.parentEntity.setAnimDuration(14.0F);
		}

		private boolean isNotColliding(double x, double y, double z, double distance){
			double d0 = (x - this.parentEntity.posX) / distance;
			double d1 = (y - this.parentEntity.posY) / distance;
			double d2 = (z - this.parentEntity.posZ) / distance;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double)i < distance; ++i){
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()){
					return false;
				}
			}
			return true;
		}
	}
	 */
}
