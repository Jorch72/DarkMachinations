package com.elytradev.darkmachinations.fluid;

import com.elytradev.concrete.inventory.ConcreteFluidTank;

import java.util.ArrayList;

public class FluidBuffer extends ConcreteFluidTank{
	private ArrayList<Runnable> listeners = new ArrayList<>();

	public FluidBuffer(int capacity) {
		super(capacity);
	}
}
