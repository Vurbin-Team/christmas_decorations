package christmas_decorations.group;

import christmas_decorations.Christmas_decorations;
import christmas_decorations.blocks.ModBlocks;
import christmas_decorations.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModGroup {

    public static final RegistryKey<ItemGroup> CHRISTMAS_GROUP = RegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            Identifier.of(Christmas_decorations.MOD_ID, "christmas_decorations")
    );

    public static final ItemGroup CHRISTMAS_DECORATIONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.SMALL_GIFT))
            .displayName(Text.translatable("itemGroup.christmas_decorations.christmas_decorations"))
            .entries((context, entries) -> {
                // Добавляем все блоки-подарки
                entries.add(ModBlocks.SMALL_GIFT);
                entries.add(ModBlocks.TALL_GIFT);
                entries.add(ModBlocks.BIG_GIFT);
                entries.add(ModBlocks.GARLANDS_RED);
                entries.add(ModBlocks.GARLANDS_BLUE);
                entries.add(ModBlocks.GARLANDS_YELLOW);
                entries.add(ModItems.ROPE_LIGHTS_TOOL);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, CHRISTMAS_GROUP, CHRISTMAS_DECORATIONS);
        Christmas_decorations.LOGGER.info("Registering Item Groups for " + Christmas_decorations.MOD_ID);
    }
}