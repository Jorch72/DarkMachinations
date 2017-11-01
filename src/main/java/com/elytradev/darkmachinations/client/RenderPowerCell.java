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

import com.elytradev.darkmachinations.block.BlockPowerCell;
import com.elytradev.darkmachinations.tileentity.TileEntityPowerCell;
import com.elytradev.darkmachinations.tileentity.TileEntityPump;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class RenderPowerCell extends TileEntitySpecialRenderer<TileEntityPowerCell> {
	private float delta;

	@Override
	public void render(TileEntityPowerCell te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		if (te.isCreative || !(te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockPowerCell)) {
			return;
		}

		BlockPos pos = te.getPos();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		GlStateManager.pushMatrix();
		GlStateManager.translate(x,y,z);

		GlStateManager.enableRescaleNormal();
		GlStateManager.disableBlend();
		//GlStateManager.disableLighting();
		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, te.getBlockType().getLightValue(getWorld().getBlockState(pos), getWorld(), pos) * 16,getWorld().getSkylightSubtracted() * 16);
		GlStateManager.color(1.0f,1.0f,1.0f,1.0f);

		bindTexture(new ResourceLocation("darkmachinations", "textures/blocks/power_cell_front_on.png"));
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

		EnumFacing facing = te.getWorld().getBlockState(pos).getValue(BlockHorizontal.FACING);

		Vec3i facingVector = facing.getDirectionVec();

		float xOffset = 1.0f;
		float yOffset = 0.5f;
		float zOffset = 0.00005f;

		bufferBuilder.pos(facingVector.getX(), facingVector.getY(), facingVector.getZ()+zOffset)
				.tex(0, 0)
				.normal(facingVector.getX(), facingVector.getY(), -facingVector.getZ())
				.endVertex();

		bufferBuilder.pos(facingVector.getX()+xOffset, facingVector.getY(), facingVector.getZ()+zOffset)
				.tex(1, 0)
				.normal(facingVector.getX(), facingVector.getY(), -facingVector.getZ())
				.endVertex();

		bufferBuilder.pos(facingVector.getX()+xOffset, facingVector.getY()+yOffset, facingVector.getZ()+zOffset)
				.tex(1, yOffset)
				.normal(facingVector.getX(), facingVector.getY(), -facingVector.getZ())
				.endVertex();

		bufferBuilder.pos(facingVector.getX(), facingVector.getY()+yOffset, facingVector.getZ()+zOffset)
				.tex(0, yOffset)
				.normal(facingVector.getX(), facingVector.getY(), -facingVector.getZ())
				.endVertex();

		tessellator.draw();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
}
