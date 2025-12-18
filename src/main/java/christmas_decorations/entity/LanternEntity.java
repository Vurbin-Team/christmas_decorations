package christmas_decorations.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LanternEntity extends Entity {
    private static final TrackedData<Float> ROPE_T = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_X = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_Y = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> START_Z = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> END_X = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> END_Y = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> END_Z = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> ROPE_ID = DataTracker.registerData(LanternEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private Vec3d ropeStart = Vec3d.ZERO;
    private Vec3d ropeEnd = Vec3d.ZERO;
    private double ropeT = 0.5;
    private int ropeId;

    public LanternEntity(EntityType<?> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    public LanternEntity(World world) {
        this(ModEntities.LANTERN, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(ROPE_T, 0.5f);
        builder.add(START_X, 0f);
        builder.add(START_Y, 0f);
        builder.add(START_Z, 0f);
        builder.add(END_X, 0f);
        builder.add(END_Y, 0f);
        builder.add(END_Z, 0f);
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
    public void tick() {
        super.tick();
        if (this.getEntityWorld().isClient()) {
            Vec3d base = getRopeStart().lerp(getRopeEnd(), getRopeT());
            double sag = 4 * Math.min(getRopeStart().distanceTo(getRopeEnd()) * 0.15, 2.0) * getRopeT() * (1-getRopeT());
            this.setPosition(base.add(0, -sag-0.25, 0));
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    public void setRopeT(double t) { this.ropeT = t; dataTracker.set(ROPE_T,(float)t); }
    public double getRopeT() { return this.getEntityWorld().isClient()? dataTracker.get(ROPE_T):ropeT; }

    public void setRopeStart(Vec3d start) { this.ropeStart = start; dataTracker.set(START_X,(float)start.x); dataTracker.set(START_Y,(float)start.y); dataTracker.set(START_Z,(float)start.z); }
    public void setRopeEnd(Vec3d end) { this.ropeEnd = end; dataTracker.set(END_X,(float)end.x); dataTracker.set(END_Y,(float)end.y); dataTracker.set(END_Z,(float)end.z); }

    public Vec3d getRopeStart() { return this.getEntityWorld().isClient()? new Vec3d(dataTracker.get(START_X),dataTracker.get(START_Y),dataTracker.get(START_Z)):ropeStart; }
    public Vec3d getRopeEnd() { return this.getEntityWorld().isClient()? new Vec3d(dataTracker.get(END_X),dataTracker.get(END_Y),dataTracker.get(END_Z)):ropeEnd; }

    @Override
    protected void readCustomData(ReadView nbt) {
        ropeT = nbt.getDouble("RopeT",0);
        ropeStart = new Vec3d(nbt.getDouble("StartX",0), nbt.getDouble("StartY",0), nbt.getDouble("StartZ",0));
        ropeEnd = new Vec3d(nbt.getDouble("EndX",0), nbt.getDouble("EndY",0), nbt.getDouble("EndZ",0));
        ropeId = nbt.getInt("RopeId",0);
        setRopeT(ropeT); setRopeStart(ropeStart); setRopeEnd(ropeEnd); setRopeId(ropeId);
    }

    @Override
    protected void writeCustomData(WriteView nbt) {
        nbt.putDouble("RopeT",ropeT);
        nbt.putDouble("StartX",ropeStart.x);
        nbt.putDouble("StartY",ropeStart.y);
        nbt.putDouble("StartZ",ropeStart.z);
        nbt.putDouble("EndX",ropeEnd.x);
        nbt.putDouble("EndY",ropeEnd.y);
        nbt.putDouble("EndZ",ropeEnd.z);
        nbt.putInt("RopeId",ropeId);
    }

    @Override
    public boolean shouldRender(double distance) { return distance < 4096; }
    @Override
    public boolean canHit() { return false; }
}
