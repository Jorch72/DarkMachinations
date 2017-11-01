package com.elytradev.darkmachinations.gui;

import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;

import java.util.Random;

public class WOverloadedPanel extends WPlainPanel {

	private Random random;
	private final int jitterDegree = 1;

	public WOverloadedPanel() {
		super();
		random = new Random();
	}

	@Override
	public void paintBackground(int x, int y) {
		super.paintBackground(x+getJitter(), y+getJitter());
	}

	private int getJitter() {
		return (random.nextInt((jitterDegree*2)+1) - jitterDegree);
	}
}
