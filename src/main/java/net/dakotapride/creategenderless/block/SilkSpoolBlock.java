package net.dakotapride.creategenderless.block;

import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.dakotapride.creategenderless.registry.CreateGenderlessBlockstateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class SilkSpoolBlock extends DirectionalBlock implements IHaveHoveringInformation, IWrenchable {
    VoxelShape SHAPE_X = Shapes.box(0, 0.0625, 0.0625, 1, 0.9375, 0.9375);
    VoxelShape SHAPE_Z = Shapes.box(0.0625, 0.0625, 0, 0.9375, 0.9375, 1);
    VoxelShape SHAPE_Y = Shapes.box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375);

    public static final EnumProperty<SpoolConnection> SPOOL_CONNECTION = CreateGenderlessBlockstateProperties.SPOOL_CONNECTION;

    public SilkSpoolBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(SPOOL_CONNECTION, SpoolConnection.NONE));
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (state.getValue(SPOOL_CONNECTION) != SpoolConnection.NONE)
            return InteractionResult.PASS;
        return IWrenchable.super.onWrenched(state, context);
    }

    //    @Override
//    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
//        if (this.defaultBlockState().getValue(SPOOL_CONNECTION) == SpoolConnection.TOP)
//            createSpoolConnectionTooltip(tooltip, SpoolConnection.TOP);
//
//        return true;
//    }
//
//    private void createSpoolConnectionTooltip(List<Component> tooltip, SpoolConnection spoolConnection) {
//        CreateLang.text("")
//                .add(Component.translatable("text.creategenderless.spool_connection").withStyle(ChatFormatting.DARK_GREEN))
//                .add(CreateLang.text(": ").style(ChatFormatting.DARK_GREEN))
//                .add(Component.literal(spoolConnection.getNameProper()).withStyle(ChatFormatting.GREEN))
//                .forGoggles(tooltip, 1);
//    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getClickedFace();
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState blockstate = level.getBlockState(ctx.getClickedPos().relative(direction.getOpposite()));

        BlockState returnStatement = blockstate.is(this) && blockstate.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);


        BlockState belowState = level.getBlockState(pos.below());
        BlockState aboveState = level.getBlockState(pos.above());

        BlockState eastState = level.getBlockState(pos.east());
        BlockState westState = level.getBlockState(pos.west());

        BlockState southState = level.getBlockState(pos.south());
        BlockState northState = level.getBlockState(pos.north());

        List<SpoolConnection> acceptableBelowStateConnections = List.of(SpoolConnection.NONE, SpoolConnection.BOTTOM);
        List<SpoolConnection> acceptableAboveStateConnections = List.of(SpoolConnection.NONE, SpoolConnection.TOP);

        List<Direction> acceptableDirections0 = List.of(Direction.EAST, Direction.WEST);
        List<Direction> acceptableDirections1 = List.of(Direction.SOUTH, Direction.NORTH);

        // Vertical
        if (aboveState.getBlock() instanceof SilkSpoolBlock && acceptableAboveStateConnections.contains(aboveState.getValue(SPOOL_CONNECTION)) && aboveState.getValue(FACING) == Direction.DOWN) {
            return returnStatement.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        }
        if (belowState.getBlock() instanceof SilkSpoolBlock && acceptableBelowStateConnections.contains(belowState.getValue(SPOOL_CONNECTION)) && belowState.getValue(FACING) == Direction.UP) {
            return returnStatement.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);
        }

        // Horizontal 1
        if (eastState.getBlock() instanceof SilkSpoolBlock && acceptableBelowStateConnections.contains(eastState.getValue(SPOOL_CONNECTION)) && eastState.getValue(FACING) == Direction.WEST && acceptableDirections0.contains(eastState.getValue(FACING))) {
            return returnStatement.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        }
        if (westState.getBlock() instanceof SilkSpoolBlock && acceptableAboveStateConnections.contains(westState.getValue(SPOOL_CONNECTION)) && westState.getValue(FACING) == Direction.EAST && acceptableDirections0.contains(westState.getValue(FACING))) {
            return returnStatement.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);
        }

        // Horizontal 2
        if (southState.getBlock() instanceof SilkSpoolBlock && acceptableBelowStateConnections.contains(southState.getValue(SPOOL_CONNECTION)) && southState.getValue(FACING) == Direction.NORTH && acceptableDirections1.contains(southState.getValue(FACING))) {
            return returnStatement.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        }
        if (northState.getBlock() instanceof SilkSpoolBlock && acceptableAboveStateConnections.contains(northState.getValue(SPOOL_CONNECTION)) && northState.getValue(FACING) == Direction.SOUTH && acceptableDirections1.contains(northState.getValue(FACING))) {
            return returnStatement.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);
        }

        return returnStatement;
    }

    // TODO: Fix rotational blockstates - halt player from being able to rotate block if SPOOL_CONNECTION != SpoolConnection.NONE
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state0, LevelAccessor level, BlockPos pos, BlockPos pos0) {
        BlockState aboveState = level.getBlockState(pos.above());
        BlockState belowState = level.getBlockState(pos.below());

        BlockState eastState = level.getBlockState(pos.east());
        BlockState westState = level.getBlockState(pos.west());

        BlockState southState = level.getBlockState(pos.south());
        BlockState northState = level.getBlockState(pos.north());

        // Vertical
        if (belowState.getBlock() instanceof SilkSpoolBlock && belowState.getValue(SPOOL_CONNECTION) == SpoolConnection.NONE && belowState.getValue(FACING) == Direction.UP)
            belowState.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        if (aboveState.getBlock() instanceof SilkSpoolBlock && aboveState.getValue(SPOOL_CONNECTION) == SpoolConnection.NONE && aboveState.getValue(FACING) == Direction.DOWN)
            aboveState.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);

        // Horizontal 1
        if (eastState.getBlock() instanceof SilkSpoolBlock && eastState.getValue(SPOOL_CONNECTION) == SpoolConnection.NONE && eastState.getValue(FACING) == Direction.WEST)
            eastState.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        if (westState.getBlock() instanceof SilkSpoolBlock && westState.getValue(SPOOL_CONNECTION) == SpoolConnection.NONE && westState.getValue(FACING) == Direction.EAST)
            westState.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);

        // Horizontal 2
        if (southState.getBlock() instanceof SilkSpoolBlock && southState.getValue(SPOOL_CONNECTION) == SpoolConnection.NONE && southState.getValue(FACING) == Direction.NORTH)
            southState.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        if (northState.getBlock() instanceof SilkSpoolBlock && northState.getValue(SPOOL_CONNECTION) == SpoolConnection.NONE && northState.getValue(FACING) == Direction.SOUTH)
            northState.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);



        // Vertical
        if (aboveState.getBlock() instanceof SilkSpoolBlock && aboveState.getValue(FACING) == Direction.DOWN) {
            return state.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        } else if (belowState.getBlock() instanceof SilkSpoolBlock && belowState.getValue(SPOOL_CONNECTION) == SpoolConnection.BOTTOM && belowState.getValue(FACING) == Direction.UP) {
            return state.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);
        }

        // Horizontal 1
        if (eastState.getBlock() instanceof SilkSpoolBlock && eastState.getValue(FACING) == Direction.WEST) {
            return state.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        } else if (westState.getBlock() instanceof SilkSpoolBlock && westState.getValue(SPOOL_CONNECTION) == SpoolConnection.BOTTOM && westState.getValue(FACING) == Direction.EAST) {
            return state.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);
        }

        // Horizontal 2
        if (southState.getBlock() instanceof SilkSpoolBlock && southState.getValue(FACING) == Direction.NORTH) {
            return state.setValue(SPOOL_CONNECTION, SpoolConnection.BOTTOM);
        } else if (northState.getBlock() instanceof SilkSpoolBlock && northState.getValue(SPOOL_CONNECTION) == SpoolConnection.BOTTOM && northState.getValue(FACING) == Direction.SOUTH) {
            return state.setValue(SPOOL_CONNECTION, SpoolConnection.TOP);
        }

        return state.setValue(SPOOL_CONNECTION, SpoolConnection.NONE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(FACING).getAxis()) {
            case X:
            default:
                return SHAPE_X;
            case Z:
                return SHAPE_Z;
            case Y:
                return SHAPE_Y;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING, SPOOL_CONNECTION);
    }

    public enum SpoolConnection implements StringRepresentable {
        TOP("Top"),
        BOTTOM("Bottom"),
        NONE("None"),;

        final String name;
        public static final StringRepresentable.EnumCodec<SpoolConnection> CODEC = StringRepresentable.fromEnum(SpoolConnection::values);

        SpoolConnection(String name) {
            this.name = name;
        }

        @Nullable
        public static SpoolConnection byName(String name) {
            return CODEC.byName(name.toLowerCase(Locale.ROOT));
        }

        public String getNameProper() {
            return name;
        }

        public String getName() {
            return name.toLowerCase(Locale.ROOT);
        }

        @Override
        public String getSerializedName() {
            return name.toLowerCase(Locale.ROOT);
        }
    }
}
