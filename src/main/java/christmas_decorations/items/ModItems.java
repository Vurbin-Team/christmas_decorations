package christmas_decorations.items;

import christmas_decorations.Christmas_decorations;
import christmas_decorations.items.RopeLightsItem;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import java.util.function.Function;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public final class ModItems {
    public static final RopeLightsItem ROPE_LIGHTS_TOOL = register(
            "rope_lights",
            RopeLightsItem::new,
            new Item.Settings().maxCount(16)
    );

    private static <T extends Item> T register(String name, Function<Item.Settings, T> itemFactory, Item.Settings settings) {
        // створюємо реєстровий ключ/ідентифікатор
        RegistryKey<Item> ITEM_KEY = RegistryKey.of(
                RegistryKeys.ITEM,
                Identifier.of(Christmas_decorations.MOD_ID, name));

        T item = itemFactory.apply(settings.registryKey(ITEM_KEY));

        // реєструємо
        return Registry.register(Registries.ITEM, ITEM_KEY.getValue(), item);
    }

    public static void register() {}
}
