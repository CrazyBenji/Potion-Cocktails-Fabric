package net.benji.cocktails.item;

import net.benji.cocktails.PotionCocktailsFabric;
import net.benji.cocktails.item.custom.CocktailGlassItem;
import net.benji.cocktails.item.custom.CocktailPotionItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class PotionCocktailItems {
    public static final Item COCKTAIL_POTION = registerItem(
            "cocktail_potion",
            CocktailPotionItem::new,
            new Item.Properties().stacksTo(16)
    );

    public static final Item COCKTAIL_GLASS = registerItem(
            "cocktail_glass",
            CocktailGlassItem::new,
            new Item.Properties().stacksTo(16)
    );

    public static Item registerItem(String name, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        Item item = itemFactory.apply(properties);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(PotionCocktailsFabric.MOD_ID, name), item);

        return item;
    }

    public static void registerItems() {
        PotionCocktailsFabric.LOGGER.info("Registering items for " + PotionCocktailsFabric.MOD_ID);
    }

}
