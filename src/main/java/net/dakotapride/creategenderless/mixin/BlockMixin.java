package net.dakotapride.creategenderless.mixin;

import net.dakotapride.creategenderless.entity.LeafkinBlossom;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "playerDestroy", at = @At("HEAD"))
    private void playerDestory(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        if (state.is(BlockTags.DIRT))
            angerNearbyLeafkinBlossoms(level, pos);
    }

    @Unique
    private void angerNearbyLeafkinBlossoms(Level level, BlockPos pos) {
        // Adjusted/Taken from BeehiveBlock.class
        List<LeafkinBlossom> aggroableLeafkinBlossoms = level.getEntitiesOfClass(LeafkinBlossom.class, (new AABB(pos)).inflate(8.0D, 6.0D, 8.0D));
        if (!aggroableLeafkinBlossoms.isEmpty()) {
            List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, (new AABB(pos)).inflate(8.0D, 6.0D, 8.0D));
            if (nearbyPlayers.isEmpty()) return; //Forge: Prevent Error when no players are around.
            int i = nearbyPlayers.size();

            for (LeafkinBlossom leafkinBlossom : aggroableLeafkinBlossoms) {
                leafkinBlossom.setTarget(nearbyPlayers.get(level.random.nextInt(i)));
            }
        }

    }
}
