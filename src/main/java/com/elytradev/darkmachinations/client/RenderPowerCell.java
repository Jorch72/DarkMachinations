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
