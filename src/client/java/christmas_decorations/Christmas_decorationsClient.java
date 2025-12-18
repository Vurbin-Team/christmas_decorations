package christmas_decorations;

import christmas_decorations.blocks.ModBlocks;
import christmas_decorations.entity.ModEntities;
import christmas_decorations.models.LanternEntityModel;
import christmas_decorations.models.ModModelLayers;
import christmas_decorations.renderer.LanternEntityRenderer;
import christmas_decorations.renderer.RopeSegmentEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.BlockRenderLayer;

public class Christmas_decorationsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.putBlock(
				ModBlocks.GARLANDS_RED,
				BlockRenderLayer.CUTOUT
		);
		BlockRenderLayerMap.putBlock(
				ModBlocks.GARLANDS_BLUE,
				BlockRenderLayer.CUTOUT
		);
		BlockRenderLayerMap.putBlock(
				ModBlocks.GARLANDS_YELLOW,
				BlockRenderLayer.CUTOUT
		);

		EntityRendererRegistry.register(ModEntities.ROPE_SEGMENT, RopeSegmentEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.LANTERN, LanternEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.LANTERN, LanternEntityModel::getTexturedModelData);

		ModModelLayers.register();
	}
}