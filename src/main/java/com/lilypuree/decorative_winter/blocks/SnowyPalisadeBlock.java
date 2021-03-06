package com.lilypuree.decorative_winter.blocks;

import com.lilypuree.decorative_blocks.blocks.PalisadeBlock;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.Arrays;

public class SnowyPalisadeBlock extends PalisadeBlock implements ISnowLoggable {

    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    protected final VoxelShape[] snowyShapes;


    public SnowyPalisadeBlock(Properties properties, IWoodType woodType) {
        super(properties, woodType);
        this.snowyShapes = Arrays.stream(this.shapes).map(voxelShape -> VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), voxelShape)).toArray(VoxelShape[]::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(SNOWY)) {
            return snowyShapes[this.getIndex(state)];
        } else {
            return super.getShape(state, worldIn, pos, context);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(SNOWY);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        BlockState state = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return updateSnowState(state, facing, facingState, worldIn, currentPos, facingPos);
    }

}
