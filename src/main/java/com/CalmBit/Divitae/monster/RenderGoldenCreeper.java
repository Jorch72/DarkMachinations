package com.CalmBit.Divitae.monster;

import com.CalmBit.Divitae.Divitae;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class RenderGoldenCreeper extends RenderLiving<EntityGoldenCreeper> {
    private static final ResourceLocation GOLDEN_CREEPER_TEXTURE = new ResourceLocation(Divitae.DIVITAE_MOD_ID, "textures/entity/golden_creeper/golden_creeper.png");
    public RenderGoldenCreeper(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelCreeper(), 0.5f);
        boolean b = this.addLayer(new LayerGoldenCreeperCharge(this));
    }

    protected void preRenderCallback(EntityCreeper entitylivingbaseIn, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
    }

    protected int getColorMultiplier(EntityGoldenCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int) (f * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int i = (int) (f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGoldenCreeper entity) {
        return GOLDEN_CREEPER_TEXTURE;
    }
}
