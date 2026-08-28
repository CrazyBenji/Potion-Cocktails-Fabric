package net.benji.cocktails.data;

import net.benji.cocktails.item.PotionCocktailItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PotionCocktailsRecipeProvider extends FabricRecipeProvider {

    public PotionCocktailsRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                registries.lookup(Registries.POTION).ifPresent(potionRegistryLookup ->
                        potionRegistryLookup.listElements().forEach(holder ->
                        BrewingRecipeBuilder
                        .brewingContainerTransform(Items.POTION, holder, Items.AMETHYST_SHARD, PotionCocktailItems.COCKTAIL_POTION)
                                .save(this.output)));
            }
        };
    }
}
