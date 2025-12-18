package christmas_decorations.entity;

import christmas_decorations.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RopeSegmentEntity extends Entity {
    private static final TrackedData<Float> START_X = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_Y = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_Z = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> END_X = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> END_Y = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> END_Z = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> SEGMENT_INDEX = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TOTAL_SEGMENTS = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ROPE_ID = DataTracker.registerData(RopeSegmentEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private Vec3d start = Vec3d.ZERO;
    private Vec3d end = Vec3d.ZERO;
    private int segmentIndex = 0;
    private int totalSegments = 1;
    private int ropeId;

    public RopeSegmentEntity(EntityType<?> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    public RopeSegmentEntity(World world) {
        this(ModEntities.ROPE_SEGMENT, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(START_X, 0f);
        builder.add(START_Y, 0f);
        builder.add(START_Z, 0f);
        builder.add(END_X, 0f);
        builder.add(END_Y, 0f);
        builder.add(END_Z, 0f);
        builder.add(SEGMENT_INDEX, 0);
        builder.add(TOTAL_SEGMENTS, 1);
        builder.add(ROPE_ID, 0);
    }

    public void setRopeId(int id) {
        this.ropeId = id;
        this.dataTracker.set(ROPE_ID, id);
    }

    public int getRopeId() {
        return this.getEntityWorld().isClient() ? this.dataTracker.get(ROPE_ID) : this.ropeId;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (!this.getEntityWorld().isClient()) {

            ItemStack stack = new ItemStack(ModItems.ROPE_LIGHTS_TOOL, 1); // Замените на ваш предмет
            this.dropStack((ServerWorld) this.getEntityWorld(), stack);

            // удаляем все сегменты и все фонарики с этим ropeId
            this.getEntityWorld().getEntitiesByType(ModEntities.ROPE_SEGMENT, this.getBoundingBox().expand(50),
                            e -> e.getRopeId() == this.getRopeId())
                    .forEach(e -> e.remove(RemovalReason.KILLED));

            this.getEntityWorld().getEntitiesByType(ModEntities.LANTERN, this.getBoundingBox().expand(50),
                            e -> e.getRopeId() == this.getRopeId())
                    .forEach(e -> e.remove(RemovalReason.KILLED));
        }
        return true;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    public void setStart(Vec3d start) {
        this.start = start;
        this.dataTracker.set(START_X, (float) start.x);
        this.dataTracker.set(START_Y, (float) start.y);
        this.dataTracker.set(START_Z, (float) start.z);
    }

    public void setEnd(Vec3d end) {
        this.end = end;
        this.dataTracker.set(END_X, (float) end.x);
        this.dataTracker.set(END_Y, (float) end.y);
        this.dataTracker.set(END_Z, (float) end.z);
    }

    public Vec3d getStart() {
        if (this.getEntityWorld().isClient()) {
            return new Vec3d(
                    this.dataTracker.get(START_X),
                    this.dataTracker.get(START_Y),
                    this.dataTracker.get(START_Z)
            );
        }
        return start;
    }

    public Vec3d getEnd() {
        if (this.getEntityWorld().isClient()) {
            return new Vec3d(
                    this.dataTracker.get(END_X),
                    this.dataTracker.get(END_Y),
                    this.dataTracker.get(END_Z)
            );
        }
        return end;
    }

    public void setSegmentIndex(int index) {
        this.segmentIndex = index;
        this.dataTracker.set(SEGMENT_INDEX, index);
    }

    public void setTotalSegments(int total) {
        this.totalSegments = total;
        this.dataTracker.set(TOTAL_SEGMENTS, total);
    }

    public int getSegmentIndex() {
        return this.getEntityWorld().isClient() ? this.dataTracker.get(SEGMENT_INDEX) : this.segmentIndex;
    }

    public int getTotalSegments() {
        return this.getEntityWorld().isClient() ? this.dataTracker.get(TOTAL_SEGMENTS) : this.totalSegments;
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 4096.0;
    }

    @Override
    protected void readCustomData(ReadView view) {
        this.start = new Vec3d(view.getDouble("StartX", 0), view.getDouble("StartY", 0), view.getDouble("StartZ", 0));
        this.end = new Vec3d(view.getDouble("EndX", 0), view.getDouble("EndY", 0), view.getDouble("EndZ", 0));
        this.segmentIndex = view.getInt("SegmentIndex", 0);
        this.totalSegments = view.getInt("TotalSegments", 0);
        this.ropeId = view.getInt("RopeId", 0);

        setStart(start);
        setEnd(end);
        setSegmentIndex(segmentIndex);
        setTotalSegments(totalSegments);
        setRopeId(ropeId);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        view.putDouble("StartX", start.x);
        view.putDouble("StartY", start.y);
        view.putDouble("StartZ", start.z);
        view.putDouble("EndX", end.x);
        view.putDouble("EndY", end.y);
        view.putDouble("EndZ", end.z);
        view.putInt("SegmentIndex", segmentIndex);
        view.putInt("TotalSegments", totalSegments);
        view.putInt("RopeId", ropeId);
    }
}
