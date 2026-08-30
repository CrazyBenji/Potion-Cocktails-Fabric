package net.benji.cocktails.item.custom;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class CocktailPotionItem extends PotionItem {
    public CocktailPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NonNull ItemStack itemStack, @NonNull Level level, @NonNull LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
        }

        if (level instanceof ServerLevel) {
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            potionContents.forEachEffect(livingEntity::addEffect, itemStack.getComponents().getOrDefault(DataComponents.POTION_DURATION_SCALE, 0.5f));
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        itemStack.consume(1, livingEntity);
        livingEntity.gameEvent(GameEvent.DRINK);
        if (itemStack.getCount() < 1) {
            return new ItemStack(Items.GLASS_BOTTLE, 1); // Placeholder
        }
        if (player != null) {
            player.addItem(new ItemStack(Items.GLASS_BOTTLE, 1)); // Placeholder
        }
        return itemStack;
    }
}
