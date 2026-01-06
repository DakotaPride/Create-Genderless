package net.dakotapride.creategenderless.item;

import net.dakotapride.creategenderless.entity.Lumarfly;
import net.dakotapride.creategenderless.registry.CreateGenderlessEntityTypes;
import net.dakotapride.creategenderless.registry.CreateGenderlessItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class LumarflyBottleItem extends Item {
    public LumarflyBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            Lumarfly lumarfly = new Lumarfly(CreateGenderlessEntityTypes.LUMARFLY.get(), context.getLevel());

            BlockHitResult blockHitResult = BucketItem.getPlayerPOVHitResult(context.getLevel(), context.getPlayer(), ClipContext.Fluid.SOURCE_ONLY);
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.offset(direction.getNormal());

            lumarfly.setPos(blockPos2.getX() + .5f, blockPos2.getY(), blockPos2.getZ() + .5f);

            if (context.getItemInHand().hasCustomHoverName()) {
                lumarfly.setCustomName(context.getItemInHand().getHoverName());
            }

            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0f, 1.4f);
            context.getLevel().addFreshEntity(lumarfly);

            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
