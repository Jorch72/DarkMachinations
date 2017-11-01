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

package com.elytradev.darkmachinations.gui;

import com.elytradev.concrete.inventory.gui.client.GuiDrawing;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class WOverloadedBar extends WBar {
	private Direction direction;
	private ResourceLocation bg;
	private ResourceLocation bar;
	private Random random;
	private final int jitterDegree = 1;

	public WOverloadedBar(ResourceLocation bg, ResourceLocation bar, IInventory inventory, int field, int maxfield) {
		this(bg, bar, inventory, field, maxfield, Direction.UP);

	}

	public WOverloadedBar(ResourceLocation bg, ResourceLocation bar, IInventory inventory, int field, int maxfield, Direction dir) {
		super(bg, bar, inventory, field, maxfield, dir);
		this.bg = bg; // Unfortunately, this field is private.
		this.bar = bar; // So is this one :\
		this.direction = dir; // *sigh*
		random = new Random();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void paintBackground(int x, int y) {
		GuiDrawing.rect(bg, x+getJitter(), y+getJitter(), getWidth(), getHeight(), 0xFFFFFFFF);

		float percent = 1f;

		int barMax = getWidth();
		if (direction == Direction.DOWN || direction == Direction.UP) barMax = getHeight();
		percent = ((int) (percent * barMax)) / (float) barMax; //Quantize to bar size

		int barSize = (int) (barMax * percent);
		if (barSize <= 0) return;

		switch(direction) { //anonymous blocks in this switch statement are to sandbox variables
			case UP: {
				int left = x;
				int top = y + getHeight();
				top -= barSize;
				GuiDrawing.rect(bar, left+getJitter(), top+getJitter(), getWidth(), barSize, 0, 1 - percent, 1, 1, randomColor());
				break;
			}
			case RIGHT: {
				GuiDrawing.rect(bar, x+getJitter(), y+getJitter(), barSize, getHeight(), 0, 0, percent, 1, randomColor());
				break;
			}
			case DOWN: {
				GuiDrawing.rect(bar, x+getJitter(), y+getJitter(), getWidth(), barSize, 0, 0, 1, percent, randomColor());
				break;
			}
			case LEFT: {
				int left = x + getWidth();
				int top = y;
				left -= barSize;
				GuiDrawing.rect(bar, left+getJitter(), top+getJitter(), barSize, getHeight(), 1 - percent, 0, 1, 1, randomColor());
				break;
			}
		}
	}

	@Override
	protected void renderTooltip(int tX, int tY) {
		super.renderTooltip(tX+getJitter(), tY+getJitter());
	}

	private int randomColor() {
		int color = random.nextInt((0xFFFFFF+1));
		return 0xFF000000+(color);
	}

	private int getJitter() {
		return (random.nextInt((jitterDegree*2)+1) - jitterDegree);
	}
}
