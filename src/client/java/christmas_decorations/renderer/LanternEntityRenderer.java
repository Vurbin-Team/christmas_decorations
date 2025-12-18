package christmas_decorations.renderer;

import christmas_decorations.Christmas_decorations;
import christmas_decorations.entity.LanternEntity;
import christmas_decorations.models.LanternEntityModel;
import christmas_decorations.models.ModModelLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class LanternEntityRenderer extends EntityRenderer<LanternEntity, LanternEntityRenderer.LanternRenderState> {

    private static final Identifier TEXTURE = Identifier.of(Christmas_decorations.MOD_ID, "textures/entity/lantern.png");
    private final LanternEntityModel model;

    public LanternEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new LanternEntityModel(ctx.getPart(ModModelLayers.LANTERN));
    }

    @Override
    public LanternRenderState createRenderState() {
        return new LanternRenderState();
    }

    @Override
    public void updateRenderState(LanternEntity entity, LanternRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.age = entity.age + tickDelta;
        state.light = getLight(entity, tickDelta);
    }

    @Override
    public void render(LanternRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();

        // Масштабирование модели
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        matrices.translate(0,-1.5,0);

        // ВАЖНО: Рендерим модель с текстурой
//        queue.submitModel(
//                this.model,                              // модель
//                state,                                    // состояние рендера
//                matrices,                                 // матрицы трансформации
//                RenderLayer.getEntityCutoutNoCull(TEXTURE), // render layer с текстурой
//                state.light,                              // освещение
//                OverlayTexture.DEFAULT_UV,                // overlay (для эффектов повреждения и т.д.)
//                0,                                        // outlineColor (0 = нет обводки)
//                null                                      // crumblingOverlay (для анимации разрушения блока)
//        );

        queue.submitModelPart(
                this.model.getOuterPart(),              // Внешние части
                matrices,
                RenderLayer.getEntityCutout(TEXTURE),
                state.light,                             // Используем обычное освещение
                OverlayTexture.DEFAULT_UV,
                null
        );

        // Рендерим светящуюся внутреннюю часть с максимальным освещением
        queue.submitModelPart(
                this.model.getInnerPart(),              // Внутренняя светящаяся часть
                matrices,
                RenderLayer.getEyes(TEXTURE),           // Специальный RenderLayer для глаз/свечения
                15728880,                                // Максимальное освещение (light = 15, skylight = 15)
                OverlayTexture.DEFAULT_UV,
                null
        );

        matrices.pop();;
    }

    public static class LanternRenderState extends EntityRenderState {
        public float age;
        public int light;
    }
}