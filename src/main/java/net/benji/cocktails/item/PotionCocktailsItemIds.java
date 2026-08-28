package net.benji.cocktails.item;

import net.benji.cocktails.PotionCocktailsFabric;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class PotionCocktailsItemIds {
    public static final ResourceKey<Item> COCKTAIL_POTION = create("cocktail_potion");

    public static ResourceKey<Item> create(String key) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(PotionCocktailsFabric.MOD_ID, key));
    }
}
