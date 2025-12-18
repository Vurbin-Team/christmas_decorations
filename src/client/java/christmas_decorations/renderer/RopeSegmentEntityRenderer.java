package christmas_decorations.renderer;

import christmas_decorations.Christmas_decorations;
import christmas_decorations.entity.RopeSegmentEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RopeSegmentEntityRenderer extends EntityRenderer<RopeSegmentEntity, RopeSegmentEntityRenderer.RopeRenderState> {

    private static final Identifier TEXTURE = Identifier.of(Christmas_decorations.MOD_ID, "textures/entity/rope.png");
    private static final RenderLayer RENDER_LAYER = RenderLayer.getEntityTranslucent(TEXTURE);

    public RopeSegmentEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public RopeRenderState createRenderState() {
        return new RopeRenderState();
    }

    @Override
    public void updateRenderState(RopeSegmentEntity entity, RopeRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.start = entity.getStart();
        state.end = entity.getEnd();
        state.entityPos = entity.getEntityPos();
        state.segmentIndex = entity.getSegmentIndex();
        state.totalSegments = entity.getTotalSegments();
        state.light = getLight(entity, tickDelta);
    }

    @Override
    public void render(RopeRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();

        Vec3d start = state.start;
        Vec3d end = state.end;
        Vec3d entityPos = state.entityPos;
        int segmentIndex = state.segmentIndex;
        int totalSegments = state.totalSegments;

        if (segmentIndex < totalSegments) {
            double t = (double) segmentIndex / totalSegments;
            double nextT = (double) (segmentIndex + 1) / totalSegments;

            Vec3d currentPos = start.lerp(end, t);
            Vec3d nextPos = start.lerp(end, nextT);

            // Добавляем провисание
            double distance = start.distanceTo(end);
            double currentSag = calculateSag(t, distance);
            double nextSag = calculateSag(nextT, distance);

            currentPos = currentPos.add(0, -currentSag, 0);
            nextPos = nextPos.add(0, -nextSag, 0);

            Vec3d relCurrent = currentPos.subtract(entityPos);
            Vec3d relNext = nextPos.subtract(entityPos);

            VertexConsumerProvider.Immediate vertexConsumers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            VertexConsumer buffer = vertexConsumers.getBuffer(RENDER_LAYER);
            MatrixStack.Entry entry = matrices.peek();

            float width = 0.03f; // Толщина веревки
            drawRopeLine(buffer, entry, relCurrent, relNext, width, state.light);
        }

        matrices.pop();
    }

    private void drawRopeLine(VertexConsumer buffer, MatrixStack.Entry entry,
                              Vec3d from, Vec3d to, float width, int light) {
        Vec3d direction = to.subtract(from).normalize();
        Vec3d perpendicular = new Vec3d(-direction.z, 0, direction.x).normalize();

        if (perpendicular.lengthSquared() < 0.01) {
            perpendicular = new Vec3d(1, 0, 0);
        }

        Vec3d offset = perpendicular.multiply(width);

        Vec3d p1 = from.add(offset);
        Vec3d p2 = from.subtract(offset);
        Vec3d p3 = to.subtract(offset);
        Vec3d p4 = to.add(offset);

        float nx = 0, ny = 1, nz = 0;

        addVertex(buffer, entry, p1, 0, 0, nx, ny, nz, light);
        addVertex(buffer, entry, p2, 1, 0, nx, ny, nz, light);
        addVertex(buffer, entry, p3, 1, 1, nx, ny, nz, light);
        addVertex(buffer, entry, p4, 0, 1, nx, ny, nz, light);
    }

    private void addVertex(VertexConsumer buffer, MatrixStack.Entry entry,
                           Vec3d pos, float u, float v, float nx, float ny, float nz, int light) {
        buffer.vertex(entry.getPositionMatrix(), (float) pos.x, (float) pos.y, (float) pos.z)
                .color(255, 255, 255, 255) // Белый цвет, чтобы текстура отображалась корректно
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, nx, ny, nz);
    }

    private double calculateSag(double t, double distance) {
        double maxSag = Math.min(distance * 0.15, 2.0);
        return 4 * maxSag * t * (1 - t);
    }

    public static class RopeRenderState extends EntityRenderState {
        public Vec3d start;
        public Vec3d end;
        public Vec3d entityPos;
        public int segmentIndex;
        public int totalSegments;
        public int light;
    }
}
