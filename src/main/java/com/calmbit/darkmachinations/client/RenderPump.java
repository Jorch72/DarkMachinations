package com.calmbit.darkmachinations.client;

import com.calmbit.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPump extends TileEntitySpecialRenderer<TileEntityPump> {
    @Override
    public void renderTileEntityAt(TileEntityPump te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();

        for(int i =0;i < 20;i++) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y-i, z);

            GlStateManager.enableRescaleNormal();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f,1.0f,1.0f,1.0f);

            bindTexture(new ResourceLocation("minecraft", "textures/blocks/iron_block.png"));
            vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

            vertexbuffer.pos(0.25, 0.0, 0.25).tex(0, 0).normal(-1.0f, 0.0f, 0.0f).endVertex();
            vertexbuffer.pos(0.25, -1.0, 0.25).tex(1, 0).normal(-1.0f, 0.0f, 0.0f).endVertex();
            vertexbuffer.pos(0.25, -1.0, 0.75).tex(1, 1).normal(-1.0f, 0.0f, 0.0f).endVertex();
            vertexbuffer.pos(0.25, 0.0, 0.75).tex(0, 1).normal(-1.0f, 0.0f, 0.0f).endVertex();

            vertexbuffer.pos(0.75, 0.0, 0.75).tex(0, 0).normal(1.0f, 0.0f, 0.0f).endVertex();
            vertexbuffer.pos(0.75, -1.0, 0.75).tex(1, 0).normal(1.0f, 0.0f, 0.0f).endVertex();
            vertexbuffer.pos(0.75, -1.0, 0.25).tex(1, 1).normal(1.0f, 0.0f, 0.0f).endVertex();
            vertexbuffer.pos(0.75, 0.0, 0.25).tex(0, 1).normal(1.0f, 0.0f, 0.0f).endVertex();

            vertexbuffer.pos(0.75, 0.0, 0.25).tex(0, 0).normal(0.0f, 0.0f, -1.0f).endVertex();
            vertexbuffer.pos(0.75, -1.0, 0.25).tex(1, 0).normal(0.0f, 0.0f, -1.0f).endVertex();
            vertexbuffer.pos(0.25, -1.0, 0.25).tex(1, 1).normal(0.0f, 0.0f, -1.0f).endVertex();
            vertexbuffer.pos(0.25, 0.0, 0.25).tex(0, 1).normal(0.0f, 0.0f, -1.0f).endVertex();

            vertexbuffer.pos(0.25, 0.0, 0.75).tex(0, 0).normal(0.0f, 0.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.25, -1.0, 0.75).tex(1, 0).normal(0.0f, 0.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.75, -1.0, 0.75).tex(1, 1).normal(0.0f, 0.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.75, 0.0, 0.75).tex(0, 1).normal(0.0f, 0.0f, 1.0f).endVertex();

            tessellator.draw();
            GlStateManager.popMatrix();
        }
    }
}
