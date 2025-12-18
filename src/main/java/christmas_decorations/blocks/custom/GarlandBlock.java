package christmas_decorations.blocks.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class GarlandBlock extends GlowLichenBlock {

    public GarlandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) continue; // запрещаем верх/низ
            if (this.getGrower().canGrow(state, world, pos, direction.getOpposite())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());

        // Проходим только по разрешённым граням
        for (Direction direction : ctx.getPlacementDirections()) {
            if (direction == Direction.UP || direction == Direction.DOWN) continue; // запрещаем верх/низ
            BlockState newState = this.withDirection(blockState, ctx.getWorld(), ctx.getBlockPos(), direction);
            if (newState != null) {
                return newState;
            }
        }

        return null; // не удалось разместить
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        // No growth behavior
    }
}