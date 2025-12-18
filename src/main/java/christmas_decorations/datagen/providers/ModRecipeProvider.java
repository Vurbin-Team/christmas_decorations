package christmas_decorations.datagen.providers;

import christmas_decorations.blocks.ModBlocks;
import christmas_decorations.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.DECORATIONS, ModBlocks.SMALL_GIFT)
                        .pattern("PPP")
                        .pattern("PSP")
                        .pattern("PPP")
                        .input('P', Items.PAPER)
                        .input('S', Items.STRING)
                        .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                        .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.TALL_GIFT)
                        .pattern("XXX")
                        .pattern("XSX")
                        .pattern("XXX")
                        .input('S', ModBlocks.SMALL_GIFT)
                        .input('X', Items.PAPER)
                        .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                        .criterion(hasItem(ModBlocks.SMALL_GIFT), conditionsFromItem(ModBlocks.SMALL_GIFT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.BIG_GIFT)
                        .pattern("XXX")
                        .pattern("XSX")
                        .pattern("XXX")
                        .input('S', ModBlocks.TALL_GIFT)
                        .input('X', Items.PAPER)
                        .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                        .criterion(hasItem(ModBlocks.TALL_GIFT), conditionsFromItem(ModBlocks.TALL_GIFT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.GARLANDS_RED, 6)
                        .pattern("CCC")
                        .pattern("RRR")
                        .pattern("GGG")
                        .input('C', Items.COPPER_NUGGET)
                        .input('R', Items.RED_DYE)
                        .input('G', Items.GLOWSTONE_DUST)
                        .criterion(hasItem(Items.COPPER_NUGGET), conditionsFromItem(Items.COPPER_NUGGET))
                        .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                        .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.GARLANDS_BLUE, 6)
                        .pattern("CCC")
                        .pattern("RRR")
                        .pattern("GGG")
                        .input('C', Items.COPPER_NUGGET)
                        .input('R', Items.BLUE_DYE)
                        .input('G', Items.GLOWSTONE_DUST)
                        .criterion(hasItem(Items.COPPER_NUGGET), conditionsFromItem(Items.COPPER_NUGGET))
                        .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                        .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.GARLANDS_YELLOW, 6)
                        .pattern("CCC")
                        .pattern("RRR")
                        .pattern("GGG")
                        .input('C', Items.COPPER_NUGGET)
                        .input('R', Items.YELLOW_DYE)
                        .input('G', Items.GLOWSTONE_DUST)
                        .criterion(hasItem(Items.COPPER_NUGGET), conditionsFromItem(Items.COPPER_NUGGET))
                        .criterion(hasItem(Items.YELLOW_DYE), conditionsFromItem(Items.YELLOW_DYE))
                        .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.ROPE_LIGHTS_TOOL, 1)
                        .pattern("CCC")
                        .pattern("RRR")
                        .pattern("GGG")
                        .input('C', Items.COPPER_NUGGET)
                        .input('R', Items.GLOWSTONE_DUST)
                        .input('G', Items.GLASS_BOTTLE)
                        .criterion(hasItem(Items.COPPER_NUGGET), conditionsFromItem(Items.COPPER_NUGGET))
                        .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                        .criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}