package net.benji.cocktails;

import net.benji.cocktails.item.PotionCocktailItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.alchemy.PotionContents;

@SuppressWarnings("unused")
public class PotionCocktailsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((itemStack, i) -> i > 0 ? -1 : FastColor.ARGB32.opaque(itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor()), PotionCocktailItems.COCKTAIL_POTION);
    }
}
