package net.benji.cocktails.datagen;

import net.benji.cocktails.item.PotionCocktailItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PotionCocktailsLanguageProvider extends FabricLanguageProvider {
    private final List<String> addedEffects = new ArrayList<>();
    private final CompletableFuture<HolderLookup.Provider> registries;
    private static final List<String> nonEffects = new ArrayList<>(List.of("awkward", "empty", "mundane", "thick", "water"));

    public PotionCocktailsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(dataOutput);
        this.registries = registries;
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        HolderLookup.Provider lookup = registries.join();

        var potionRegistry = lookup.lookupOrThrow(Registries.POTION);

        for (Holder.Reference<Potion> holder : potionRegistry.listElements().toList()) {
            var id = holder.value().getName("");
            var stack = PotionUtils.setPotion(new ItemStack(PotionCocktailItems.COCKTAIL_POTION), holder.value());

            if (!addedEffects.contains(id)) {
                translationBuilder.add(stack.getItem().getDescriptionId(stack), format(id));
                addedEffects.add(id);
            }
        }
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
