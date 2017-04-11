package com.CalmBit.Divitae.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIGoldenCreeperSwell extends EntityAIBase {

    private EntityGoldenCreeper creeper;
    private EntityLivingBase target;
    private static final double STOP_RADIUS = 16.0D;
    private static final double TRACKING_RADIUS = 49.0D;
    public EntityAIGoldenCreeperSwell(EntityGoldenCreeper creeper)
    {
        this.creeper =  creeper;
        this.setMutexBits(1);
    }
    @Override
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.creeper.getAttackTarget();
        return this.creeper.getCreeperState() > 0 || entitylivingbase != null && this.creeper.getDistanceSqToEntity(entitylivingbase) < STOP_RADIUS;
    }

    @Override
    public void startExecuting() {
        this.creeper.getNavigator().clearPathEntity();
        this.target = this.creeper.getAttackTarget();
    }

    @Override
    public void resetTask() {
        this.target = null;
    }

    @Override
    public void updateTask() {
        if(this.target == null ||
                this.creeper.getDistanceSqToEntity(this.target) > TRACKING_RADIUS ||
                !this.creeper.getEntitySenses().canSee(this.target))
        {
            this.creeper.setCreeperState(-1);
        }
        else
        {
            this.creeper.setCreeperState(1);
        }
    }
}
