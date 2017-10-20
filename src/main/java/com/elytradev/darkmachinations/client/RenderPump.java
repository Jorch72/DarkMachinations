package com.elytradev.darkmachinations.client;

import com.elytradev.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPump extends TileEntitySpecialRenderer<TileEntityPump> {

	private float delta;

	@Override
	public void render(TileEntityPump te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();


		if(delta == 0.0f)
			delta = 1.0f/te.pumpTimerMaximum;

		if(te.renderToobHeightCurr < te.renderToobHeightMax)
			te.renderToobHeightCurr += delta;
		else
			te.renderToobHeightMax = te.toobHeight;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		GlStateManager.enableRescaleNormal();
		GlStateManager.disableBlend();
		GlStateManager.color(te.tR,te.tG,te.tB,1.0f);

		bindTexture(new ResourceLocation("minecraft", "textures/blocks/iron_block.png"));
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

		bufferBuilder.pos(0.25, 0.0, 0.25).tex(0, 0).normal(-1.0f, 0.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.25, -te.renderToobHeightCurr, 0.25).tex(te.renderToobHeightCurr, 0).normal(-1.0f, 0.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.25, -te.renderToobHeightCurr, 0.75).tex(te.renderToobHeightCurr, 1).normal(-1.0f, 0.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.25, 0.0, 0.75).tex(0, 1).normal(-1.0f, 0.0f, 0.0f).endVertex();

		bufferBuilder.pos(0.75, 0.0, 0.75).tex(0, 0).normal(1.0f, 0.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.75, -te.renderToobHeightCurr, 0.75).tex(te.renderToobHeightCurr, 0).normal(1.0f, 0.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.75, -te.renderToobHeightCurr, 0.25).tex(te.renderToobHeightCurr, 1).normal(1.0f, 0.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.75, 0.0, 0.25).tex(0, 1).normal(1.0f, 0.0f, 0.0f).endVertex();

		bufferBuilder.pos(0.75, 0.0, 0.25).tex(0, 0).normal(0.0f, 0.0f, -1.0f).endVertex();
		bufferBuilder.pos(0.75, -te.renderToobHeightCurr, 0.25).tex(te.renderToobHeightCurr, 0).normal(0.0f, 0.0f, -1.0f).endVertex();
		bufferBuilder.pos(0.25, -te.renderToobHeightCurr, 0.25).tex(te.renderToobHeightCurr, 1).normal(0.0f, 0.0f, -1.0f).endVertex();
		bufferBuilder.pos(0.25, 0.0, 0.25).tex(0, 1).normal(0.0f, 0.0f, -1.0f).endVertex();

		bufferBuilder.pos(0.25, 0.0, 0.75).tex(0, 0).normal(0.0f, 0.0f, 1.0f).endVertex();
		bufferBuilder.pos(0.25, -te.renderToobHeightCurr, 0.75).tex(te.renderToobHeightCurr, 0).normal(0.0f, 0.0f, 1.0f).endVertex();
		bufferBuilder.pos(0.75, -te.renderToobHeightCurr, 0.75).tex(te.renderToobHeightCurr, 1).normal(0.0f, 0.0f, 1.0f).endVertex();
		bufferBuilder.pos(0.75, 0.0, 0.75).tex(0, 1).normal(0.0f, 0.0f, 1.0f).endVertex();

		bufferBuilder.pos(0.25, -te.renderToobHeightCurr, 0.25).tex(0, 0).normal(0.0f, -1.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.75, -te.renderToobHeightCurr, 0.25).tex(1, 0).normal(0.0f, -1.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.75, -te.renderToobHeightCurr, 0.75).tex(1, 1).normal(0.0f, -1.0f, 0.0f).endVertex();
		bufferBuilder.pos(0.25, -te.renderToobHeightCurr, 0.75).tex(0, 1).normal(0.0f, -1.0f, 0.0f).endVertex();

		tessellator.draw();
		GlStateManager.popMatrix();
	}
}
