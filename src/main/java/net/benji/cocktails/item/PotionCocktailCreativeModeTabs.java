package net.benji.cocktails.item;

import net.benji.cocktails.PotionCocktailsFabric;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public class PotionCocktailCreativeModeTabs {
    public static final ResourceKey<CreativeModeTab> POTION_COCKTAILS_TAB = createKey("creative_mode_tab.cocktails");

    @SuppressWarnings("all")
    private static ResourceKey<CreativeModeTab> createKey(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(PotionCocktailsFabric.MOD_ID, name));
    }

    public static void bootstrap(Registry<CreativeModeTab> registry) {
        Registry.register(
                registry,
                POTION_COCKTAILS_TAB,
                CreativeModeTab.builder(CreativeModeTab.Row.TOP, 7)
                        .title(Component.translatable("creative_mode_tab.cocktails"))
                        .icon(PotionCocktailItems.COCKTAIL_POTION::getDefaultInstance)
                        .displayItems(((itemDisplayParameters, output) -> itemDisplayParameters.holders()
                                .lookup(Registries.POTION)
                                .ifPresent(
                                        registryLookup -> {
                                            generatePotionEffectTypes(
                                                    output, registryLookup, PotionCocktailItems.COCKTAIL_POTION, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, itemDisplayParameters.enabledFeatures()
                                            );
                                        }
                                ))).build()
        );
    }

    @SuppressWarnings("all")
    private static void generatePotionEffectTypes(
            CreativeModeTab.Output output, HolderLookup<Potion> holderLookup, Item item, CreativeModeTab.TabVisibility tabVisibility, FeatureFlagSet featureFlagSet
    ) {
        holderLookup.listElements()
                .filter(reference -> ((Potion)reference.value()).isEnabled(featureFlagSet))
                .map(reference -> PotionContents.createItemStack(item, reference))
                .forEach(itemStack -> output.accept(itemStack, tabVisibility));
    }

    public static void registerCreativeModeTabs() {
        PotionCocktailsFabric.LOGGER.info("Registering Item Groups for " + PotionCocktailsFabric.MOD_ID);
        bootstrap(BuiltInRegistries.CREATIVE_MODE_TAB);
    }
}
