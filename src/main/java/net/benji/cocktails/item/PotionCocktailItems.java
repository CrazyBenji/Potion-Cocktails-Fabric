package net.benji.cocktails.item;

import net.benji.cocktails.PotionCocktailsFabric;
import net.benji.cocktails.item.custom.CocktailPotionItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumables;

import java.util.function.Function;

public class PotionCocktailItems {
    public static final Item COCKTAIL_POTION = registerItem(
            PotionCocktailsItemIds.COCKTAIL_POTION,
            CocktailPotionItem::new,
            new Item.Properties().stacksTo(16)
                    .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                    .component(DataComponents.POTION_DURATION_SCALE, 0.5f)
                    .component(DataComponents.CONSUMABLE, Consumables.defaultDrink().consumeSeconds(0.8f).build())
    );

    public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        return Registry.register(BuiltInRegistries.ITEM, key, itemFactory.apply(properties.setId(key)));
    }

    public static void registerItems() {
        PotionCocktailsFabric.LOGGER.info("Registering items for " + PotionCocktailsFabric.MOD_ID);
    }

}
