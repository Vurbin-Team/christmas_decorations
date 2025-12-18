package christmas_decorations.models;

import christmas_decorations.Christmas_decorations;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer LANTERN = new EntityModelLayer(
            Identifier.of(Christmas_decorations.MOD_ID, "lantern"),
            "main"
    );

    public static void register() {
        // Метод для инициализации класса
    }
}
