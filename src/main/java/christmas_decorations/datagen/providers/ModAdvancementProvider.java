package christmas_decorations.datagen.providers;

import christmas_decorations.Christmas_decorations;
import christmas_decorations.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementRequirements requirements = AdvancementRequirements.anyOf(
                List.of(new String[]{
                        "has_small_gift",
                        "has_tall_gift",
                        "has_big_gift",
                })
        );

        // Создаем RegistryKey для рецепта
        RegistryKey<Recipe<?>> smallGiftRecipeKey = RegistryKey.of(
                RegistryKeys.RECIPE,
                Identifier.of(Christmas_decorations.MOD_ID, "small_gift")
        );

        // Advancement для разблокировки рецепта Grand Exp Bottle
        AdvancementEntry grandExpBottleRecipe = Advancement.Builder.create()
                .display(
                        ModBlocks.SMALL_GIFT,
                        Text.translatable("advancement.christmas_decorations.small_gift.title"),
                        Text.translatable("advancement.christmas_decorations.small_gift.description"),
                        null, // background
                        AdvancementFrame.TASK,
                        true, // showToast
                        false, // announceToChat
                        true // hidden
                )
                .criterion("has_small_gift", InventoryChangedCriterion.Conditions.items(ModBlocks.SMALL_GIFT))
                .criterion("has_tall_gift", InventoryChangedCriterion.Conditions.items(ModBlocks.TALL_GIFT))
                .criterion("has_big_gift", InventoryChangedCriterion.Conditions.items(ModBlocks.BIG_GIFT))
                .rewards(AdvancementRewards.Builder.recipe(smallGiftRecipeKey)).requirements(
                        requirements
                )
                .build(consumer, "small_gift");
    }
}