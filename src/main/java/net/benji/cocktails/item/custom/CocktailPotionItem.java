package net.benji.cocktails.item.custom;

import net.benji.cocktails.item.PotionCocktailItems;
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
    private static final List<String> nonEffects = new ArrayList<>(List.of("awkward", "empty", "mundane", "thick", "water"));

    public CocktailPotionItem(Properties properties) {
        super(properties);
    }

    @Override
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

        if (livingEntity instanceof Player && !((Player)livingEntity).getAbilities().instabuild) {
            ItemStack itemStack2 = new ItemStack(PotionCocktailItems.COCKTAIL_GLASS);
            if (!player.getInventory().add(itemStack2)) {
                player.drop(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        PotionUtils.addPotionTooltip(halvePotionTimes(PotionUtils.getMobEffects(itemStack)), list, 1.0F);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        return Component.literal(format(PotionUtils.getPotion(itemStack).getName("")));
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

    public static String format(String input) {
        if (nonEffects.contains(input)) {
            return formatNonEffect(input);
        }
        return formatEffect(input);
    }

    public static String formatEffect(String input) {
        StringBuilder result = new StringBuilder();
        result.append("Cocktail of ");
        boolean nextLetterUppercase = true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '_' || c == '-') {
                result.append(' ');
                nextLetterUppercase = true;
            }
            else if (nextLetterUppercase && Character.isLetter(c)) {
                result.append(Character.toUpperCase(c));
                nextLetterUppercase = false;
            }
            else {
                result.append(c);
                nextLetterUppercase = false;
            }
        }

        return result.toString();
    }

    public static String formatNonEffect(String input) {
        return Character.toUpperCase(input.charAt(0)) + input.substring(1) + " Cocktail";
    }
}
