package christmas_decorations.models;

import christmas_decorations.renderer.LanternEntityRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;

public class LanternEntityModel extends Model<LanternEntityRenderer.LanternRenderState> {

	private final ModelPart bb_main_outer;  // Внешние кубоиды
	private final ModelPart bb_main_inner;  // Внутренний вывернутый кубоид

	public LanternEntityModel(ModelPart root) {
		super(root, RenderLayer::getEntityCutout);  // Односторонний рендеринг с culling
		this.bb_main_outer = root.getChild("bb_main_outer");
		this.bb_main_inner = root.getChild("bb_main_inner");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		// Внешние кубоиды (нормальные)
		ModelPartData bb_main_outer = modelPartData.addChild("bb_main_outer",
				ModelPartBuilder.create()
						.uv(0, 22).cuboid(-1.5F, -9.0F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 24.0F, 0.0F));

		// Внутренний вывернутый кубоид
		ModelPartData bb_main_inner = modelPartData.addChild("bb_main_inner",
				ModelPartBuilder.create()
						.uv(0, 12).cuboid(3.0F, 0.025F, 3.0F, -6.0F, -6.0F, -6.0F, new Dilation(0.0F))
						.uv(0, 14).cuboid(-2.0F, -5.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)),
				ModelTransform.origin(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(LanternEntityRenderer.LanternRenderState state) {
		// Анимация
	}

	public ModelPart getOuterPart() {
		return bb_main_outer;
	}

	public ModelPart getInnerPart() {
		return bb_main_inner;
	}
}