package net.benji.cocktails.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CocktailPotionItem extends PotionItem {
    public CocktailPotionItem(Properties properties) {
        super(properties);
    }

    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
        }

        if (!level.isClientSide) {
            for(MobEffectInstance mobEffectInstance : PotionUtils.getMobEffects(itemStack)) {
                if (mobEffectInstance.getEffect().isInstantenous()) {
                    mobEffectInstance.getEffect().applyInstantenousEffect(player, player, livingEntity, mobEffectInstance.getAmplifier(), 1.0F);
                } else {
                    livingEntity.addEffect(halvePotionTime(mobEffectInstance));
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return itemStack;
    }

    public int getUseDuration(ItemStack itemStack) {
        return 16;
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        PotionUtils.addPotionTooltip(halvePotionTimes(PotionUtils.getMobEffects(itemStack)), list, 1.0F);
    }

    public List<MobEffectInstance> halvePotionTimes(List<MobEffectInstance> effects) {
        List<MobEffectInstance> halvedEffects = new ArrayList<>();

        for (MobEffectInstance mobEffectInstance : effects) {
            halvedEffects.add(halvePotionTime(mobEffectInstance));
        }

        return halvedEffects;
    }

    public MobEffectInstance halvePotionTime(MobEffectInstance mobEffectInstance) {
        return new MobEffectInstance(
                mobEffectInstance.getEffect(),
                (int)(mobEffectInstance.getDuration() / 2.0f),
                mobEffectInstance.getAmplifier(),
                mobEffectInstance.isAmbient(),
                mobEffectInstance.isVisible(),
                mobEffectInstance.showIcon());
    }
}
