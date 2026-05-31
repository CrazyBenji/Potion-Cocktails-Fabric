package net.benji.cocktails;

import net.benji.cocktails.entity.PotionCocktailsEntityTypes;
import net.benji.cocktails.item.PotionCocktailCreativeModeTabs;
import net.benji.cocktails.item.PotionCocktailItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.crafting.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionCocktailsFabric implements ModInitializer {
	public static final String MOD_ID = "cocktails";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PotionCocktailItems.registerItems();
		PotionCocktailCreativeModeTabs.registerCreativeModeTabs();
		PotionCocktailsEntityTypes.registerEntityTypes();

		FabricBrewingRecipeRegistry.registerItemRecipe((PotionItem)Items.POTION, Ingredient.of(Items.AMETHYST_SHARD), (PotionItem)PotionCocktailItems.COCKTAIL_POTION);
	}
}