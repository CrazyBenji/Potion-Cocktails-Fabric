package net.benji.cocktails;

import net.benji.cocktails.item.PotionCocktailItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.alchemy.PotionUtils;

@SuppressWarnings("unused")
public class PotionCocktailsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(
                (itemStack, i) -> i > 0 ? -1 :
                        PotionUtils.getColor(itemStack), PotionCocktailItems.COCKTAIL_POTION
        );
    }
}
