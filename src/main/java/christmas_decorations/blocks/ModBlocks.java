package christmas_decorations.blocks;

import christmas_decorations.Christmas_decorations;
import christmas_decorations.blocks.custom.GarlandBlock;
import christmas_decorations.blocks.custom.GiftBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

/**
 * Класс для регистрации всех блоков мода.
 * Следует принципу Dependency Inversion - зависит от абстракций (Block), а не от конкретных реализаций.
 */
public class ModBlocks {

    // Пример регистрации блоков-подарков
    public static final GiftBlock SMALL_GIFT = registerBlock("small_gift",
            AbstractBlock.Settings.create().strength(0.5f).sounds(BlockSoundGroup.WOOD).nonOpaque(),GiftBlock::new);

    public static final GiftBlock TALL_GIFT = registerBlock("tall_gift",
            AbstractBlock.Settings.create().strength(0.5f).sounds(BlockSoundGroup.WOOD).nonOpaque(),GiftBlock::new);

    public static final GiftBlock BIG_GIFT = registerBlock("big_gift",
            AbstractBlock.Settings.create().strength(0.5f).sounds(BlockSoundGroup.WOOD).nonOpaque(),GiftBlock::new);

    public static final GarlandBlock GARLANDS_RED = registerBlock("garlands_red",
            AbstractBlock.Settings.create().strength(0.1f).sounds(BlockSoundGroup.WOOD).nonOpaque().luminance(value -> 13),GarlandBlock::new);

    public static final GarlandBlock GARLANDS_BLUE = registerBlock("garlands_blue",
            AbstractBlock.Settings.create().strength(0.1f).sounds(BlockSoundGroup.WOOD).nonOpaque().luminance(value -> 13),GarlandBlock::new);

    public static final GarlandBlock GARLANDS_YELLOW = registerBlock("garlands_yellow",
            AbstractBlock.Settings.create().strength(0.1f).sounds(BlockSoundGroup.WOOD).nonOpaque().luminance(value -> 13),GarlandBlock::new);

    /**
     * Регистрирует блок с автоматическим созданием BlockItem
     * ВАЖНО: settings.registryKey() должен быть вызван ДО создания блока!
     */
    private static <T extends Block> T registerBlock(
            String name,
            AbstractBlock.Settings settings,
            java.util.function.Function<AbstractBlock.Settings, T> blockFactory
    ) {
        Identifier id = Identifier.of(Christmas_decorations.MOD_ID, name);

        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);

        T block = blockFactory.apply(settings.registryKey(blockKey));

        Registry.register(Registries.BLOCK, blockKey, block);

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Registry.register(
                Registries.ITEM,
                itemKey,
                new BlockItem(block, new Item.Settings().registryKey(itemKey))
        );

        return block;
    }

    /**
     * Инициализирует все блоки мода
     */
    public static void register() {
        Christmas_decorations.LOGGER.info("Registering blocks for " + Christmas_decorations.MOD_ID);
    }
}