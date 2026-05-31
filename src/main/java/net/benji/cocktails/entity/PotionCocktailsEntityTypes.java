package net.benji.cocktails.entity;

import net.benji.cocktails.PotionCocktailsFabric;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class PotionCocktailsEntityTypes {

    public static final EntityType<CocktailGlass> COCKTAIL_GLASS = registerEntityType(
            "cocktail_glass", EntityType.Builder.<CocktailGlass>of(CocktailGlass::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    );

    private static <T extends Entity> EntityType<T> registerEntityType(String id, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(PotionCocktailsFabric.MOD_ID, id), builder.build(id));
    }

    public static void registerEntityTypes() {
        PotionCocktailsFabric.LOGGER.info("Registering Entity Types");
    }
}
