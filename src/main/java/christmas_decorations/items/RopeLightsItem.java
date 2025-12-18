package christmas_decorations.items;

import christmas_decorations.entity.LanternEntity;
import christmas_decorations.entity.RopeSegmentEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RopeLightsItem extends Item {
    private static final int MAX_DISTANCE = 20;
    private static final String FIRST_POS_KEY = "FirstPos";
    private static final String HAS_FIRST_KEY = "HasFirst";

    public RopeLightsItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player == null) return ActionResult.PASS;

        NbtCompound nbt = getOrCreateNbt(stack);
        boolean hasFirst = nbt.contains(HAS_FIRST_KEY) && nbt.getBoolean(HAS_FIRST_KEY).get();

        if (player.isSneaking() && hasFirst) {
            nbt.remove(HAS_FIRST_KEY);
            nbt.remove(FIRST_POS_KEY);
            setNbt(stack, nbt);
            if (!world.isClient()) player.sendMessage(Text.literal("§eТочка сброшена"), true);
            return ActionResult.SUCCESS;
        }

        if (!hasFirst) {
            nbt.putLong(FIRST_POS_KEY, pos.asLong());
            nbt.putBoolean(HAS_FIRST_KEY, true);
            setNbt(stack, nbt);
            player.sendMessage(Text.literal("§aПервая точка установлена!"), true);
            return ActionResult.SUCCESS;
        } else {
            BlockPos firstPos = BlockPos.fromLong(nbt.getLong(FIRST_POS_KEY).get());
            double distance = Math.sqrt(firstPos.getSquaredDistance(pos));
            if (distance > MAX_DISTANCE) {
                player.sendMessage(Text.literal("§cСлишком далеко! Максимум " + MAX_DISTANCE + " блоков"), true);
                nbt.remove(HAS_FIRST_KEY);
                nbt.remove(FIRST_POS_KEY);
                setNbt(stack, nbt);
                return ActionResult.FAIL;
            }
            createRopeWithLanterns(world, firstPos, pos, player);
            player.sendMessage(Text.literal("§aВеревка с фонариками создана!"), true);
            nbt.remove(HAS_FIRST_KEY);
            nbt.remove(FIRST_POS_KEY);
            setNbt(stack, nbt);

            // Уменьшаем стак если игрок не в креативе
            if (!player.isCreative()) {
                stack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }
    }

    private NbtCompound getOrCreateNbt(ItemStack stack) {
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        return nbtComponent.copyNbt();
    }

    private void setNbt(ItemStack stack, NbtCompound nbt) {
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    private void createRopeWithLanterns(World world, BlockPos start, BlockPos end, PlayerEntity player) {
        Vec3d startVec = Vec3d.ofCenter(start);
        Vec3d endVec = Vec3d.ofCenter(end);

        double distance = startVec.distanceTo(endVec);
        int segments = (int) Math.ceil(distance * 2);
        int lanternCount = Math.max(1, (int) (distance / 1)); // фонарики через ~1 блок - distance / 1

        RopeSegmentEntity firstSegment = new RopeSegmentEntity(world);
        int ropeId = firstSegment.getId();

        for (int i = 0; i <= segments; i++) {
            double t = (double) i / segments;
            Vec3d pos = startVec.lerp(endVec, t);
            double sag = 4 * Math.min(distance * 0.15, 2.0) * t * (1 - t);
            pos = pos.add(0, -sag, 0);

            RopeSegmentEntity ropeSegment = new RopeSegmentEntity(world);
            ropeSegment.setPosition(pos);
            ropeSegment.setStart(startVec);
            ropeSegment.setEnd(endVec);
            ropeSegment.setSegmentIndex(i);
            ropeSegment.setTotalSegments(segments);
            ropeSegment.setRopeId(ropeId);

            world.spawnEntity(ropeSegment);
        }

        for (int i = 0; i < lanternCount; i++) {
            double t = (double) (i + 1) / (lanternCount + 1);
            Vec3d pos = startVec.lerp(endVec, t);
            double sag = 4 * Math.min(distance * 0.15, 2.0) * t * (1 - t);
            pos = pos.add(0, -sag - 0.25, 0);

            LanternEntity lantern = new LanternEntity(world);
            lantern.setPosition(pos);
            lantern.setRopeT(t);
            lantern.setRopeStart(startVec);
            lantern.setRopeEnd(endVec);
            lantern.setRopeId(ropeId);

            world.spawnEntity(lantern);
        }
    }
}
