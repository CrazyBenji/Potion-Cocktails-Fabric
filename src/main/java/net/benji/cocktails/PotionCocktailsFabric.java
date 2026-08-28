package net.benji.cocktails;

import net.benji.cocktails.item.PotionCocktailCreativeModeTabs;
import net.benji.cocktails.item.PotionCocktailItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionCocktailsFabric implements ModInitializer {
	public static final String MOD_ID = "cocktails";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PotionCocktailItems.registerItems();
		PotionCocktailCreativeModeTabs.registerCreativeModeTabs();
	}
}