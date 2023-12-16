package com.hlysine.create_connected.content.brake;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.hlysine.create_connected.content.brake.BrakeBlock.POWERED;

public class BrakeBlockEntity extends SplitShaftBlockEntity {

    private static final int PARTICLE_INTERVAL = 2;
    private int particleTimer = 0;


    public BrakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        return 1;
    }

    @Override
    public float calculateStressApplied() {
        return getBlockState().getValue(POWERED) ? super.calculateStressApplied() : 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide()) return;

        if (getBlockState().getValue(POWERED) && getSpeed() > 0) {
            if (particleTimer-- < 0) {
                particleTimer = PARTICLE_INTERVAL;
                Vec3 loc = Vec3.atBottomCenterOf(getBlockPos());
                level.addParticle(ParticleTypes.LARGE_SMOKE, false, loc.x, loc.y + 0.5, loc.z, 0, 0.05, 0);
            }
        }
    }
}