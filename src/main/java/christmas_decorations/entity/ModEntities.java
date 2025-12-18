package christmas_decorations.entity;

import christmas_decorations.Christmas_decorations;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModEntities {

    public static final EntityType<RopeSegmentEntity> ROPE_SEGMENT =
            register("rope_segment",
                    FabricEntityTypeBuilder.<RopeSegmentEntity>create(
                                    SpawnGroup.MISC,
                                    RopeSegmentEntity::new
                            )
                            .dimensions(EntityDimensions.fixed(0.125f, 0.125f))
                            .trackRangeBlocks(64)
                            .trackedUpdateRate(10)
            );

    public static final EntityType<LanternEntity> LANTERN =
            register("lantern",
                    FabricEntityTypeBuilder.<LanternEntity>create(
                                    SpawnGroup.MISC,
                                    LanternEntity::new
                            )
                            .dimensions(EntityDimensions.fixed(0.375f, 0.5f))
                            .trackRangeBlocks(64)
                            .trackedUpdateRate(10)
            );

    private static <T extends net.minecraft.entity.Entity> EntityType<T> register(
            String name,
            FabricEntityTypeBuilder<T> builder
    ) {
        Identifier id = Identifier.of(Christmas_decorations.MOD_ID, name);

        RegistryKey<EntityType<?>> key =
                RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);

        EntityType<T> type = builder.build(key);

        return Registry.register(Registries.ENTITY_TYPE, id, type);
    }

    public static void register() {}
}
