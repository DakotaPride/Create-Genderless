package net.dakotapride.creategenderless.entity;

import net.dakotapride.creategenderless.registry.CreateGenderlessAdvancementCriteria;
import net.dakotapride.creategenderless.registry.CreateGenderlessEntityTypes;
import net.dakotapride.creategenderless.registry.CreateGenderlessItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Lumarfly extends Animal implements FlyingAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public Lumarfly(EntityType<? extends Lumarfly> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new LumarflyWanderAroundGoal());
        this.goalSelector.addGoal(1, new FloatGoal(this));
    }

    class LumarflyWanderAroundGoal extends Goal {
        LumarflyWanderAroundGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return Lumarfly.this.navigation.isDone() && Lumarfly.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return Lumarfly.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3d = this.getRandomLocation();
            if (vec3d != null) {
                Lumarfly.this.navigation.moveTo(Lumarfly.this.navigation.createPath(BlockPos.containing(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vec3d2 = Lumarfly.this.getViewVector(0.35F);

            Vec3 vec3d3 = HoverRandomPos.getPos(Lumarfly.this, 8, 7, vec3d2.x, vec3d2.z, (float) (Math.PI / 2), 3, 1);
            return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(Lumarfly.this, 8, 4, -2, vec3d2.x, vec3d2.z, (float) (Math.PI / 2));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == Items.GLASS_BOTTLE && !this.isBaby()) {
            ItemStack itemStack = new ItemStack(CreateGenderlessItems.LUMARFLY_BOTTLE);
            if (this.hasCustomName()) {
                itemStack.setHoverName(this.getName());
            }

            if (!player.getAbilities().instabuild) {
                if (stack.getCount() > 1) {
                    stack.shrink(1);
                    if (!player.getInventory().add(itemStack)) {
                        player.drop(itemStack, true);
                    }
                } else {
                    player.setItemInHand(hand, itemStack);
                }
            } else {
                if (!player.getInventory().add(itemStack)) {
                    player.drop(itemStack, true);
                }
            }

            this.level().playSound(player, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.discard();

            if (!player.level().isClientSide)
                CreateGenderlessAdvancementCriteria.LUMARFLY_CAPTURE.trigger((ServerPlayer) player);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected void checkFallDamage(double bool0, boolean bool1, BlockState state, BlockPos pos) {}

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return CreateGenderlessEntityTypes.LUMARFLY.get().create(level);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }
}
