package com.calmbit.darkmachinations.monster;


import com.calmbit.darkmachinations.registry.LootTableRegistry;
import com.calmbit.darkmachinations.registry.SoundRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;


public class EntityGoldenCreeper extends EntityMob {

    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntityGoldenCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityGoldenCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntityGoldenCreeper.class, DataSerializers.BOOLEAN);

    private int lastActiveTime;
    private int timeSinceIgnited;
    private short fuseTime = 30;
    private byte explosionRadius = 3;
    private int creeperState;
    private static final double MOVEMENT_SPEED = 0.25D;
    private static final double MAXIMUM_HEALTH = 30.0D;
    public EntityGoldenCreeper(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.7F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIGoldenCreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(4, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAXIMUM_HEALTH);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    public int getMaxFallHeight()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(STATE, -1);
        this.dataManager.register(POWERED, false);
        this.dataManager.register(IGNITED, false);
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("powered", this.dataManager.get(POWERED));
        compound.setShort("fuse", this.fuseTime);
        compound.setByte("explosionRadius", this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(POWERED, compound.getBoolean("powered"));
        this.fuseTime = compound.getShort("fuse");
        this.explosionRadius = compound.getByte("explosionRadius");
        if(compound.getBoolean("ignited"))
            this.ignite();
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.hasIgnited())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(SoundRegistry.goldenCreeperHiss, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }
        super.onUpdate();
    }

    protected SoundEvent getHurtSound()
    {
        return SoundRegistry.goldenCreeperHurt;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundRegistry.goldenCreeperKilled;
    }

    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (this.world.getGameRules().getBoolean("doMobLoot"))
        {
            if (cause.getEntity() instanceof EntitySkeleton)
            {
                int i = Item.getIdFromItem(Items.RECORD_13);
                int j = Item.getIdFromItem(Items.RECORD_WAIT);
                int k = i + this.rand.nextInt(j - i + 1);
                this.dropItem(Item.getItemById(k), 1);
            }
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        return true;
    }

    public boolean getPowered()
    {
        return this.dataManager.get(POWERED);
    }

    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float modifier)
    {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * modifier) / (float)(this.fuseTime - 2);
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableRegistry.ENTITIES_GOLDEN_CREEPER;
    }

    public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(POWERED, Boolean.TRUE);
    }

    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.FLINT_AND_STEEL)
        {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);

            if (!this.world.isRemote)
            {
                this.ignite();
                itemstack.damageItem(1, player);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    private void explode()
    {
        if (!this.world.isRemote)
        {
            boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            float f = this.getPowered() ? 3.0f : 1.5f;
            this.dead = true;
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius * f, flag);
            this.setDead();
        }
    }

    private boolean hasIgnited()
    {
        return this.dataManager.get(IGNITED);
    }

    private void ignite()
    {
        this.dataManager.set(IGNITED, Boolean.TRUE);
    }



    int getCreeperState() {
        return this.dataManager.get(STATE);
    }

    void setCreeperState(int creeperState) {
        this.dataManager.set(STATE, creeperState);
    }
}
