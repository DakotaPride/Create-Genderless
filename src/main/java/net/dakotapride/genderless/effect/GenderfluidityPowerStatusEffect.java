package net.dakotapride.genderless.effect;

import dev.mayaqq.estrogen.registry.items.GenderChangePotionItem;
import net.dakotapride.genderless.init.GenderlessKeybindings;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.dakotapride.genderless.CreateGenderlessMod.isEstrogenLoaded;

public class GenderfluidityPowerStatusEffect extends MobEffect {
    public GenderfluidityPowerStatusEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB9B7ED);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        Level level = entity.level();

        if (isEstrogenLoaded() && !level.isClientSide() && GenderlessKeybindings.ACTIVATE_GENDER_SWITCH.getKeybind().consumeClick()) {
            GenderChangePotionItem.changeGender(level, entity);
            if (entity instanceof ServerPlayer player)
                player.displayClientMessage(Component.translatable("text.genderless.genderfluidity.gender_switch"), true);
            // particle require to be clientside, the method above require no clientside, conflict with each other for some reason with the inclusion of a keybind
//            if (level.isClientSide()) {
//                Vec3 playerPos = entity.position();
//                double heightIncrement = entity.getBoundingBox().getYsize() / (double)50.0F;
//
//                for(int i = 0; i < 50; ++i) {
//                    double angle = (double)i * Math.PI / (double)16.0F;
//                    double x = playerPos.x + (double)0.5F * Math.cos(angle);
//                    double y = playerPos.y + (double)i * heightIncrement;
//                    double z = playerPos.z + (double)0.5F * Math.sin(angle);
//                    y += (double)level.random.nextFloat() * 0.1 * (double)(level.random.nextBoolean() ? 1 : -1);
//                    level.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.3F, 0.7F), 1.0F), x, y, z, 0.0F, 0.0F, 0.0F);
//                    x = playerPos.x + (double)0.5F * Math.cos(angle + Math.PI);
//                    z = playerPos.z + (double)0.5F * Math.sin(angle + Math.PI);
//                    y += (double)level.random.nextFloat() * 0.1 * (double)(level.random.nextBoolean() ? 1 : -1);
//                    level.addParticle(new DustParticleOptions(new Vector3f(0.15F, 0.2F, 0.81F), 1.0F), x, y, z, 0.0F, 0.0F, 0.0F);
//                }
//            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
