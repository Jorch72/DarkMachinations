/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017:
 *     Ethan Brooks (CalmBit),
 *     and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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


		if (delta == 0.0f)
			delta = 1.0f/te.pumpTimerMaximum;

		if (te.renderToobHeightCurr < te.renderToobHeightMax)
			te.renderToobHeightCurr += delta;
		else
			te.renderToobHeightMax = te.toobHeight;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		GlStateManager.enableRescaleNormal();
		GlStateManager.disableBlend();
		GlStateManager.color(te.tR,te.tG,te.tB,1.0f);

		bindTexture(new ResourceLocation("darkmachinations", "textures/blocks/fluid_pipe.png"));
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
