package com.hlysine.create_connected.content.freewheelclutch;

import com.hlysine.create_connected.content.ClutchValueBox;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static com.hlysine.create_connected.content.freewheelclutch.FreewheelClutchBlock.UNCOUPLED;
import static com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity.RotationDirection;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class FreewheelClutchBlockEntity extends SplitShaftBlockEntity {

    protected ScrollOptionBehaviour<RotationDirection> movementDirection;

    private boolean reattachNextTick = false;

    public FreewheelClutchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        movementDirection = new ScrollOptionBehaviour<>(RotationDirection.class,
                Lang.translateDirect("contraptions.windmill.rotation_direction"),
                this,
                new ClutchValueBox());
        movementDirection.withCallback(i -> this.onDirectionChanged());
        behaviours.add(movementDirection);
    }

    @Override
    public void initialize() {
        onDirectionChanged();
        super.initialize();
    }

    private void onDirectionChanged() {
        KineticNetwork network = getOrCreateNetwork();
        updateFromNetwork(capacity, stress, network == null ? 0 : network.getSize());
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);

        boolean coupled = !getBlockState().getValue(UNCOUPLED);
        boolean correctDirection = Mth.sign(getSpeed()) == (movementDirection.getValue() * 2 - 1);
        if (coupled != correctDirection) {
            if (level != null) {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().cycle(UNCOUPLED));
                RotationPropagator.handleRemoved(level, getBlockPos(), this);
                reattachNextTick = true;
            }
        }
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        KineticNetwork network = getOrCreateNetwork();
        updateFromNetwork(capacity, stress, network == null ? 0 : network.getSize());
    }

    @Override
    public void tick() {
        super.tick();
        if (reattachNextTick && level != null) {
            reattachNextTick = false;
            RotationPropagator.handleAdded(level, getBlockPos(), this);
        }
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        if (face == getBlockState().getValue(FACING) && getBlockState().getValue(UNCOUPLED))
            return 0;
        return 1;
    }
}

